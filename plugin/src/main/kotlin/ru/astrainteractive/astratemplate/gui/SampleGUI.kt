package ru.astrainteractive.astratemplate.gui

import com.astrainteractive.astratemplate.api.dto.UserDTO
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.ItemStack
import ru.astrainteractive.astralibs.di.Dependency
import ru.astrainteractive.astralibs.di.getValue
import ru.astrainteractive.astralibs.menu.holder.DefaultPlayerHolder
import ru.astrainteractive.astralibs.menu.holder.PlayerHolder
import ru.astrainteractive.astralibs.menu.menu.PaginatedMenu
import ru.astrainteractive.astralibs.menu.utils.InventoryButton
import ru.astrainteractive.astralibs.menu.utils.ItemStackButtonBuilder
import ru.astrainteractive.astralibs.menu.utils.MenuSize
import ru.astrainteractive.astralibs.menu.utils.click.MenuClickListener
import ru.astrainteractive.astratemplate.plugin.Translation

class SampleGUI(
    player: Player,
    translationModule: Dependency<Translation>,
    private val viewModel: SampleGUIViewModel
) : PaginatedMenu() {
    private val translation by translationModule

    private val clickListener = MenuClickListener()

    fun createItemStackWithName(material: Material, name: String) = ItemStack(material).apply {
        val meta = itemMeta
        meta.setDisplayName(name)
        itemMeta = meta
    }

    override val playerHolder: PlayerHolder = DefaultPlayerHolder(player)
    override var menuTitle: String = translation.menuTitle
    override val menuSize: MenuSize = MenuSize.XL
    override var maxItemsPerPage: Int = 45
    override var page: Int = 0
    override val maxItemsAmount: Int
        get() = when (val state = viewModel.inventoryState.value) {
            is InventoryState.Items -> state.items.size
            is InventoryState.Users -> state.users.size
            InventoryState.Loading -> 0
        }

    private fun button(
        index: Int,
        item: ItemStack,
        onClick: (e: InventoryClickEvent) -> Unit
    ) = ItemStackButtonBuilder {
        this.index = index
        this.onClick = onClick
        itemStack = item
    }

    private val changeModeButton: InventoryButton
        get() = ItemStackButtonBuilder {
            index = 50
            onClick = {
                viewModel.onModeChange()
            }
            itemStack = when (viewModel.inventoryState.value) {
                is InventoryState.Items -> createItemStackWithName(Material.SUNFLOWER, "Items")
                InventoryState.Loading -> createItemStackWithName(Material.SUNFLOWER, "Loading")
                is InventoryState.Users -> createItemStackWithName(Material.SUNFLOWER, "Users")
            }
        }

    private val addUserButton = button(48, createItemStackWithName(Material.EMERALD, translation.menuAddPlayer)) {
        viewModel.onAddUserClicked()
    }
    override val backPageButton = button(49, createItemStackWithName(Material.PAPER, translation.menuClose)) {
        inventory.close()
    }
    override val nextPageButton = button(53, createItemStackWithName(Material.PAPER, translation.menuNextPage)) {
        showPage(page + 1)
    }
    override val prevPageButton = button(45, createItemStackWithName(Material.PAPER, translation.menuPrevPage)) {
        showPage(page - 1)
    }

    override fun onInventoryClose(it: InventoryCloseEvent) {
        viewModel.close()
    }

    override fun onPageChanged() {
        onStateChanged()
    }

    override fun onInventoryClicked(e: InventoryClickEvent) {
        e.isCancelled = true
        clickListener.onClick(e)
    }

    override fun onCreated() {
        viewModel.onUiCreated()
        viewModel.inventoryState.collectOn(block = ::onStateChanged)
    }

    private fun onStateChanged(state: InventoryState = viewModel.inventoryState.value) {
        inventory.clear()
        clickListener.clearClickListener()
        changeModeButton.apply {
            clickListener.remember(this)
            setInventoryButton()
        }

        setManageButtons(clickListener)

        when (state) {
            is InventoryState.Items -> {
                setItemStacks(state.items)
            }

            is InventoryState.Users -> {
                addUserButton.setInventoryButton()
                clickListener.remember(addUserButton)
                setUsers(state.users)
            }

            InventoryState.Loading -> {}
        }
    }

    private fun setUsers(list: List<UserDTO>) {
        for (i in 0 until maxItemsPerPage) {
            val index = maxItemsPerPage * page + i
            if (index >= list.size) {
                continue
            }
            val user = list[index]
            val button = ItemStackButtonBuilder {
                this.index = i
                this.onClick = {
                    viewModel.onItemClicked(i, it.click)
                }
                itemStack {
                    this.type = Material.PLAYER_HEAD
                    editMeta {
                        it.setDisplayName(user.id.toString())
                        it.lore = listOf(
                            "${viewModel.randomColor}discordID: ${user.discordId}",
                            "${viewModel.randomColor}minecraftUUID: ${user.minecraftUUID}",
                            "${viewModel.randomColor}Press LeftClick to delete user",
                            "${viewModel.randomColor}Press MiddleClick to delete user",
                            "${viewModel.randomColor}Press RightClick to Add Relation"
                        )
                    }
                }
            }
            clickListener.remember(button)
            button.setInventoryButton()
        }
    }

    private fun setItemStacks(list: List<ItemStack>) {
        for (i in 0 until maxItemsPerPage) {
            val index = maxItemsPerPage * page + i
            if (index >= list.size) {
                continue
            }
            val itemStack = list[index]
            val button = ItemStackButtonBuilder {
                this.index = i
                this.itemStack = itemStack
                this.onClick = {
                    viewModel.onItemClicked(i, it.click)
                }
            }
            clickListener.remember(button)
            button.setInventoryButton()
        }
    }
}
