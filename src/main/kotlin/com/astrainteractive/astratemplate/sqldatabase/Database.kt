package com.astrainteractive.astratemplate.sqldatabase

import com.astrainteractive.astralibs.Logger
import com.astrainteractive.astralibs.async.AsyncHelper
import com.astrainteractive.astralibs.catching
import com.astrainteractive.astratemplate.AstraTemplate
import com.astrainteractive.astratemplate.sqldatabase.entities.User
import com.astrainteractive.astratemplate.utils.Translation
import kotlinx.coroutines.launch
import java.io.File
import java.sql.Connection
import java.sql.DriverManager
import kotlin.random.Random


/**
 * Database for plugin
 */
class Database {
    /**
     * Path for your plugin database
     */
    private val _dbPath = "${AstraTemplate.instance.dataFolder}${File.separator}data.db"

    /**
     * Connection for your database.
     */
    companion object {
        lateinit var connection: Connection
        val isInitialized: Boolean
            get() = this::connection.isInitialized && !connection.isClosed
    }

    /**
     * Function for connecting to database
     */
    private suspend fun connectDatabase() =
        catching {
            connection = DriverManager.getConnection(("jdbc:sqlite:${_dbPath}"))
            return@catching isInitialized
        }


    /**
     * Here we launch our tasks async
     *
     * Also the [Callback] implementation
     */
    suspend fun onEnable() {
        AsyncHelper.launch {
            connectDatabase()
            if (isInitialized)
                Logger.log(Translation.dbSuccess, "Database")
            else {
                Logger.error(Translation.dbFail, "Database")
                return@launch
            }
            val result = DatabaseApi.createUserTable()
            if (result != null) {
                val user = User("id${Random.nextInt(20000)}", "mine${Random.nextInt(5000)}")
                DatabaseApi.insertUser(user)
                val users = DatabaseApi.getAllUsers()
                Logger.log("Users: ${users}", "Database")
            } else
                Logger.warn("Could not create table", "Database")

        }
    }


    public suspend fun onDisable() {
        connection.close()
    }


}


