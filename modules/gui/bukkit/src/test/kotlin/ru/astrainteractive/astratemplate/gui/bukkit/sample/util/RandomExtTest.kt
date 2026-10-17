@file:Suppress("FunctionNaming")

package ru.astrainteractive.astratemplate.gui.bukkit.sample.util

import org.bukkit.ChatColor
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals

class RandomExtTest {
    @Test
    fun GIVEN_same_seed_WHEN_colors_are_drawn_THEN_same_colors_from_all_chat_colors_return() {
        val first = List(10) { Random(seed = 7).nextChatColor() }
        val expected = ChatColor.entries[Random(seed = 7).nextInt(ChatColor.entries.size)]

        assertEquals(List(10) { expected }, first)
    }
}
