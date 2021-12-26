package com.astrainteractive.astratemplate.sqldatabase

import com.astrainteractive.astralibs.catching
import com.astrainteractive.astratemplate.AstraTemplate
import com.astrainteractive.astratemplate.sqldatabase.entities.User
import java.io.File
import java.sql.Connection
import java.sql.DriverManager
import kotlin.random.Random


/**
 * Database for plugin
 *
 * Not fully functional!
 */
class Database {


    /**
     * Path for your plugin database
     *
     * Should be private
     */
    private val _dbPath = "${AstraTemplate.instance.dataFolder}${File.separator}data.db"


    /**
     * Connection for your database.
     *
     * You should call this object only from DatabaseQuerries
     * @See DatabaseQuerries
     */
    companion object {
        lateinit var connection: Connection
    }

    /**
     * Function for connecting to local database
     */
    private fun connectDatabase() =
        catching {
            connection = DriverManager.getConnection(("jdbc:sqlite:${_dbPath}"))
            return@catching true
        }



    init {

        connectDatabase()
        Repository.createUserTable()
        val user = User("id${Random.nextInt(20000)}", "mine${Random.nextInt(5000)}")
        Repository.insertUser(user)
        Repository.getAllUsers()
    }

    public fun onDisable() {
        connection.close()
    }


}


