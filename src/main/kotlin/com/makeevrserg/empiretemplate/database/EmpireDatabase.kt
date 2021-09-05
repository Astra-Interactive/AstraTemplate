package com.makeevrserg.empiretemplate.database

import com.makeevrserg.empiretemplate.EmpireTemplate
import com.makeevrserg.empiretemplate.database.entities.User
import com.makeevrserg.empiretemplate.database.entities.Users
import org.bukkit.ChatColor
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File
import java.lang.annotation.ElementType
import java.lang.annotation.RetentionPolicy
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import kotlin.random.Random
import kotlin.reflect.KClass


/**
 * Database for plugin
 *
 * Not fully functional!
 */
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
        lateinit var connection: Database
    }

    /**
     * Function for connecting to local database
     */
    private fun connectDatabase() {

        connection = Database.connect("jdbc:sqlite:${dbPath}", "org.sqlite.JDBC")
        TransactionManager.manager.defaultIsolationLevel =
            Connection.TRANSACTION_SERIALIZABLE


    }


    init {
        connectDatabase()
        DatabaseQuerries.createUser("id${Random.nextInt(20000)}","mine${Random.nextInt(5000)}")
        transaction {
            for (user in User.all()){
                println("${ChatColor.RED}${user.discordId} ${user.minecraftUuid}")
            }
        }


    }

    public fun onDisable() {

        TransactionManager.managerFor(connection)?.currentOrNull()?.close()
    }

}
