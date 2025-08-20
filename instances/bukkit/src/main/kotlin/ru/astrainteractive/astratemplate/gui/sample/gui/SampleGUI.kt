package ru.astrainteractive.astratemplate.gui.sample.gui

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import ru.astrainteractive.astralibs.kyori.KyoriComponentSerializer
import ru.astrainteractive.astralibs.kyori.unwrap
import ru.astrainteractive.astralibs.menu.holder.DefaultPlayerHolder
import ru.astrainteractive.astralibs.menu.holder.PlayerHolder
import ru.astrainteractive.astralibs.menu.inventory.PaginatedInventoryMenu
import ru.astrainteractive.astralibs.menu.inventory.model.InventorySize
import ru.astrainteractive.astralibs.menu.inventory.model.PageContext
import ru.astrainteractive.astralibs.menu.inventory.util.PageContextExt.getIndex
import ru.astrainteractive.astralibs.menu.inventory.util.PageContextExt.isLastPage
import ru.astrainteractive.astralibs.menu.inventory.util.PaginatedInventoryMenuExt.showNextPage
import ru.astrainteractive.astralibs.menu.inventory.util.PaginatedInventoryMenuExt.showPrevPage
import ru.astrainteractive.astralibs.menu.slot.InventorySlot
import ru.astrainteractive.astralibs.menu.slot.util.InventorySlotBuilderExt.setDisplayName
import ru.astrainteractive.astralibs.menu.slot.util.InventorySlotBuilderExt.setIndex
import ru.astrainteractive.astralibs.menu.slot.util.InventorySlotBuilderExt.setItemStack
import ru.astrainteractive.astralibs.menu.slot.util.InventorySlotBuilderExt.setLore
import ru.astrainteractive.astralibs.menu.slot.util.InventorySlotBuilderExt.setMaterial
import ru.astrainteractive.astralibs.menu.slot.util.InventorySlotBuilderExt.setOnClickListener
import ru.astrainteractive.astratemplate.api.local.model.UserModel
import ru.astrainteractive.astratemplate.core.plugin.PluginTranslation
import ru.astrainteractive.astratemplate.gui.sample.feature.SampleGuiComponent
import ru.astrainteractive.klibs.kstorage.api.CachedKrate
import ru.astrainteractive.klibs.kstorage.util.getValue

internal class SampleGUI(
    player: Player,
    kyoriKrate: CachedKrate<KyoriComponentSerializer>,
    translationKrate: CachedKrate<PluginTranslation>,
    private val sampleComponent: SampleGuiComponent
) : PaginatedInventoryMenu(),
    KyoriComponentSerializer by kyoriKrate.unwrap() {
    override val childComponents: List<CoroutineScope>
        get() = listOf(sampleComponent)
    private val translation by translationKrate

    override val playerHolder: PlayerHolder = DefaultPlayerHolder(player)
    override var title: Component = translation.menu.menuTitle.let(::toComponent)
    override val inventorySize: InventorySize = InventorySize.XL
    override var pageContext: PageContext = PageContext(
        page = 0,
        maxItemsPerPage = inventorySize.size - InventorySize.XXS.size,
        maxItems = 0
    )

    private val changeModeButton: InventorySlot
        get() = InventorySlot.Builder()
            .setIndex(50)
            .setMaterial(Material.SUNFLOWER)
            .setDisplayName(
                when (sampleComponent.model.value) {
                    is SampleGuiComponent.Model.Items -> "Items"
                    SampleGuiComponent.Model.Loading -> "Loading"
                    is SampleGuiComponent.Model.Users -> "Users"
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

    private val backPageButton: InventorySlot
        get() = InventorySlot.Builder()
            .setIndex(49)
            .setMaterial(Material.PAPER)
            .setDisplayName(translation.menu.menuClose.let(::toComponent))
            .setOnClickListener { inventory.close() }
            .build()

    override val nextPageButton: InventorySlot
        get() = InventorySlot.Builder()
            .setIndex(53)
            .setMaterial(Material.PAPER)
            .setDisplayName(translation.menu.menuNextPage.let(::toComponent))
            .setOnClickListener { showNextPage() }
            .build()

    override val prevPageButton: InventorySlot
        get() = InventorySlot.Builder()
            .setIndex(45)
            .setMaterial(Material.PAPER)
            .setDisplayName(translation.menu.menuPrevPage.let(::toComponent))
            .setOnClickListener { showPrevPage() }
            .build()

    override fun onInventoryClicked(e: InventoryClickEvent) {
        super.onInventoryClicked(e)
        e.isCancelled = true
    }

    override fun onInventoryCreated() {
        sampleComponent.onUiCreated()
        sampleComponent.model
            .onEach { state ->
                val maxItems = when (state) {
                    is SampleGuiComponent.Model.Items -> state.items.size
                    is SampleGuiComponent.Model.Users -> state.users.size
                    SampleGuiComponent.Model.Loading -> 0
                }
                pageContext = pageContext.copy(maxItems = maxItems)
            }
            .onEach { render() }
            .launchIn(menuScope)
    }

    override fun render() {
        super.render()
        val state: SampleGuiComponent.Model = sampleComponent.model.value
        inventory.clear()
        changeModeButton.setInventorySlot()
        prevPageButton.setInventorySlotIf { pageContext.page > 0 }
        nextPageButton.setInventorySlotIf { !pageContext.isLastPage }
        backPageButton.setInventorySlot()

        when (state) {
            is SampleGuiComponent.Model.Items -> {
                setItemStacks(state.items)
            }

            is SampleGuiComponent.Model.Users -> {
                addUserButton.setInventorySlot()
                setUsers(state.users)
            }

            SampleGuiComponent.Model.Loading -> Unit
        }
    }

    private fun setUsers(list: List<UserModel>) {
        for (i in 0 until pageContext.maxItemsPerPage) {
            val index = pageContext.getIndex(i)
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
        for (i in 0 until pageContext.maxItemsPerPage) {
            val index = pageContext.getIndex(i)
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
