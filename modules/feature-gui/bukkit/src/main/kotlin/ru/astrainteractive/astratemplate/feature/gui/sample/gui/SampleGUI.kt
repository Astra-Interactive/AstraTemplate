package ru.astrainteractive.astratemplate.feature.gui.sample.gui

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import net.kyori.adventure.text.Component
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import ru.astrainteractive.astralibs.coroutines.withTimings
import ru.astrainteractive.astralibs.kyori.KyoriComponentSerializer
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
import ru.astrainteractive.astratemplate.feature.gui.button.addUser
import ru.astrainteractive.astratemplate.feature.gui.button.back
import ru.astrainteractive.astratemplate.feature.gui.button.border
import ru.astrainteractive.astratemplate.feature.gui.button.changeMode
import ru.astrainteractive.astratemplate.feature.gui.button.di.ButtonContext
import ru.astrainteractive.astratemplate.feature.gui.button.itemSlot
import ru.astrainteractive.astratemplate.feature.gui.button.nextPage
import ru.astrainteractive.astratemplate.feature.gui.button.prevPage
import ru.astrainteractive.astratemplate.feature.gui.button.userSlot
import ru.astrainteractive.astratemplate.feature.gui.layout.DefaultSampleInventoryLayoutFactory
import ru.astrainteractive.astratemplate.feature.gui.layout.SampleSlotKey
import ru.astrainteractive.astratemplate.feature.gui.sample.feature.SampleGuiComponent
import ru.astrainteractive.klibs.mikro.core.coroutines.CoroutineFeature
import ru.astrainteractive.klibs.mikro.core.dispatchers.KotlinDispatchers

internal class SampleGUI(
    private val buttonContext: ButtonContext,
    private val dispatchers: KotlinDispatchers,
    private val sampleComponent: SampleGuiComponent
) : InventoryMenu(),
    KyoriComponentSerializer by buttonContext {

    override val childComponents: List<CoroutineScope>
        get() = listOf(sampleComponent)

    override val title: Component = toComponent(buttonContext.translation.menu.menuTitle)
    override val inventorySize: InventorySize = InventorySize.XL

    override val menuScope: CoroutineScope = CoroutineFeature
        .Default(dispatchers.Main)
        .withTimings()

    private val inventoryMap by lazy {
        DefaultSampleInventoryLayoutFactory.create(isCompact = true)
    }

    private val paginator = DefaultPaginator(
        maxItemsPerPage = inventoryMap.count(SampleSlotKey.CONTENT_ITEM)
    )

    private val borderButtons: List<InventorySlot>
        get() = inventoryMap.mapSlotsNotNull(
            key = SampleSlotKey.BORDER,
            transform = buttonContext::border
        )

    private val nextPageButton: InventorySlot
        get() = buttonContext.nextPage(
            index = inventoryMap.firstIndexOf(SampleSlotKey.NEXT_PAGE),
            click = { onNextPageClicked() }
        )

    private val prevPageButton: InventorySlot
        get() = buttonContext.prevPage(
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
            return buttonContext.changeMode(
                index = inventoryMap.firstIndexOf(SampleSlotKey.CHANGE_MODE),
                label = label,
                click = { sampleComponent.onModeChange() }
            )
        }

    private val addUserButton: InventorySlot
        get() = buttonContext.addUser(
            index = inventoryMap.firstIndexOf(SampleSlotKey.ADD_USER),
            click = { sampleComponent.onAddUserClicked() }
        )

    private val backButton: InventorySlot
        get() = buttonContext.back(
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
                        buttonContext.itemSlot(
                            index = slotIndex,
                            itemStack = item,
                            click = { sampleComponent.onItemClicked(dataIndex, it.click) }
                        )
                    }
                    is SampleGuiComponent.Model.Users -> state.users.getOrNull(dataIndex)?.let { user ->
                        buttonContext.userSlot(
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
