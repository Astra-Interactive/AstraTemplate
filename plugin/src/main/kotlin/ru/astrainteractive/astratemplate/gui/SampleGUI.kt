package ru.astrainteractive.astratemplate.gui

import com.astrainteractive.astratemplate.domain.local.dto.UserDTO
import com.astrainteractive.astratemplate.domain.local.entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.ItemStack
import ru.astrainteractive.astralibs.Logger
import ru.astrainteractive.astralibs.async.BukkitMain
import ru.astrainteractive.astralibs.di.getValue
import ru.astrainteractive.astralibs.menu.*
import ru.astrainteractive.astratemplate.api.ItemStackSpigotAPI
import ru.astrainteractive.astratemplate.modules.RepositoryModule
import ru.astrainteractive.astratemplate.modules.SampleGuiViewModelFactory
import ru.astrainteractive.astratemplate.modules.TranslationProvider


class SampleGUI(player: Player) : PaginatedMenu() {
    private val translation by TranslationProvider

    private val viewModel = SampleGuiViewModelFactory.provide()

    fun createItemStackWithName(material: Material, name: String) = ItemStack(material).apply {
        val meta = itemMeta
        meta.setDisplayName(name)
        itemMeta = meta
    }



    override val playerMenuUtility: IPlayerHolder = DefaultPlayerHolder(player)
    override var menuTitle: String = translation.menuTitle
    override val menuSize: AstraMenuSize = AstraMenuSize.XL
    override var maxItemsPerPage: Int = 45
    override var page: Int = 0
    override val maxItemsAmount: Int
        get() = when (val state = viewModel.inventoryState.value) {
            is InventoryState.Items -> state.items.size
            is InventoryState.Users -> state.users.size
            InventoryState.Loading -> 0
        }


    private val changeModeButton = object : IInventoryButton {
        override val index: Int = 50
        override val item: ItemStack
            get() = when (viewModel.inventoryState.value) {
                is InventoryState.Items -> createItemStackWithName(Material.SUNFLOWER, "Items")
                InventoryState.Loading -> createItemStackWithName(Material.SUNFLOWER, "Loading")
                is InventoryState.Users -> createItemStackWithName(Material.SUNFLOWER, "Users")
            }
        override val onClick: (e: InventoryClickEvent) -> Unit = {
            viewModel.onModeChange()
        }
    }

    private val addUserButton = object : IInventoryButton {
        override val onClick: (e: InventoryClickEvent) -> Unit = {
            viewModel.onAddUserClicked()
        }
        override val index: Int = 48
        override val item: ItemStack = createItemStackWithName(Material.EMERALD, translation.menuAddPlayer)
    }


    override val backPageButton = object : IInventoryButton {
        override val index: Int = 49
        override val item: ItemStack = createItemStackWithName(Material.PAPER, translation.menuClose)
        override val onClick: (e: InventoryClickEvent) -> Unit = {
            inventory.close()
        }
    }
    override val nextPageButton = object : IInventoryButton {
        override val index: Int = 53
        override val item: ItemStack = createItemStackWithName(Material.PAPER, translation.menuNextPage)
        override val onClick: (e: InventoryClickEvent) -> Unit = {
            loadPage(page + 1)
        }
    }
    override val prevPageButton = object : IInventoryButton {
        override val index: Int = 45
        override val item: ItemStack = createItemStackWithName(Material.PAPER, translation.menuPrevPage)
        override val onClick: (e: InventoryClickEvent) -> Unit = {
            loadPage(page - 1)
        }
    }

    override fun onInventoryClose(it: InventoryCloseEvent) {
        Logger.log("SampleGUI closed", "SampleGUI")
        viewModel.clear()
    }

    override fun onPageChanged() {
        onStateChanged()
    }

    override fun onInventoryClicked(e: InventoryClickEvent) {
        super.onInventoryClicked(e)
        e.isCancelled = true
        if (e.slot == addUserButton.index)
            addUserButton.onClick(e)

        if (e.slot == changeModeButton.index)
            changeModeButton.onClick(e)

        if (IntRange(0, maxItemsPerPage).contains(e.slot))
            viewModel.onItemClicked(e.slot, e.click)
        else if (e.slot == backPageButton.index)
            inventory.close()
    }

    override fun onCreated() {
        viewModel.onUiCreated()
        lifecycleScope.launch(Dispatchers.BukkitMain) {
            viewModel.inventoryState.collectLatest {
                onStateChanged(it)
            }
        }
    }

    fun onStateChanged(state: InventoryState = viewModel.inventoryState.value) {
        inventory.clear()
        setManageButtons()
        changeModeButton.setInventoryButton()

        when (state) {
            is InventoryState.Items -> {
                setItemStacks(state.items)
            }

            is InventoryState.Users -> {
                addUserButton.setInventoryButton()
                setUsers(state.users)
            }

            InventoryState.Loading -> {}
        }
    }

    private fun setUsers(list: List<UserDTO>) {
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
                        "${viewModel.randomColor}minecraftUUID: ${user.minecraftUUID}",
                        "${viewModel.randomColor}Press LeftClick to delete user",
                        "${viewModel.randomColor}Press MiddleClick to delete user",
                        "${viewModel.randomColor}Press RightClick to Add Relation"
                    )
                }
            }
            inventory.setItem(i, itemStack)
        }
    }

    private fun setItemStacks(list: List<ItemStack>) {
        for (i in 0 until maxItemsPerPage) {
            val index = maxItemsPerPage * page + i
            if (index >= list.size)
                continue
            val itemStack = list[index]
            inventory.setItem(i, itemStack)
        }
    }


}