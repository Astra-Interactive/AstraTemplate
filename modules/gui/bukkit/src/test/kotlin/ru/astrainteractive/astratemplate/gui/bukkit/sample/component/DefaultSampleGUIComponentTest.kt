@file:Suppress("FunctionNaming")

package ru.astrainteractive.astratemplate.gui.bukkit.sample.component

import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import org.bukkit.event.inventory.ClickType
import ru.astrainteractive.astratemplate.data.dao.user.memory.InMemoryUserRepository
import kotlin.random.Random
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.seconds

class DefaultSampleGUIComponentTest {
    private val repository = InMemoryUserRepository()
    private val component = DefaultSampleGUIComponent(
        userRepository = repository,
        random = Random(seed = 1)
    )

    private suspend fun awaitUsers(count: Int): SampleGuiComponent.Model.Users {
        return withTimeout(5.seconds) {
            component.model
                .first { model -> model is SampleGuiComponent.Model.Users && model.users.size == count }
                as SampleGuiComponent.Model.Users
        }
    }

    @AfterTest
    fun tearDown() {
        component.cancel()
    }

    @Test
    fun GIVEN_loading_menu_WHEN_mode_changes_THEN_menu_keeps_loading() {
        component.onModeChange()
        component.onItemClicked(slot = 0, clickType = ClickType.LEFT)

        assertEquals(SampleGuiComponent.Model.Loading, component.model.value)
    }

    @Test
    fun GIVEN_menu_WHEN_add_user_is_clicked_THEN_new_user_is_stored_and_shown() = runBlocking {
        component.onAddUserClicked()

        val users = awaitUsers(count = 1).users

        assertEquals(repository.getAllUsers(), users)
        assertTrue(users.single().discordId.startsWith("id"))
    }

    @Test
    fun GIVEN_shown_user_WHEN_left_clicked_THEN_user_is_deleted() = runBlocking {
        component.onAddUserClicked()
        awaitUsers(count = 1)

        component.onItemClicked(slot = 0, clickType = ClickType.LEFT)

        awaitUsers(count = 0)
        assertTrue(repository.getAllUsers().isEmpty())
    }

    @Test
    fun GIVEN_shown_user_WHEN_right_clicked_THEN_rating_is_added_for_that_user() = runBlocking {
        component.onAddUserClicked()
        val user = awaitUsers(count = 1).users.single()

        component.onItemClicked(slot = 0, clickType = ClickType.RIGHT)

        withTimeout(5.seconds) {
            while (repository.selectRating(user).isEmpty()) delay(10)
        }
        assertEquals(1, repository.selectRating(user).size)
    }
}
