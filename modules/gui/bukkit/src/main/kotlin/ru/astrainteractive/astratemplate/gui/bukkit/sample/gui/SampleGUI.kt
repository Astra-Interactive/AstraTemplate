package ru.astrainteractive.astratemplate.gui.bukkit.sample.gui

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import net.kyori.adventure.text.Component
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import ru.astrainteractive.astralibs.coroutines.withTimings
import ru.astrainteractive.astralibs.menu.core.setInventorySlot
import ru.astrainteractive.astralibs.menu.inventory.api.InventoryMenu
import ru.astrainteractive.astralibs.menu.inventory.model.InventorySize
import ru.astrainteractive.astralibs.menu.layout.mapSlotsNotNullIndexed
import ru.astrainteractive.astralibs.menu.paginator.api.DefaultPaginator
import ru.astrainteractive.astralibs.menu.paginator.api.context
import ru.astrainteractive.astralibs.menu.paginator.api.openNextPage
import ru.astrainteractive.astralibs.menu.paginator.api.openPrevPage
import ru.astrainteractive.astralibs.menu.paginator.api.setMaxItems
import ru.astrainteractive.astralibs.menu.paginator.model.indexOfSlot
import ru.astrainteractive.astralibs.menu.paginator.model.isFirstPage
import ru.astrainteractive.astralibs.menu.paginator.model.isLastPage
import ru.astrainteractive.astralibs.menu.slot.InventorySlot
import ru.astrainteractive.astratemplate.gui.bukkit.context.SlotContext
import ru.astrainteractive.astratemplate.gui.bukkit.sample.component.SampleGuiComponent
import ru.astrainteractive.astratemplate.gui.bukkit.sample.model.SampleSlotKey
import ru.astrainteractive.astratemplate.gui.bukkit.sample.util.sampleInventoryLayout
import ru.astrainteractive.astratemplate.gui.bukkit.slot.addUserSlot
import ru.astrainteractive.astratemplate.gui.bukkit.slot.backSlot
import ru.astrainteractive.astratemplate.gui.bukkit.slot.borderSlot
import ru.astrainteractive.astratemplate.gui.bukkit.slot.changeModeSlot
import ru.astrainteractive.astratemplate.gui.bukkit.slot.itemSlot
import ru.astrainteractive.astratemplate.gui.bukkit.slot.nextPageSlot
import ru.astrainteractive.astratemplate.gui.bukkit.slot.prevPageSlot
import ru.astrainteractive.astratemplate.gui.bukkit.slot.userSlot
import ru.astrainteractive.klibs.mikro.core.coroutines.CoroutineFeature
import ru.astrainteractive.klibs.mikro.core.dispatchers.KotlinDispatchers

internal class SampleGUI(
    private val slotContext: SlotContext,
    private val dispatchers: KotlinDispatchers,
    private val sampleComponent: SampleGuiComponent
) : InventoryMenu() {

    override val childComponents: List<CoroutineScope>
        get() = listOf(sampleComponent)

    override val title: Component = slotContext.translation.menu.title.toComponent(slotContext.locale)
    override val inventorySize: InventorySize = InventorySize.XL

    override val menuScope: CoroutineScope = CoroutineFeature
        .Default(dispatchers.Main)
        .withTimings()

    private val inventoryMap by lazy {
        sampleInventoryLayout(isCompact = true)
    }

    private val paginator = DefaultPaginator(
        maxItemsPerPage = inventoryMap.count(SampleSlotKey.CONTENT_ITEM)
    )

    private val borderButtons: List<InventorySlot>
        get() = inventoryMap.mapSlotsNotNull(
            key = SampleSlotKey.BORDER,
            transform = slotContext::borderSlot
        )

    private val nextPageButton: InventorySlot
        get() = slotContext.nextPageSlot(
            index = inventoryMap.firstIndexOf(SampleSlotKey.NEXT_PAGE),
            click = { onNextPageClicked() }
        )

    private val prevPageButton: InventorySlot
        get() = slotContext.prevPageSlot(
            index = inventoryMap.firstIndexOf(SampleSlotKey.PREV_PAGE),
            click = { onPrevPageClicked() }
        )

    private val changeModeButton: InventorySlot
        get() {
            val label = when (sampleComponent.model.value) {
                is SampleGuiComponent.Model.Items -> "Items"
                is SampleGuiComponent.Model.Users -> "Users"
                SampleGuiComponent.Model.Loading -> "Loading"
            }
            return slotContext.changeModeSlot(
                index = inventoryMap.firstIndexOf(SampleSlotKey.CHANGE_MODE),
                label = label,
                click = { sampleComponent.onModeChange() }
            )
        }

    private val addUserButton: InventorySlot
        get() = slotContext.addUserSlot(
            index = inventoryMap.firstIndexOf(SampleSlotKey.ADD_USER),
            click = { sampleComponent.onAddUserClicked() }
        )

    private val backButton: InventorySlot
        get() = slotContext.backSlot(
            index = inventoryMap.firstIndexOf(SampleSlotKey.BACK),
            click = { e -> e.whoClicked.closeInventory() }
        )

    private val contentSlots: List<InventorySlot>
        get() {
            val state = sampleComponent.model.value
            return inventoryMap.mapSlotsNotNullIndexed(SampleSlotKey.CONTENT_ITEM) { iterIndex, slotIndex ->
                val dataIndex = paginator.context.indexOfSlot(iterIndex)
                when (state) {
                    is SampleGuiComponent.Model.Items -> state.items.getOrNull(dataIndex)?.let { item ->
                        slotContext.itemSlot(
                            index = slotIndex,
                            itemStack = item,
                            click = { sampleComponent.onItemClicked(dataIndex, it.click) }
                        )
                    }
                    is SampleGuiComponent.Model.Users -> state.users.getOrNull(dataIndex)?.let { user ->
                        slotContext.userSlot(
                            index = slotIndex,
                            user = user,
                            randomColor = sampleComponent.randomColor,
                            click = { sampleComponent.onItemClicked(dataIndex, it.click) }
                        )
                    }
                    SampleGuiComponent.Model.Loading -> null
                }
            }
        }

    private fun onNextPageClicked() {
        paginator.openNextPage()
        render()
    }

    private fun onPrevPageClicked() {
        paginator.openPrevPage()
        render()
    }

    override fun onInventoryClickEvent(e: InventoryClickEvent) {
        super.onInventoryClickEvent(e)
        e.isCancelled = true
    }

    override fun onInventoryOpenEvent(e: InventoryOpenEvent) {
        sampleComponent.onUiCreated()
        sampleComponent.model
            .onEach { state ->
                paginator.setMaxItems(
                    when (state) {
                        is SampleGuiComponent.Model.Items -> state.items.size
                        is SampleGuiComponent.Model.Users -> state.users.size
                        SampleGuiComponent.Model.Loading -> 0
                    }
                )
            }
            .onEach { render() }
            .launchIn(menuScope)
    }

    override fun render() {
        super.render()
        setInventorySlot(borderButtons)
        setInventorySlot(changeModeButton)
        setInventorySlot(backButton)
        if (!paginator.context.isFirstPage) setInventorySlot(prevPageButton)
        if (!paginator.context.isLastPage) setInventorySlot(nextPageButton)
        val state = sampleComponent.model.value
        if (state is SampleGuiComponent.Model.Users) {
            setInventorySlot(addUserButton)
        }
        setInventorySlot(contentSlots)
    }
}
