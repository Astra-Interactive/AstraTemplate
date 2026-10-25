@file:Suppress("FunctionNaming")

package ru.astrainteractive.astratemplate.command.error

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import ru.astrainteractive.astralibs.command.api.argumenttype.IntArgumentConverter
import ru.astrainteractive.astralibs.command.api.brigadier.command.MultiplatformCommand
import ru.astrainteractive.astralibs.command.api.exception.BadArgumentException
import ru.astrainteractive.astralibs.localization.component.LocalizableComponent
import ru.astrainteractive.astratemplate.command.additem.model.AddItemError
import ru.astrainteractive.astratemplate.command.fake.FakeMultiplatformCommands
import ru.astrainteractive.astratemplate.command.fake.RecordingConsoleKCommandSender
import ru.astrainteractive.astratemplate.core.plugin.model.PluginPermission
import ru.astrainteractive.astratemplate.core.plugin.model.PluginTranslation
import ru.astrainteractive.klibs.kstorage.api.asCachedMutableKrate
import ru.astrainteractive.klibs.kstorage.api.impl.DefaultMutableKrate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CommandErrorHandlerTest {
    private val sender = RecordingConsoleKCommandSender()
    private val multiplatformCommand = MultiplatformCommand(FakeMultiplatformCommands(sender))
    private val handler = CommandErrorHandler(
        multiplatformCommand = multiplatformCommand,
        translationKrate = DefaultMutableKrate(factory = ::PluginTranslation, loader = { null }).asCachedMutableKrate()
    )
    private val translation = PluginTranslation()

    private fun execute(command: LiteralArgumentBuilder<Any>, input: String) {
        val dispatcher = CommandDispatcher<Any>()
        dispatcher.register(command)
        dispatcher.execute(input, Any())
    }

    private fun executeFailing(failure: Throwable) {
        val command = with(multiplatformCommand) {
            command("template") {
                runs(handler::onFailure) { _ -> throw failure }
            }
        }
        execute(command, "template")
    }

    private fun assertSenderRead(message: LocalizableComponent) {
        assertEquals(listOf(message), sender.messages)
    }

    @Test
    fun GIVEN_sender_without_permission_WHEN_command_requires_it_THEN_sender_reads_no_permission() {
        val command = with(multiplatformCommand) {
            command("atempreload") {
                runs(handler::onFailure) { ctx -> ctx.requirePermission(PluginPermission.Reload) }
            }
        }

        execute(command, "atempreload")

        assertSenderRead(translation.commandError.noPermission)
    }

    @Test
    fun GIVEN_unknown_item_WHEN_command_fails_THEN_sender_reads_item_not_found() {
        executeFailing(AddItemError.ItemNotFound())

        assertSenderRead(translation.addItem.itemNotFound)
    }

    @Test
    fun GIVEN_sender_that_is_not_a_player_WHEN_command_fails_THEN_sender_reads_not_player() {
        executeFailing(AddItemError.SenderNotPlayer())

        assertSenderRead(translation.commandError.notPlayer)
    }

    @Test
    fun GIVEN_argument_of_incompatible_type_WHEN_command_fails_THEN_sender_reads_nothing() {
        executeFailing(BadArgumentException("ten", IntArgumentConverter))

        assertTrue(sender.messages.isEmpty())
    }

    @Test
    fun GIVEN_unexpected_failure_WHEN_command_fails_THEN_sender_reads_nothing() {
        executeFailing(IllegalStateException("Unexpected"))

        assertTrue(sender.messages.isEmpty())
    }
}
