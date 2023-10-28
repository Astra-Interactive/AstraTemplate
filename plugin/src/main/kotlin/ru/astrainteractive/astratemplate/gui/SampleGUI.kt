package ru.astrainteractive.astratemplate.gui

import net.kyori.adventure.text.Component
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
import ru.astrainteractive.astralibs.string.BukkitTranslationContext
import ru.astrainteractive.astralibs.string.StringDesc
import ru.astrainteractive.astratemplate.api.dto.UserDTO
import ru.astrainteractive.astratemplate.gui.SampleGuiComponent.Model
import ru.astrainteractive.astratemplate.gui.di.SampleGuiDependencies

class SampleGUI(
    player: Player,
    module: SampleGuiDependencies
) : PaginatedMenu(),
    SampleGuiDependencies by module,
    BukkitTranslationContext by module.bukkitTranslationContext {
    private val viewModel = module.viewModelFactory.create()

    private val clickListener = MenuClickListener()

    private fun createItemStackWithName(material: Material, name: String): ItemStack {
        return createItemStackWithName(material, StringDesc.Raw(name))
    }

    private fun createItemStackWithName(material: Material, name: StringDesc) = ItemStack(material).apply {
        val meta = itemMeta
        meta.displayName(name.toComponent())
        itemMeta = meta
    }

    override val playerHolder: PlayerHolder = DefaultPlayerHolder(player)
    override var menuTitle: Component = translation.menu.menuTitle.toComponent()
    override val menuSize: MenuSize = MenuSize.XL
    override var maxItemsPerPage: Int = 45
    override var page: Int = 0
    override val maxItemsAmount: Int
        get() = when (val state = viewModel.model.value) {
            is Model.Items -> state.items.size
            is Model.Users -> state.users.size
            Model.Loading -> 0
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
            itemStack = when (viewModel.model.value) {
                is Model.Items -> createItemStackWithName(Material.SUNFLOWER, "Items")
                Model.Loading -> createItemStackWithName(Material.SUNFLOWER, "Loading")
                is Model.Users -> createItemStackWithName(Material.SUNFLOWER, "Users")
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
        onModelChanged()
    }

    override fun onInventoryClicked(e: InventoryClickEvent) {
        e.isCancelled = true
        clickListener.onClick(e)
    }

    override fun onCreated() {
        viewModel.onUiCreated()
        viewModel.model.collectOn(dispatchers.BukkitMain, ::onModelChanged)
    }

    private fun onModelChanged(state: Model = viewModel.model.value) {
        inventory.clear()
        clickListener.clearClickListener()
        changeModeButton.apply {
            clickListener.remember(this)
            setInventorySlot()
        }

        setManageButtons(clickListener)

        when (state) {
            is Model.Items -> {
                setItemStacks(state.items)
            }

            is Model.Users -> {
                addUserButton.setInventorySlot()
                clickListener.remember(addUserButton)
                setUsers(state.users)
            }

            Model.Loading -> {}
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
            button.setInventorySlot()
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
            button.setInventorySlot()
        }
    }
}
