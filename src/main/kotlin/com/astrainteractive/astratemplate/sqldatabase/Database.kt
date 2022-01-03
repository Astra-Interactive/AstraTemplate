package com.astrainteractive.astratemplate.sqldatabase

import com.astrainteractive.astralibs.Logger
import com.astrainteractive.astralibs.catching
import com.astrainteractive.astratemplate.AstraTemplate
import com.astrainteractive.astratemplate.sqldatabase.entities.User
import com.astrainteractive.astratemplate.utils.AsyncTask
import com.astrainteractive.astratemplate.utils.Translation
import com.astrainteractive.astratemplate.utils.callbackCatching
import kotlinx.coroutines.launch
import java.io.File
import java.lang.Exception
import java.sql.Connection
import java.sql.DriverManager
import kotlin.random.Random


/**
 * Database for plugin
 */
class Database : AsyncTask {
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
    fun onEnable() {
        launch {
            connectDatabase()
            if (isInitialized)
                Logger.log(Translation.instance.dbSuccess, "Database")
            else {
                Logger.error(Translation.instance.dbFail, "Database")
                return@launch
            }
            Repository.createUserTable(object : Callback() {
                override suspend fun <T> onSuccess(result: T?) {
                    val user = User("id${Random.nextInt(20000)}", "mine${Random.nextInt(5000)}")
                    Repository.insertUser(user)
                    val users = Repository.getAllUsers()
                    Logger.log("Users: ${users}", "Database")
                }

                override suspend fun onFailure(e: Exception) {
                    Logger.warn("${e.message}", "Database")
                }
            })

        }
    }


    public fun onDisable() {
        connection.close()
    }


}


