@file:Suppress("FunctionNaming")

package ru.astrainteractive.astratemplate.data.exposed.user

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.TransactionManager
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import ru.astrainteractive.astratemplate.data.dao.user.model.User
import ru.astrainteractive.astratemplate.data.exposed.user.table.UserRatingTable
import ru.astrainteractive.astratemplate.data.exposed.user.table.UserTable
import java.io.File
import kotlin.io.path.createTempFile
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ExposedUserRepositoryTest {
    private lateinit var databaseFile: File
    private lateinit var database: Database
    private lateinit var repository: ExposedUserRepository

    @BeforeTest
    fun setUp() {
        databaseFile = createTempFile(prefix = "template_users_test", suffix = ".db").toFile()
        database = Database.connect(
            url = "jdbc:sqlite:${databaseFile.absolutePath}",
            driver = "org.sqlite.JDBC"
        )
        transaction(database) {
            SchemaUtils.create(UserRatingTable, UserTable)
        }
        repository = ExposedUserRepository(databaseFlow = flowOf(database))
    }

    @AfterTest
    fun tearDown() {
        TransactionManager.closeAndUnregister(database)
        databaseFile.delete()
    }

    private fun newUser(discordId: String): User {
        return User(id = -1, discordId = discordId, minecraftUUID = "uuid-$discordId")
    }

    @Test
    fun GIVEN_new_user_WHEN_inserted_THEN_it_is_listed_with_its_new_id() = runTest {
        val id = repository.insertUser(newUser("alex"))

        assertEquals(listOf(User(id = id, discordId = "alex", minecraftUUID = "uuid-alex")), repository.getAllUsers())
    }

    @Test
    fun GIVEN_stored_user_WHEN_updated_THEN_listed_user_has_new_values() = runTest {
        val id = repository.insertUser(newUser("alex"))

        repository.updateUser(User(id = id, discordId = "steve", minecraftUUID = "uuid-steve"))

        assertEquals(listOf(User(id = id, discordId = "steve", minecraftUUID = "uuid-steve")), repository.getAllUsers())
    }

    @Test
    fun GIVEN_two_users_WHEN_one_is_deleted_THEN_only_the_other_is_listed() = runTest {
        val alexId = repository.insertUser(newUser("alex"))
        val steveId = repository.insertUser(newUser("steve"))

        repository.deleteUser(User(id = alexId, discordId = "alex", minecraftUUID = "uuid-alex"))

        assertEquals(listOf(steveId), repository.getAllUsers().map(User::id))
    }

    @Test
    fun GIVEN_user_with_ratings_WHEN_ratings_are_selected_THEN_only_that_users_ratings_return() = runTest {
        val alex = newUser("alex").let { user -> user.copy(id = repository.insertUser(user)) }
        val steve = newUser("steve").let { user -> user.copy(id = repository.insertUser(user)) }
        val firstRatingId = repository.insertRating(alex)
        val secondRatingId = repository.insertRating(alex)
        repository.insertRating(steve)

        val ratings = repository.selectRating(alex)

        assertEquals(listOf(firstRatingId, secondRatingId), ratings.map { rating -> rating.id })
        assertTrue(ratings.all { rating -> rating.userID == alex.id })
    }
}
