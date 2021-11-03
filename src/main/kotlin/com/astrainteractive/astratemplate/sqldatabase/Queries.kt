package com.astrainteractive.astratemplate.sqldatabase

import com.astrainteractive.astralibs.catching
import com.astrainteractive.astratemplate.sqldatabase.entities.User
import javax.xml.crypto.Data

object Queries {
    fun createUserTable() =
        catching {
            Database.connection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS ${User.table} " +
                        "(${User.discordId.name} ${User.discordId.type} NOT NULL, " +
                        "${User.minecraftUuid.name} ${User.minecraftUuid.type} NOT NULL, " +
                        "PRIMARY KEY (${User.primaryKey}));"
            ).execute()
        }

    fun insertUser(user: User) =
        catching {
            val command = InsertQuery.Builder()
                .table(User.table)
                .columns(User.discordId.name, User.minecraftUuid.name)
                .values(user.discordId, user.minecraftUuid)
                .build()
            println(command)
            Database.connection.createStatement().executeUpdate(command)
        }

    fun getAllUsers() = catching {
        val rs = Database.connection.createStatement().executeQuery("SELECT * FROM ${User.table}")
        val list = mutableListOf<User>()
        while (rs.next()) {
            val user = User.fromResultSet(rs) ?: continue
            list.add(user)
            println("User = ${user}")
        }


        return@catching list
    }
}

class InsertQuery private constructor() {

    data class Builder(
        private val command: String = "INSERT INTO",
        private var table: String? = null,
        private var columns: String? = null,
        private var values: String? = null
    ) {

        fun table(table: String) = apply { this.table = table }
        fun columns(vararg columns: String) = apply { this.columns = "(${columns.joinToString(", ")})" }


        private fun Array<out Any>.parseSQLValues(): String {
            val list = mutableListOf<Any>()
            this.forEach {
                if (it is String)
                    list.add("\'$it\'")
                else list.add("\'$it\'")
            }
            return "(${list.joinToString(",")})"
        }


        fun values(vararg values: Any) = apply { this.values = values.parseSQLValues() }

        fun build(): String? {
            return "$command $table ${columns ?: ""} VALUES ${values ?: return null};"
        }
    }

}