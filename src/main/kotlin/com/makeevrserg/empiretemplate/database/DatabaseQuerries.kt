package com.makeevrserg.empiretemplate.database

import com.makeevrserg.empiretemplate.database.entities.User
import java.sql.DriverManager
import java.sql.SQLException
/**
 * Class for getting items from database
 */
class DatabaseQuerries {
    companion object{
        /**
         * Just for minimize code
         */
        private fun connection() = EmpireDatabase.connection
        /**
         * Function for creating user table
         */
        public fun createUserTable(): Boolean {
            return try {
                connection().
                prepareStatement("CREATE TABLE IF NOT EXISTS USER(discord_id varchar(32),mincraft_uuid varchar(16))")
                    .execute()
                true
            } catch (ex: SQLException) {
                false
            }
        }
        /**
         * Function for get all users from database
         */
        public fun getUsers(): MutableList<User> {
            return try {
                val result = connection().createStatement().executeQuery("SELECT * from USER")
                val users = mutableListOf<User>()
                while (result.next())
                    users.add(User(result))
                users
            } catch (ex: SQLException) {
                return mutableListOf()
            }
        }

        /**
         * Function for creating user
         */
        public fun createUser(user: User): Boolean {
            return try {
                connection().prepareStatement("INSERT INTO USER (name, age) VALUES('${user.name}','${user.age}')")
                    .execute()
                true
            } catch (ex: SQLException) {
                false
            }
        }


    }
}