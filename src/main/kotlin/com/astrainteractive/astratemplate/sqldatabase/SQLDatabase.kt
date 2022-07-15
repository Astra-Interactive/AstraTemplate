package com.astrainteractive.astratemplate.sqldatabase

import com.astrainteractive.astralibs.AstraLibs
import com.astrainteractive.astralibs.Logger
import com.astrainteractive.astralibs.utils.catching
import com.astrainteractive.astratemplate.api.DatabaseApi
import com.astrainteractive.astratemplate.utils.Translation
import java.io.File
import java.sql.DriverManager

object SQLDatabase : DatabaseCore() {
    private val _dbPath = "${AstraLibs.instance.dataFolder}${File.separator}data.db"
    override suspend fun createConnection() {
        connection = catching { DriverManager.getConnection(("jdbc:sqlite:${_dbPath}")) }
    }

    override suspend fun onEnable() {
        createConnection()
        if (isInitialized)
            Logger.log(Translation.dbSuccess, "Database")
        else {
            Logger.error(Translation.dbFail, "Database")
            return
        }
        DatabaseApi.createUserTable()
    }
}