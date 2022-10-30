package com.astrainteractive.astratemplate.gui

import com.astrainteractive.astratemplate.api.ItemStackSpigotAPI
import com.astrainteractive.astratemplate.domain.Repository
import com.astrainteractive.astratemplate.domain.local.entities.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.bukkit.ChatColor
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.ItemStack
import ru.astrainteractive.astralibs.architecture.ViewModel
import ru.astrainteractive.astralibs.async.PluginScope
import ru.astrainteractive.astralibs.utils.next
import kotlin.random.Random



/**
 * MVVM technique
 */
class SampleGUIViewModel(private val repository: Repository, private val itemStackSpigotAPi: ItemStackSpigotAPI) :
    ViewModel() {
    private val _items = MutableStateFlow(itemStackSpigotAPi.randomItemStackList())
    val items: StateFlow<List<ItemStack>>
        get() = _items
    private val _mode = MutableStateFlow(Mode.ITEMS)
    val mode: StateFlow<Mode>
        get() = _mode
    private val _users = MutableStateFlow(runBlocking { repository?.getAllUsers() ?: emptyList() })
    val users: StateFlow<List<User>>
        get() = _users


    val randomColor: ChatColor
        get() = ChatColor.values()[Random.nextInt(ChatColor.values().size)]

    fun onModeChange() {
        _mode.value = mode.value.next()
    }

    fun onItemClicked(slot: Int, clickType: ClickType) {
        if (mode.value == Mode.ITEMS)
            onItemStackClicked(slot)
        else onPlayerHeadClicked(slot, clickType)
    }

    fun onAddUserClicked() {
        viewModelScope.launch {
            repository?.insertUser(User(-1, "id${Random.nextInt(20000)}", "mine${Random.nextInt(5000)}"))
            _users.value = repository?.getAllUsers() ?: emptyList()
        }
    }


    private fun onPlayerHeadClicked(slot: Int, clickType: ClickType) {
        val user = users.value[slot]
        viewModelScope.launch {
            when (clickType) {
                ClickType.MIDDLE -> repository?.updateUser(user)
                ClickType.LEFT -> repository?.deleteUser(user)
                else -> {
                    println(repository?.selectRating(user))
                    repository?.insertRating(user)
                }
            }
            _users.value = repository?.getAllUsers() ?: emptyList()
        }
    }

    private fun onItemStackClicked(slot: Int) {
        val list = _items.value.toMutableList()
        val item = list[slot].clone().apply {
            editMeta {
                it.setDisplayName(randomColor.toString() + this.type.name)
            }
        }
        list[slot] = item
        _items.value = list.toList()
    }

    fun onDisable() {

    }
}
