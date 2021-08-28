package com.makeevrserg.empiretemplate.database

import com.makeevrserg.empiretemplate.EmpireTemplate
import com.makeevrserg.empiretemplate.database.entities.User
import java.io.File
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

/**
 * Database for plugin
 *
 * Not fully functional!
 */
@Deprecated("Not fully working")
class EmpireDatabase {


    /**
     * Path for your plugin database
     *
     * Should be private
     */
    private val dbPath = "${EmpireTemplate.instance.dataFolder}${File.separator}data.db"

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
    private fun connectDatabase(): Boolean {
        return try {
            connection = DriverManager.getConnection("jdbc:sqlite:$dbPath")
            true
        } catch (ex: SQLException) {
            false
        }
    }

    /**
     * Demonstrating of creating user
     */
    @Deprecated("Only for demonstation purposes")
    private fun createUser() {
        DatabaseQuerries.createUser(User("RomaRoman", 21))
    }

    /**
     * Demonstrating of getting all users
     */
    @Deprecated("Only for demonstation purposes")
    private fun getUsers() {
        DatabaseQuerries.getUsers()
    }

    /**
     * Initialization for your database
     */
    private fun initDatabase() {
        if (connectDatabase())
            println(EmpireTemplate.translations.DB_SUCCESS)
        else {
            println(EmpireTemplate.translations.DB_FAIL)
        }
        createUser()
        getUsers()
    }

    init {
        initDatabase()
    }

    public fun onDisable() {
        connection.close()
    }

}
