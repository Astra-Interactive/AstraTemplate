package com.makeevrserg.empiretemplate.database

import com.makeevrserg.empiretemplate.database.entities.User
import com.makeevrserg.empiretemplate.database.entities.Users
import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.DriverManager
import java.sql.SQLException

/**
 * Class for getting items from database
 */
object DatabaseQuerries {

    /**
     * Creating user
     */
    fun createUser(discordId: String, minecraftUuid: String) {
        transaction {
            User.new {
                this.discordId = discordId
                this.minecraftUuid = minecraftUuid
            }
        }
    }

    /**
     * Getting all users
     */
    fun getAllUsers(): SizedIterable<User> {
        return transaction {
            return@transaction User.all()
        }
    }

    /**
     * Get user by discord id
     */
    fun getUserByDiscordId(id: String): User? {
        return transaction {
            return@transaction User.find {
                Users.discordId eq id
            }.firstOrNull()
        }
    }

    /**
     * Delete user from database
     */
    fun removeUserByDiscordId(id:String){
        transaction {
            val user = getUserByDiscordId(id)?.delete()?:return@transaction
        }
    }


}