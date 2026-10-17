@file:Suppress("FunctionNaming")

package ru.astrainteractive.astratemplate.command.additem.command

import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import ru.astrainteractive.astratemplate.command.additem.fake.FakeOnlineKPlayer
import ru.astrainteractive.astratemplate.command.additem.model.AddItemCommand
import ru.astrainteractive.astratemplate.core.plugin.model.PluginTranslation
import ru.astrainteractive.klibs.kstorage.api.asCachedMutableKrate
import ru.astrainteractive.klibs.kstorage.api.impl.DefaultMutableKrate
import java.util.Locale
import kotlin.test.Test
import kotlin.test.assertEquals

class AddItemExecutorTest {
    private val translation = PluginTranslation()
    private val executor = AddItemExecutor(
        translationKrate = DefaultMutableKrate(factory = { translation }, loader = { null }).asCachedMutableKrate()
    )

    @Test
    fun GIVEN_add_item_command_WHEN_executed_THEN_player_reads_how_many_items_were_added() {
        val player = FakeOnlineKPlayer()

        executor.execute(AddItemCommand(player = player, amount = 3, itemName = "diamond"))

        val messages = player.messages.map { message ->
            PlainTextComponentSerializer.plainText().serialize(message.toComponent(Locale.US))
        }
        assertEquals(listOf("[TEMPLATE] Added 3x diamond!"), messages)
    }
}
