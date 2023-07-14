package ru.astrainteractive.astratemplate.gui

import org.bukkit.inventory.ItemStack
import ru.astrainteractive.astratemplate.api.dto.UserDTO

sealed interface InventoryState {
    data class Items(
        val items: List<ItemStack>
    ) : InventoryState

    data class Users(
        val users: List<UserDTO>
    ) : InventoryState

    object Loading : InventoryState
}
