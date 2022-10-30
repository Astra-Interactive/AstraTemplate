package com.astrainteractive.astratemplate.gui

import com.astrainteractive.astratemplate.api.ItemStackSpigotAPI
import com.astrainteractive.astratemplate.modules.RepositoryModule
import com.astrainteractive.astratemplate.modules.TranslationProvider
import com.astrainteractive.astratemplate.utils.PluginTranslation
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.ItemStack
import ru.astrainteractive.astralibs.Logger
import ru.astrainteractive.astralibs.menu.*


class SampleGUI(player: Player) : PaginatedMenu() {
    fun createItemStackWithName(material: Material, name: String) = ItemStack(material).apply {
        val meta = itemMeta
        meta.setDisplayName(name)
        itemMeta = meta
    }

    private val translation: PluginTranslation
        get() = TranslationProvider.value

    private val viewModel = SampleGUIViewModel(RepositoryModule.value, ItemStackSpigotAPI)

    override val playerMenuUtility: IPlayerHolder = DefaultPlayerHolder(player)
    override var menuTitle: String = translation.menuTitle
    override val menuSize: AstraMenuSize = AstraMenuSize.XL
    override var maxItemsPerPage: Int = 45
    override var page: Int = 0
    override val maxItemsAmount: Int
        get() = if (viewModel.mode.value == Mode.ITEMS) viewModel.items.value.size else viewModel.users.value.size


    private val changeModeButton = object : IClickableInventoryButton {
        override fun onClick() {
            viewModel.onModeChange()
        }

        override val index: Int = 50
        override val item: ItemStack = createItemStackWithName(Material.SUNFLOWER, viewModel.mode.value.name)
    }
    private val addUserButton = object : IClickableInventoryButton {
        override fun onClick() {
            viewModel.onAddUserClicked()
        }

        override val index: Int = 48
        override val item: ItemStack = createItemStackWithName(Material.EMERALD, translation.menuAddPlayer)
    }


    override val backPageButton = object : IInventoryButton {
        override val index: Int = 49
        override val item: ItemStack = createItemStackWithName(Material.PAPER, translation.menuClose)
    }
    override val nextPageButton = object : IInventoryButton {
        override val index: Int = 53
        override val item: ItemStack = createItemStackWithName(Material.PAPER, translation.menuNextPage)
    }
    override val prevPageButton = object : IInventoryButton {
        override val index: Int = 45
        override val item: ItemStack = createItemStackWithName(Material.PAPER, translation.menuPrevPage)
    }

    override fun onInventoryClose(it: InventoryCloseEvent) {
        Logger.log("SampleGUI closed", "SampleGUI")
        viewModel.clear()
    }

    override fun onPageChanged() {
        setMenuItems()
    }

    override fun onInventoryClicked(e: InventoryClickEvent) {
        super.onInventoryClicked(e)
        addUserButton.listen(e)
        changeModeButton.listen(e)
        if (IntRange(0, maxItemsPerPage).contains(e.slot))
            viewModel.onItemClicked(e.slot, e.click)
        else if (e.slot == backPageButton.index)
            inventory.close()
    }

    override fun onCreated() {
        viewModel.items.collectOn {
            setMenuItems()
        }
        viewModel.mode.collectOn {
            setMenuItems()
        }
        viewModel.users.collectOn {
            setMenuItems()
        }
    }

    fun setMenuItems() {
        val modeState = viewModel.mode.value

        inventory.clear()
        setManageButtons()
        changeModeButton.setInventoryButton()
        when (modeState) {
            Mode.ITEMS -> {
                setItemStacks()
            }
            Mode.DATABASE -> {
                addUserButton.setInventoryButton()
                setUsers()
            }
        }
    }

    private fun setUsers() {
        val list = viewModel.users.value
        for (i in 0 until maxItemsPerPage) {
            val index = maxItemsPerPage * page + i
            if (index >= list.size)
                continue
            val user = list[index]
            val itemStack = ItemStack(Material.PLAYER_HEAD).apply {
                editMeta {
                    it.setDisplayName(user.id.toString())
                    it.lore = listOf(
                        "${viewModel.randomColor}discordID: ${user.discordId}",
                        "${viewModel.randomColor}minecraftUUID: ${user.minecraftUuid}",
                        "${viewModel.randomColor}Press LeftClick to delete user",
                        "${viewModel.randomColor}Press MiddleClick to delete user",
                        "${viewModel.randomColor}Press RightClick to Add Relation"
                    )
                }
            }
            inventory.setItem(i, itemStack)
        }
    }

    private fun setItemStacks() {
        val list = viewModel.items.value
        for (i in 0 until maxItemsPerPage) {
            val index = maxItemsPerPage * page + i
            if (index >= list.size)
                continue
            val itemStack = list[index]
            inventory.setItem(i, itemStack)
        }
    }


}