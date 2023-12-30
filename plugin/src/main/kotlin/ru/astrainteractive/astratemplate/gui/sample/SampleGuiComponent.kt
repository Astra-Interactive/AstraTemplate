package ru.astrainteractive.astratemplate.gui.sample

import kotlinx.coroutines.flow.StateFlow
import org.bukkit.ChatColor
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.ItemStack
import ru.astrainteractive.astratemplate.api.local.model.UserModel

interface SampleGuiComponent {
    val model: StateFlow<Model>

    val randomColor: ChatColor

    fun onModeChange()

    fun onItemClicked(slot: Int, clickType: ClickType)

    fun onAddUserClicked()

    fun onUiCreated()

    sealed interface Model {
        data class Items(
            val items: List<ItemStack>
        ) : Model

        data class Users(
            val users: List<UserModel>
        ) : Model

        data object Loading : Model
    }
}
