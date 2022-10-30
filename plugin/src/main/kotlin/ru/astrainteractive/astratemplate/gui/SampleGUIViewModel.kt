package ru.astrainteractive.astratemplate.gui

import ru.astrainteractive.astratemplate.api.ItemStackSpigotAPI
import com.astrainteractive.astratemplate.domain.Repository
import com.astrainteractive.astratemplate.domain.local.dto.UserDTO
import com.astrainteractive.astratemplate.domain.local.entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.bukkit.ChatColor
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.ItemStack
import ru.astrainteractive.astralibs.Logger
import ru.astrainteractive.astralibs.architecture.ViewModel
import kotlin.random.Random


/**
 * MVVM/MVI technique
 */
class SampleGUIViewModel(
    private val repository: Repository,
    private val itemStackSpigotAPi: ItemStackSpigotAPI
) : ViewModel() {

    val inventoryState = MutableStateFlow<InventoryState>(InventoryState.Loading)

    val randomColor: ChatColor
        get() = ChatColor.values()[Random.nextInt(ChatColor.values().size)]

    fun onModeChange() = viewModelScope.launch(Dispatchers.IO) {
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
        viewModelScope.launch(Dispatchers.IO) {
            repository?.insertUser(UserDTO(-1, "id${Random.nextInt(20000)}", "mine${Random.nextInt(5000)}"))
            loadUsersState()
        }
    }


    private fun onPlayerHeadClicked(slot: Int, clickType: ClickType) {
        val state = inventoryState.value as? InventoryState.Users ?: return
        val users = state.users
        val user = users.getOrNull(slot) ?: return
        viewModelScope.launch(Dispatchers.IO) {
            when (clickType) {
                ClickType.MIDDLE -> repository?.updateUser(user)
                ClickType.LEFT -> repository?.deleteUser(user)
                else -> {
                    println(repository?.selectRating(user))
                    repository?.insertRating(user)
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
        inventoryState.value = InventoryState.Users(repository.getAllUsers() ?: emptyList())
    }

    fun onUiCreated() = viewModelScope.launch(Dispatchers.IO) {
        Logger.log("onUiCreated")
        delay(1000)
        loadItemsState()
    }
}
