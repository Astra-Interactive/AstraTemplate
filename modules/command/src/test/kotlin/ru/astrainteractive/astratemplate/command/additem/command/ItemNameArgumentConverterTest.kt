@file:Suppress("FunctionNaming")

package ru.astrainteractive.astratemplate.command.additem.command

import ru.astrainteractive.astratemplate.command.additem.model.AddItemError
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ItemNameArgumentConverterTest {
    @Test
    fun GIVEN_item_name_WHEN_converted_THEN_returns_it() {
        assertEquals("diamond", ItemNameArgumentConverter.transform("diamond"))
    }

    @Test
    fun GIVEN_blank_item_name_WHEN_converted_THEN_fails_as_item_not_found() {
        assertFailsWith<AddItemError.ItemNotFound> {
            ItemNameArgumentConverter.transform("  ")
        }
    }
}
