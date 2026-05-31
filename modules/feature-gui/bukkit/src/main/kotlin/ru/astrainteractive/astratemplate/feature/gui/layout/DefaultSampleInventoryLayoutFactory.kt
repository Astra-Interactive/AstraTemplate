package ru.astrainteractive.astratemplate.feature.gui.layout

import ru.astrainteractive.astralibs.menu.layout.slotInventoryLayout

@Suppress("MagicNumber")
internal object DefaultSampleInventoryLayoutFactory {

    private fun createCompactLayout() = slotInventoryLayout {
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

    private fun createBorderedLayout() = slotInventoryLayout {
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

    fun create(isCompact: Boolean) = when {
        isCompact -> createCompactLayout()
        else -> createBorderedLayout()
    }
}
