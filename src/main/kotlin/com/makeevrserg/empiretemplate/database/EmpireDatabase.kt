package com.makeevrserg.empiretemplate.database

import com.makeevrserg.empiretemplate.EmpireTemplate.Companion.plugin
import com.makeevrserg.empiretemplate.utils.Translations.Companion.translations
import java.io.File
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException


class EmpireDatabase {


    val dbPath = "${plugin.dataFolder}${File.separator}data.db"
    lateinit var conn: Connection


    private fun CreateTables(): Boolean {
        return try {
            conn = DriverManager.getConnection("jdbc:sqlite:$dbPath")
            conn.prepareStatement("CREATE TABLE IF NOT EXISTS USER(discord_id varchar(32),mincraft_uuid varchar(16))")
                .execute()
            true
        } catch (ex: SQLException) {
            false
        }
    }

    data class User(var discordID: String, var mincraftUUID: String) {
        constructor(result: ResultSet) : this("", "") {
            discordID = result.getString(1)
            mincraftUUID = result.getString(2)
        }
    }

    public fun createUser(user: User): Boolean {
        return try {
            conn.prepareStatement("INSERT INTO USER (discord_id, mincraft_uuid) VALUES('${user.discordID}','${user.mincraftUUID}')")
                .execute()

            true
        } catch (ex: SQLException) {
            false
        }
    }

    public fun getUsers(): MutableList<User> {
        return try {
            val result = conn.createStatement().executeQuery("SELECT * from USER")
            if (!result.next())
                return mutableListOf()
            val users = mutableListOf<User>()
            while (result.next())
                users.add(User(result))
            users
        } catch (ex: SQLException) {
            return mutableListOf()
        }
    }

    init {
        if (CreateTables())
            println(translations.DB_SUCCESS)
        else
            println(translations.DB_FAIL)


        (createUser(User("AAaaaa", "Bbbbb")))
        getUsers()


    }

    public fun onDisable() {
        conn.close()
    }

}
