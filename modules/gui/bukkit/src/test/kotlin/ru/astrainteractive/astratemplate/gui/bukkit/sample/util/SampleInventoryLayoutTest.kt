@file:Suppress("FunctionNaming")

package ru.astrainteractive.astratemplate.gui.bukkit.sample.util

import ru.astrainteractive.astratemplate.gui.bukkit.sample.model.SampleSlotKey
import kotlin.test.Test
import kotlin.test.assertEquals

class SampleInventoryLayoutTest {
    @Test
    fun GIVEN_compact_layout_WHEN_built_THEN_five_rows_hold_content_and_last_row_holds_navigation() {
        val layout = sampleInventoryLayout(isCompact = true)

        assertEquals(45, layout.count(SampleSlotKey.CONTENT_ITEM))
        assertEquals(0, layout.count(SampleSlotKey.BORDER))
        assertEquals(45, layout.firstIndexOf(SampleSlotKey.PREV_PAGE))
        assertEquals(53, layout.firstIndexOf(SampleSlotKey.NEXT_PAGE))
    }

    @Test
    fun GIVEN_bordered_layout_WHEN_built_THEN_border_surrounds_content() {
        val layout = sampleInventoryLayout(isCompact = false)

        assertEquals(28, layout.count(SampleSlotKey.CONTENT_ITEM))
        assertEquals(19, layout.count(SampleSlotKey.BORDER))
        assertEquals(46, layout.firstIndexOf(SampleSlotKey.PREV_PAGE))
        assertEquals(52, layout.firstIndexOf(SampleSlotKey.NEXT_PAGE))
    }
}
