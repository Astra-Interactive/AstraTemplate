package com.astrainteractive.astratemplate.sqldatabase

import com.astrainteractive.astralibs.AstraLibs
import com.astrainteractive.astralibs.Logger
import com.astrainteractive.astralibs.database.DatabaseCore
import com.astrainteractive.astralibs.database.isConnected
import com.astrainteractive.astratemplate.api.DatabaseApi
import com.astrainteractive.astratemplate.utils.Translation
import java.io.File
import java.sql.Connection
import java.sql.DriverManager

object SQLDatabase : DatabaseCore() {
    override val connectionBuilder: () -> Connection? = {
        val _dbPath = "${AstraLibs.instance.dataFolder}${File.separator}data.db"
        DriverManager.getConnection(("jdbc:sqlite:$_dbPath"))
    }

    override suspend fun onEnable() {
        if (connection.isConnected)
            Logger.log(Translation.dbSuccess, "Database")
        else {
            Logger.error(Translation.dbFail, "Database")
            return
        }
        DatabaseApi.createUserTable()
        DatabaseApi.createRatingTable()
    }
}