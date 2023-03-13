package ru.astrainteractive.astratemplate.gui

import com.astrainteractive.astratemplate.api.dto.UserDTO
import com.astrainteractive.astratemplate.api.local.LocalApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.bukkit.ChatColor
import org.bukkit.event.inventory.ClickType
import ru.astrainteractive.astralibs.Logger
import ru.astrainteractive.astralibs.async.AsyncComponent
import ru.astrainteractive.astratemplate.api.ItemStackSpigotAPI
import kotlin.random.Random

/**
 * MVVM/MVI technique
 */
class SampleGUIViewModel(
    private val localApi: LocalApi,
    private val itemStackSpigotAPi: ItemStackSpigotAPI
) : AsyncComponent() {

    val inventoryState = MutableStateFlow<InventoryState>(InventoryState.Loading)

    val randomColor: ChatColor
        get() = ChatColor.values()[Random.nextInt(ChatColor.values().size)]

    fun onModeChange() = componentScope.launch(Dispatchers.IO) {
        println("OnModeChanged")
        when (inventoryState.value) {
            InventoryState.Loading -> return@launch
            is InventoryState.Items -> loadUsersState()
            is InventoryState.Users -> loadItemsState()
        }
    }

    fun onItemClicked(slot: Int, clickType: ClickType) = when (val state = inventoryState.value) {
        InventoryState.Loading -> {}
        is InventoryState.Items -> {
            onItemStackClicked(slot)
        }

        is InventoryState.Users -> {
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
        val state = inventoryState.value as? InventoryState.Users ?: return
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
        val state = inventoryState.value as? InventoryState.Items ?: return

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
        inventoryState.value = InventoryState.Items(itemStackSpigotAPi.randomItemStackList())
    }

    suspend fun loadUsersState() {
        inventoryState.value = InventoryState.Users(localApi.getAllUsers() ?: emptyList())
    }

    fun onUiCreated() = componentScope.launch(Dispatchers.IO) {
        Logger.log("SampleGuiViewModel", "onUiCreated")
        delay(1000)
        loadItemsState()
    }
}
