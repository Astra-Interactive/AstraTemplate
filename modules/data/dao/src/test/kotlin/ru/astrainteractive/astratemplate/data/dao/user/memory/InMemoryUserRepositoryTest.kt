@file:Suppress("FunctionNaming")

package ru.astrainteractive.astratemplate.data.dao.user.memory

import kotlinx.coroutines.test.runTest
import ru.astrainteractive.astratemplate.data.dao.user.model.User
import kotlin.test.Test
import kotlin.test.assertEquals

class InMemoryUserRepositoryTest {
    private val repository = InMemoryUserRepository()

    private fun newUser(discordId: String): User = User(id = -1, discordId = discordId, minecraftUUID = "uuid-$discordId")

    @Test
    fun GIVEN_two_new_users_WHEN_inserted_THEN_they_get_increasing_ids() = runTest {
        val alexId = repository.insertUser(newUser("alex"))
        val steveId = repository.insertUser(newUser("steve"))

        assertEquals(listOf(alexId, steveId), repository.getAllUsers().map(User::id))
        assertEquals(alexId + 1, steveId)
    }

    @Test
    fun GIVEN_stored_user_WHEN_updated_THEN_listed_user_has_new_values() = runTest {
        val id = repository.insertUser(newUser("alex"))
        val updated = User(id = id, discordId = "steve", minecraftUUID = "uuid-steve")

        repository.updateUser(updated)

        assertEquals(listOf(updated), repository.getAllUsers())
    }

    @Test
    fun GIVEN_two_users_WHEN_one_is_deleted_THEN_only_the_other_is_listed() = runTest {
        val alexId = repository.insertUser(newUser("alex"))
        val steveId = repository.insertUser(newUser("steve"))

        repository.deleteUser(User(id = alexId, discordId = "alex", minecraftUUID = "uuid-alex"))

        assertEquals(listOf(steveId), repository.getAllUsers().map(User::id))
    }

    @Test
    fun GIVEN_ratings_of_two_users_WHEN_ratings_are_selected_THEN_only_that_users_ratings_return() = runTest {
        val alex = newUser("alex").copy(id = repository.insertUser(newUser("alex")))
        val steve = newUser("steve").copy(id = repository.insertUser(newUser("steve")))
        val ratingId = repository.insertRating(alex)
        repository.insertRating(steve)

        assertEquals(listOf(ratingId), repository.selectRating(alex).map { rating -> rating.id })
    }
}
