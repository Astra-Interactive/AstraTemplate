package com.astrainteractive.astratemplate.sqldatabase

import com.astrainteractive.astralibs.utils.AstraLibsExtensions.then
import com.astrainteractive.astralibs.utils.catching
import com.astrainteractive.astralibs.utils.mapNotNull
import com.astrainteractive.astratemplate.sqldatabase.entities.EntityInfo
import java.sql.Connection
import java.sql.ResultSet
import java.sql.Statement

abstract class DatabaseCore {
    var connection: Connection? = null
    val isInitialized: Boolean
        get() = connection?.isClosed != true

    companion object {
        /**
         * In SQL we need to wrap string values in "", so you need to modify your string with this function
         */
        fun sqlString(value: String) = "\"$value\""
        val String.sqlString: String
            get() = sqlString(this)
    }

    abstract suspend fun createConnection()
    abstract suspend fun onEnable()
    suspend fun close() = catching {
        connection?.close()
    }

    suspend fun createTable(table: String, entites: List<EntityInfo>) = catching(true) {
        val query = "CREATE TABLE IF NOT EXISTS $table (" + entites.joinToString(",") {
            mutableListOf<String>().apply {
                add("${it.name} ${it.type}")
                if (it.primaryKey) add("PRIMARY KEY")
                if (it.autoIncrement) add("AUTOINCREMENT")
                if (!it.nullable) add("NOT NULL")
                if (it.unique) add("UNIQUE")
            }.joinToString(" ")
        } + "); "
        return@catching connection?.prepareStatement(query)?.execute()
    }

    suspend fun insert(table: String, vararg entry: Pair<EntityInfo, Any>): Long? = catching(true) {
        val names = "(" + entry.map { it.first.name }.joinToString(",") + ") "
        val values = "VALUES(" + entry.map { if (it.second is String) (it.second as String).sqlString else it.second }
            .joinToString(",") + ");"
        val query = "INSERT INTO $table $names $values"
        val prepared =
            connection?.prepareStatement(query, Statement.RETURN_GENERATED_KEYS).apply { this?.executeUpdate() }
        return@catching prepared?.generatedKeys?.getLong(1)
    }

    suspend fun <T, K> selectEntryByID(table: String, idName: String, id: K, builder: (ResultSet) -> T?): T? =
        catching(true) {
            val id = (id is String).then((id as String).sqlString) ?: id
            val query = "SELECT * FROM $table WHERE $idName=$id"
            val rs = connection?.createStatement()?.executeQuery(query)
            return@catching rs?.mapNotNull { builder(it) }?.firstOrNull()
        }

    suspend fun <T> select(table: String, builder: (ResultSet) -> T?): List<T>? = catching {
        val rs = connection?.createStatement()?.executeQuery("SELECT * FROM $table")
        return@catching rs?.mapNotNull { builder(it) }
    }

    suspend fun <T> updateByID(table: String, idName: String, id: T, vararg entry: Pair<EntityInfo, Any>): Boolean? =
        catching(true) {
            val id = (id is String).then((id as String).sqlString) ?: id
            val entries = entry.map { it.first.name + "=" + it.second }.joinToString(", ")
            val query = "UPDATE $table SET $entries WHERE $idName=$id"
            return@catching connection?.prepareStatement(query)?.execute()
        }

    suspend fun <T> deleteEntryByID(table: String, idName: String, id: T): Boolean? = catching(true) {
        val id = (id is String).then((id as String).sqlString) ?: id
        val query = "DELETE FROM $table WHERE $idName=$id"
        return@catching connection?.prepareStatement(query)?.execute()
    }
}

