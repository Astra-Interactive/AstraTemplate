package ru.astrainteractive.astratemplate.gui

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.ItemStack
import ru.astrainteractive.astralibs.menu.clicker.Click
import ru.astrainteractive.astralibs.menu.clicker.MenuClickListener
import ru.astrainteractive.astralibs.menu.holder.DefaultPlayerHolder
import ru.astrainteractive.astralibs.menu.holder.PlayerHolder
import ru.astrainteractive.astralibs.menu.menu.InventorySlot
import ru.astrainteractive.astralibs.menu.menu.MenuSize
import ru.astrainteractive.astralibs.menu.menu.PaginatedMenu
import ru.astrainteractive.astratemplate.api.dto.UserDTO
import ru.astrainteractive.astratemplate.gui.di.SampleGuiModule
import ru.astrainteractive.astratemplate.gui.store.InventoryStore.State

class SampleGUI(
    player: Player,
    module: SampleGuiModule
) : PaginatedMenu(), SampleGuiModule by module {
    private val viewModel = module.viewModelFactory.create()

    private val clickListener = MenuClickListener()

    fun createItemStackWithName(material: Material, name: String) = ItemStack(material).apply {
        val meta = itemMeta
        meta.setDisplayName(name)
        itemMeta = meta
    }

    override val playerHolder: PlayerHolder = DefaultPlayerHolder(player)
    override var menuTitle: String = translation.menu.menuTitle
    override val menuSize: MenuSize = MenuSize.XL
    override var maxItemsPerPage: Int = 45
    override var page: Int = 0
    override val maxItemsAmount: Int
        get() = when (val state = viewModel.inventoryState.value) {
            is State.Items -> state.items.size
            is State.Users -> state.users.size
            State.Loading -> 0
        }

    private fun button(
        index: Int,
        item: ItemStack,
        onClick: Click
    ) = InventorySlot.Builder {
        this.index = index
        this.click = onClick
        itemStack = item
    }

    private val changeModeButton: InventorySlot
        get() = InventorySlot.Builder {
            index = 50
            click = Click {
                viewModel.onModeChange()
            }
            itemStack = when (viewModel.inventoryState.value) {
                is State.Items -> createItemStackWithName(Material.SUNFLOWER, "Items")
                State.Loading -> createItemStackWithName(Material.SUNFLOWER, "Loading")
                is State.Users -> createItemStackWithName(Material.SUNFLOWER, "Users")
            }
        }

    private val addUserButton = button(48, createItemStackWithName(Material.EMERALD, translation.menu.menuAddPlayer)) {
        viewModel.onAddUserClicked()
    }
    override val backPageButton = button(49, createItemStackWithName(Material.PAPER, translation.menu.menuClose)) {
        inventory.close()
    }
    override val nextPageButton = button(53, createItemStackWithName(Material.PAPER, translation.menu.menuNextPage)) {
        showPage(page + 1)
    }
    override val prevPageButton = button(45, createItemStackWithName(Material.PAPER, translation.menu.menuPrevPage)) {
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
        viewModel.inventoryState.collectOn(dispatchers.BukkitMain, ::onStateChanged)
    }

    private fun onStateChanged(state: State = viewModel.inventoryState.value) {
        inventory.clear()
        clickListener.clearClickListener()
        changeModeButton.apply {
            clickListener.remember(this)
            setInventoryButton()
        }

        setManageButtons(clickListener)

        when (state) {
            is State.Items -> {
                setItemStacks(state.items)
            }

            is State.Users -> {
                addUserButton.setInventoryButton()
                clickListener.remember(addUserButton)
                setUsers(state.users)
            }

            State.Loading -> {}
        }
    }

    private fun setUsers(list: List<UserDTO>) {
        for (i in 0 until maxItemsPerPage) {
            val index = maxItemsPerPage * page + i
            if (index >= list.size) {
                continue
            }
            val user = list[index]
            val button = InventorySlot.Builder {
                this.index = i
                this.click = Click {
                    viewModel.onItemClicked(i, it.click)
                }
                itemStack = ItemStack(Material.PLAYER_HEAD).apply {
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
            val button = InventorySlot.Builder {
                this.index = i
                this.itemStack = itemStack
                this.click = Click {
                    viewModel.onItemClicked(i, it.click)
                }
            }
            clickListener.remember(button)
            button.setInventoryButton()
        }
    }
}
