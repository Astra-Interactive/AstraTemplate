package ru.astrainteractive.astratemplate.feature.gui.button

import net.kyori.adventure.text.Component
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import ru.astrainteractive.astralibs.menu.clicker.Click
import ru.astrainteractive.astralibs.menu.slot.InventorySlot
import ru.astrainteractive.astralibs.menu.slot.setDisplayName
import ru.astrainteractive.astralibs.menu.slot.setIndex
import ru.astrainteractive.astralibs.menu.slot.setItemStack
import ru.astrainteractive.astralibs.menu.slot.setLore
import ru.astrainteractive.astralibs.menu.slot.setMaterial
import ru.astrainteractive.astralibs.menu.slot.setOnClickListener
import ru.astrainteractive.astratemplate.api.local.model.UserModel
import ru.astrainteractive.astratemplate.feature.gui.button.di.ButtonContext

internal fun ButtonContext.itemSlot(
    index: Int,
    itemStack: ItemStack,
    click: Click
) = InventorySlot.Builder()
    .setIndex(index)
    .setItemStack(itemStack)
    .setOnClickListener(click)
    .build()

internal fun ButtonContext.userSlot(
    index: Int,
    user: UserModel,
    randomColor: ChatColor,
    click: Click
) = InventorySlot.Builder()
    .setIndex(index)
    .setMaterial(Material.PLAYER_HEAD)
    .setDisplayName(user.id.toString())
    .setLore(
        listOf(
            "${randomColor}discordID: ${user.discordId}",
            "${randomColor}minecraftUUID: ${user.minecraftUUID}",
            "${randomColor}Press LeftClick to delete user",
            "${randomColor}Press MiddleClick to update user",
            "${randomColor}Press RightClick to add relation"
        ).map(Component::text)
    )
    .setOnClickListener(click)
    .build()
