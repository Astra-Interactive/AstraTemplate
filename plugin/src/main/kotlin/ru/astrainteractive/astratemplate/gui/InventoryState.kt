package ru.astrainteractive.astratemplate.gui

import com.astrainteractive.astratemplate.api.dto.UserDTO
import org.bukkit.inventory.ItemStack

sealed interface InventoryState {
    data class Items(
        val items: List<ItemStack>
    ) : InventoryState

    data class Users(
        val users: List<UserDTO>
    ) : InventoryState

    object Loading : InventoryState
}
