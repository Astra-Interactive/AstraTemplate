package com.astrainteractive.astratemplate.domain.local

import com.astrainteractive.astratemplate.domain.local.entities.RatingRelation
import com.astrainteractive.astratemplate.domain.local.entities.User
import ru.astrainteractive.astralibs.database.DatabaseCore
import ru.astrainteractive.astralibs.database.isConnected
import java.io.File
import java.sql.Connection
import java.sql.DriverManager

class SQLDatabase(path: String) : DatabaseCore() {
    object DBConnectionException : Exception("Database failed to establish connection")

    override val connectionBuilder: () -> Connection? = {
        DriverManager.getConnection(("jdbc:sqlite:$path"))
    }

    override suspend fun onEnable() {
        if (!connection.isConnected) throw DBConnectionException
        createTable<User>()
        createTable<RatingRelation>()
    }
}