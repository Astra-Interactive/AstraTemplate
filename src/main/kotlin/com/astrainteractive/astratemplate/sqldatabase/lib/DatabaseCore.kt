package com.astrainteractive.astratemplate.sqldatabase.lib

import com.astrainteractive.astralibs.utils.catching
import com.astrainteractive.astralibs.utils.mapNotNull
import com.astrainteractive.astratemplate.sqldatabase.lib.AnnotationUtils
import com.astrainteractive.astratemplate.sqldatabase.lib.create
import com.astrainteractive.astratemplate.sqldatabase.lib.fromResultSet
import java.sql.Connection
import java.sql.ResultSet
import java.sql.Statement

val Connection?.isConnected: Boolean
    get() = this?.isClosed != true

abstract class DatabaseCore() {
    abstract val connectionBuilder: () -> Connection?
    val connection by lazy { catching { connectionBuilder() } }


    abstract suspend fun onEnable()
    suspend fun close() = catching {
        connection?.close()
        connection?.close()
    }

    inline fun <reified T> update(instance: T) = update(T::class.java, instance)
    inline fun <reified T> delete(instance: T) = delete(T::class.java, instance)
    inline fun <reified T> select(where: String = "") = select(T::class.java, where)
    inline fun <reified T> insert(instance: T) = insert(T::class.java, instance)
    inline fun <reified T> createTable() = createTable(T::class.java)
    fun <T> update(clazz: Class<out T>, instance: T): Boolean? {
        val info = AnnotationUtils.EntityInfo.create(clazz, instance)
        val primaryKeyInfo =
            info?.columns?.firstOrNull { it.primaryKey != null } ?: throw Exception("Primary key not found")

        val entries = info?.columns?.mapNotNull {
            if (it.primaryKey != null) null
            else "${it.columnInfo.name}=${it.sqlFieldValue}"
        }?.joinToString(", ")

        val query =
            "UPDATE ${info?.entity?.tableName} SET $entries WHERE ${primaryKeyInfo.columnInfo.name}=${primaryKeyInfo.sqlFieldValue}"
        println(query)
        return connection?.prepareStatement(query)?.execute()
    }

    fun <T> delete(clazz: Class<out T>, instance: T): Boolean? {
        val info = AnnotationUtils.EntityInfo.create(clazz, instance)
        val primaryKeyInfo =
            info?.columns?.firstOrNull { it.primaryKey != null } ?: throw Exception("Primary key not found")
        val query =
            "DELETE FROM ${info?.entity?.tableName} WHERE ${primaryKeyInfo.columnInfo.name}=${primaryKeyInfo.sqlFieldValue}"
        println(query)
        return connection?.prepareStatement(query)?.execute()
    }

    fun <T> select(clazz: Class<out T>, where: String = ""): List<T>? {
        val info = AnnotationUtils.EntityInfo.create(clazz)
        val query = "SELECT * FROM ${info?.entity?.tableName} $where"
        println(query)
        return connection?.createStatement()?.executeQuery(query)?.mapNotNull { rs ->
            fromResultSet(clazz, info, rs)
        }
    }


    fun <T> insert(clazz: Class<out T>, instance: T): Long? {
        val info = AnnotationUtils.EntityInfo.create(clazz, instance)
        val keys = info?.columns?.mapNotNull {
            if (it.primaryKey != null) null
            else it.columnInfo.name
        }?.joinToString(", ", "(", ")")

        val values = info?.columns?.mapNotNull {
            if (it.primaryKey != null) null
            else it.sqlFieldValue
        }?.joinToString(", ", "(", ")")

        val query = "INSERT INTO ${info?.entity?.tableName} $keys VALUES $values"
        println(query)

        val prepared =
            connection?.prepareStatement(query, Statement.RETURN_GENERATED_KEYS).apply { this?.executeUpdate() }
        return prepared?.generatedKeys?.getLong(1)
    }


    fun <T> createTable(clazz: Class<out T>): Boolean? {
        val info = AnnotationUtils.EntityInfo.create(clazz)
        val keys = info?.columns?.joinToString(",", "(", ")") {
            buildList {
                add("${it.columnInfo.name} ${AnnotationUtils.resolveType(it.parameter)}")
                it.primaryKey?.let {
                    add("PRIMARY KEY")
                    if (it.autoIncrement)
                        add("AUTOINCREMENT")
                }
                if (!it.columnInfo.nullable) add("NOT NULL")
                if (it.columnInfo.unique) add("UNIQUE")
            }.joinToString(" ")
        }
        val query = "CREATE TABLE IF NOT EXISTS ${info?.entity?.tableName} $keys"
        println(query)
        return connection?.prepareStatement(query)?.execute()
    }
}

