package com.astrainteractive.astratemplate.gui

import com.astrainteractive.astralibs.Logger
import com.astrainteractive.astralibs.async.AsyncHelper
import com.astrainteractive.astralibs.events.DSLEvent
import com.astrainteractive.astralibs.events.EventListener
import com.astrainteractive.astralibs.events.EventManager
import com.astrainteractive.astralibs.menu.AstraMenuSize
import com.astrainteractive.astralibs.menu.AstraPlayerMenuUtility
import com.astrainteractive.astralibs.menu.Menu
import com.astrainteractive.astralibs.menu.PaginatedMenu
import com.astrainteractive.astratemplate.utils.Translation
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.ItemStack

class SampleGUI(override val playerMenuUtility: AstraPlayerMenuUtility) : PaginatedMenu() {
    constructor(player: Player) : this(AstraPlayerMenuUtility(player))

    private val viewModel = SampleGUIViewModel()
    override val backButtonIndex: Int = 49
    override val nextButtonIndex: Int = 53
    override val prevButtonIndex: Int = 45
    private val changeModeIndex: Int = 50
    private val changeModeItemStack: ItemStack
        get() = ItemStack(Material.SUNFLOWER).apply {
            editMeta {
                it.setDisplayName(viewModel.mode.value.name)
            }
        }
    private val addUserIndex = 48
    private val addUserItemStack: ItemStack
        get() = ItemStack(Material.EMERALD).apply {
            editMeta {
                it.setDisplayName(Translation.menuAddPlayer)
            }
        }

    override var menuName: String = Translation.menuTitle
    override val menuSize: AstraMenuSize = AstraMenuSize.XL
    override val backPageButton: ItemStack = ItemStack(Material.PAPER).apply {
        val meta = itemMeta
        meta.setDisplayName(Translation.menuClose)
        itemMeta = meta
    }
    override val nextPageButton: ItemStack = ItemStack(Material.PAPER).apply {
        val meta = itemMeta
        meta.setDisplayName(Translation.menuNextPage)
        itemMeta = meta
    }
    override val prevPageButton: ItemStack = ItemStack(Material.PAPER).apply {
        val meta = itemMeta
        meta.setDisplayName(Translation.menuPrevPage)
        itemMeta = meta
    }
    override var maxItemsPerPage: Int = 45
    override var page: Int = 0
    override val maxItemsAmount: Int
        get() = if (viewModel.mode.value == Mode.ITEMS) viewModel.items.value.size else viewModel.users.value.size


    override fun handleMenu(e: InventoryClickEvent) {
        super.handleMenu(e)
        if (IntRange(0, maxItemsPerPage).contains(e.slot))
            viewModel.onItemClicked(e.slot,e.click)
        else if (e.slot == changeModeIndex)
            viewModel.onModeChange()
        else if (e.slot == addUserIndex)
            viewModel.onAddUserClicked()
        else if (e.slot == backButtonIndex)
            inventory.close()
    }


    private val itemCollector = AsyncHelper.launch {
        viewModel.items.collectLatest {
            AsyncHelper.callSyncMethod {
                setMenuItems()
            }
        }
    }
    private val modCollector = AsyncHelper.launch {
        viewModel.mode.collectLatest {
            AsyncHelper.callSyncMethod {
                setMenuItems()
            }
        }
    }
    private val usersCollector = AsyncHelper.launch {
        viewModel.users.collectLatest {
            AsyncHelper.callSyncMethod {
                setMenuItems()
            }
        }
    }

    override fun onInventoryClose(it: InventoryCloseEvent, manager: EventManager) {
        if(it.inventory!=inventory) return
        Logger.log("SampleGUI closed", "SampleGUI")
        viewModel.onDisable()
        manager.onDisable()
        itemCollector.cancel()
        modCollector.cancel()
        usersCollector.cancel()
    }

    override fun setMenuItems() {
        inventory.clear()
        addManageButtons()
        inventory.setItem(changeModeIndex, changeModeItemStack)
        if (viewModel.mode.value == Mode.DATABASE)
            inventory.setItem(addUserIndex, addUserItemStack)
        if (viewModel.mode.value == Mode.ITEMS)
            setItemStacks()
        else setUsers()
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