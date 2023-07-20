package ru.astrainteractive.astratemplate.gui

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.bukkit.ChatColor
import org.bukkit.event.inventory.ClickType
import ru.astrainteractive.astralibs.async.AsyncComponent
import ru.astrainteractive.astratemplate.api.ItemStackSpigotAPI
import ru.astrainteractive.astratemplate.api.dto.UserDTO
import ru.astrainteractive.astratemplate.api.local.LocalApi
import ru.astrainteractive.astratemplate.gui.store.InventoryStore.State
import kotlin.random.Random

/**
 * MVVM/MVI technique
 */
class SampleGUIViewModel(
    private val localApi: LocalApi,
    private val itemStackSpigotAPi: ItemStackSpigotAPI
) : AsyncComponent() {

    val inventoryState = MutableStateFlow<State>(State.Loading)

    val randomColor: ChatColor
        get() = ChatColor.values()[Random.nextInt(ChatColor.values().size)]

    fun onModeChange() = componentScope.launch(Dispatchers.IO) {
        println("OnModeChanged")
        when (inventoryState.value) {
            State.Loading -> return@launch
            is State.Items -> loadUsersState()
            is State.Users -> loadItemsState()
        }
    }

    fun onItemClicked(slot: Int, clickType: ClickType) = when (val state = inventoryState.value) {
        State.Loading -> {}
        is State.Items -> {
            onItemStackClicked(slot)
        }

        is State.Users -> {
            onPlayerHeadClicked(slot, clickType)
        }
    }

    fun onAddUserClicked() {
        componentScope.launch(Dispatchers.IO) {
            localApi.insertUser(UserDTO(-1, "id${Random.nextInt(20000)}", "mine${Random.nextInt(5000)}"))
            loadUsersState()
        }
    }

    private fun onPlayerHeadClicked(slot: Int, clickType: ClickType) {
        val state = inventoryState.value as? State.Users ?: return
        val users = state.users
        val user = users.getOrNull(slot) ?: return
        componentScope.launch(Dispatchers.IO) {
            when (clickType) {
                ClickType.MIDDLE -> localApi.updateUser(user)
                ClickType.LEFT -> localApi.deleteUser(user)
                else -> {
                    println(localApi.selectRating(user))
                    localApi.insertRating(user)
                }
            }
            loadUsersState()
        }
    }

    private fun onItemStackClicked(slot: Int) {
        val state = inventoryState.value as? State.Items ?: return

        val list = state.items.toMutableList()
        val item = list.getOrNull(slot)?.clone()?.apply {
            editMeta {
                it.setDisplayName(randomColor.toString() + this.type.name)
            }
        } ?: return
        list[slot] = item
        this.inventoryState.update {
            state.copy(items = list)
        }
    }

    suspend fun loadItemsState() {
        inventoryState.value = State.Items(itemStackSpigotAPi.randomItemStackList())
    }

    suspend fun loadUsersState() {
        inventoryState.value = State.Users(localApi.getAllUsers() ?: emptyList())
    }

    fun onUiCreated() = componentScope.launch(Dispatchers.IO) {
        delay(1000)
        loadItemsState()
    }
}
