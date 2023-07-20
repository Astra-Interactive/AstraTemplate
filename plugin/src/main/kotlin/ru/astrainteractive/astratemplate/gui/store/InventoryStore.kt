package ru.astrainteractive.astratemplate.gui.store

import org.bukkit.inventory.ItemStack
import ru.astrainteractive.astratemplate.api.dto.UserDTO

interface InventoryStore {
    sealed interface State {
        data class Items(
            val items: List<ItemStack>
        ) : State

        data class Users(
            val users: List<UserDTO>
        ) : State

        data object Loading : State
    }
}
