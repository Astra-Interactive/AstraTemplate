package ru.astrainteractive.astratemplate.gui.bukkit.sample.util

import ru.astrainteractive.astralibs.menu.layout.InventoryLayout
import ru.astrainteractive.astralibs.menu.layout.slotInventoryLayout
import ru.astrainteractive.astralibs.menu.slot.InventorySlot
import ru.astrainteractive.astratemplate.gui.bukkit.sample.model.SampleSlotKey

@Suppress("MagicNumber")
private fun compactSampleLayout() = slotInventoryLayout {
    repeat(5) {
        row(9, SampleSlotKey.CONTENT_ITEM)
    }
    row(
        SampleSlotKey.PREV_PAGE,
        SampleSlotKey.EMPTY,
        SampleSlotKey.EMPTY,
        SampleSlotKey.ADD_USER,
        SampleSlotKey.BACK,
        SampleSlotKey.CHANGE_MODE,
        SampleSlotKey.EMPTY,
        SampleSlotKey.EMPTY,
        SampleSlotKey.NEXT_PAGE
    )
}

@Suppress("MagicNumber")
private fun borderedSampleLayout() = slotInventoryLayout {
    row(9, SampleSlotKey.BORDER)
    repeat(4) {
        row(
            SampleSlotKey.BORDER,
            SampleSlotKey.CONTENT_ITEM,
            SampleSlotKey.CONTENT_ITEM,
            SampleSlotKey.CONTENT_ITEM,
            SampleSlotKey.CONTENT_ITEM,
            SampleSlotKey.CONTENT_ITEM,
            SampleSlotKey.CONTENT_ITEM,
            SampleSlotKey.CONTENT_ITEM,
            SampleSlotKey.BORDER
        )
    }
    row(
        SampleSlotKey.BORDER,
        SampleSlotKey.PREV_PAGE,
        SampleSlotKey.EMPTY,
        SampleSlotKey.ADD_USER,
        SampleSlotKey.BACK,
        SampleSlotKey.CHANGE_MODE,
        SampleSlotKey.EMPTY,
        SampleSlotKey.NEXT_PAGE,
        SampleSlotKey.BORDER
    )
}

internal fun sampleInventoryLayout(isCompact: Boolean): InventoryLayout<SampleSlotKey, InventorySlot> {
    return when {
        isCompact -> compactSampleLayout()
        else -> borderedSampleLayout()
    }
}
