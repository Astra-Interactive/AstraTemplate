package ru.astrainteractive.astratemplate.gui.sample

import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.ItemStack
import ru.astrainteractive.astralibs.menu.holder.DefaultPlayerHolder
import ru.astrainteractive.astralibs.menu.holder.PlayerHolder
import ru.astrainteractive.astralibs.menu.menu.InventorySlot
import ru.astrainteractive.astralibs.menu.menu.MenuSize
import ru.astrainteractive.astralibs.menu.menu.PaginatedMenu
import ru.astrainteractive.astralibs.menu.menu.setDisplayName
import ru.astrainteractive.astralibs.menu.menu.setIndex
import ru.astrainteractive.astralibs.menu.menu.setItemStack
import ru.astrainteractive.astralibs.menu.menu.setLore
import ru.astrainteractive.astralibs.menu.menu.setMaterial
import ru.astrainteractive.astralibs.menu.menu.setOnClickListener
import ru.astrainteractive.astralibs.serialization.KyoriComponentSerializer
import ru.astrainteractive.astratemplate.api.local.model.UserModel
import ru.astrainteractive.astratemplate.gui.di.SampleGuiDependencies
import ru.astrainteractive.astratemplate.gui.sample.SampleGuiComponent.Model

class SampleGUI(
    player: Player,
    dependencies: SampleGuiDependencies
) : PaginatedMenu(),
    SampleGuiDependencies by dependencies,
    KyoriComponentSerializer by dependencies.kyoriComponentSerializer {
    private val sampleComponent = dependencies.viewModelFactory.create()

    override val playerHolder: PlayerHolder = DefaultPlayerHolder(player)
    override var menuTitle: Component = translation.menu.menuTitle.let(::toComponent)
    override val menuSize: MenuSize = MenuSize.XL
    override var maxItemsPerPage: Int = 45
    override var page: Int = 0
    override val maxItemsAmount: Int
        get() = when (val state = sampleComponent.model.value) {
            is Model.Items -> state.items.size
            is Model.Users -> state.users.size
            Model.Loading -> 0
        }

    private val changeModeButton: InventorySlot
        get() = InventorySlot.Builder()
            .setIndex(50)
            .setMaterial(Material.SUNFLOWER)
            .setDisplayName(
                when (sampleComponent.model.value) {
                    is Model.Items -> "Items"
                    Model.Loading -> "Loading"
                    is Model.Users -> "Users"
                }
            )
            .setOnClickListener { sampleComponent.onModeChange() }
            .build()

    private val addUserButton: InventorySlot
        get() = InventorySlot.Builder()
            .setIndex(48)
            .setMaterial(Material.EMERALD)
            .setDisplayName(translation.menu.menuAddPlayer.let(::toComponent))
            .setOnClickListener { sampleComponent.onAddUserClicked() }
            .build()

    override val backPageButton: InventorySlot
        get() = InventorySlot.Builder()
            .setIndex(49)
            .setMaterial(Material.PAPER)
            .setDisplayName(translation.menu.menuClose.let(::toComponent))
            .setOnClickListener { sampleComponent.close() }
            .build()

    override val nextPageButton: InventorySlot
        get() = InventorySlot.Builder()
            .setIndex(53)
            .setMaterial(Material.PAPER)
            .setDisplayName(translation.menu.menuNextPage.let(::toComponent))
            .setOnClickListener { showPage(page + 1) }
            .build()

    override val prevPageButton: InventorySlot
        get() = InventorySlot.Builder()
            .setIndex(45)
            .setMaterial(Material.PAPER)
            .setDisplayName(translation.menu.menuPrevPage.let(::toComponent))
            .setOnClickListener { showPage(page - 1) }
            .build()

    override fun onInventoryClose(it: InventoryCloseEvent) {
        sampleComponent.close()
    }

    override fun onPageChanged() {
        onModelChanged()
    }

    override fun onInventoryClicked(e: InventoryClickEvent) {
        super.onInventoryClicked(e)
        e.isCancelled = true
    }

    override fun onCreated() {
        sampleComponent.onUiCreated()
        sampleComponent.model.onEach { state -> onModelChanged(state) }.launchIn(menuScope)
    }

    private fun onModelChanged(state: Model = sampleComponent.model.value) {
        inventory.clear()
        changeModeButton.setInventorySlot()
        setManageButtons()

        when (state) {
            is Model.Items -> {
                setItemStacks(state.items)
            }

            is Model.Users -> {
                addUserButton.setInventorySlot()
                setUsers(state.users)
            }

            Model.Loading -> Unit
        }
    }

    private fun setUsers(list: List<UserModel>) {
        for (i in 0 until maxItemsPerPage) {
            val index = maxItemsPerPage * page + i
            if (index >= list.size) {
                continue
            }
            val user = list[index]

            InventorySlot.Builder()
                .setIndex(i)
                .setMaterial(Material.PLAYER_HEAD)
                .setDisplayName(user.id.toString())
                .setLore(
                    listOf(
                        "${sampleComponent.randomColor}discordID: ${user.discordId}",
                        "${sampleComponent.randomColor}minecraftUUID: ${user.minecraftUUID}",
                        "${sampleComponent.randomColor}Press LeftClick to delete user",
                        "${sampleComponent.randomColor}Press MiddleClick to delete user",
                        "${sampleComponent.randomColor}Press RightClick to Add Relation"
                    ).map(Component::text)
                )
                .setOnClickListener { sampleComponent.onItemClicked(index, it.click) }
                .build()
                .setInventorySlot()
        }
    }

    private fun setItemStacks(list: List<ItemStack>) {
        for (i in 0 until maxItemsPerPage) {
            val index = maxItemsPerPage * page + i
            if (index >= list.size) {
                continue
            }
            val itemStack = list[index]
            InventorySlot.Builder()
                .setIndex(i)
                .setItemStack(itemStack)
                .setOnClickListener { sampleComponent.onItemClicked(index, it.click) }
                .build()
                .setInventorySlot()
        }
    }
}
