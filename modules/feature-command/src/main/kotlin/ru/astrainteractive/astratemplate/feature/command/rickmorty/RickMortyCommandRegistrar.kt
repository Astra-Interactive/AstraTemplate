package ru.astrainteractive.astratemplate.feature.command.rickmorty

import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.astrainteractive.astralibs.command.api.brigadier.command.MultiplatformCommand
import ru.astrainteractive.astralibs.command.api.brigadier.sender.KCommandSender
import ru.astrainteractive.astralibs.command.api.registrar.CommandRegistrarContext
import ru.astrainteractive.astratemplate.api.remote.api.RickMortyApi
import ru.astrainteractive.astratemplate.feature.command.errorhandler.DefaultErrorHandler
import ru.astrainteractive.klibs.mikro.core.dispatchers.KotlinDispatchers
import kotlin.random.Random

internal class RickMortyCommandRegistrar(
    private val scope: CoroutineScope,
    private val dispatchers: KotlinDispatchers,
    private val rmApi: RickMortyApi,
    private val registrarContext: CommandRegistrarContext,
    private val multiplatformCommand: MultiplatformCommand,
    private val errorHandler: DefaultErrorHandler
) {
    private fun send(sender: KCommandSender, number: Int) {
        scope.launch(dispatchers.IO) {
            rmApi.getRandomCharacter(number)
                .onSuccess { sender.sendMessage(net.kyori.adventure.text.Component.text("Got response: $it")) }
                .onFailure { sender.sendMessage(net.kyori.adventure.text.Component.text("Fail: ${it.message}")) }
        }
    }

    @Suppress("MagicNumber")
    private fun createNode(): LiteralArgumentBuilder<*> {
        return with(multiplatformCommand) {
            command("rickandmorty") {
                literal("random") {
                    runs(errorHandler::handle) { ctx ->
                        send(ctx.getSender(), Random.nextInt(0, 100))
                    }
                }
                literal("specific") {
                    argument("number", IntegerArgumentType.integer()) { numberArg ->
                        runs(errorHandler::handle) { ctx ->
                            send(ctx.getSender(), ctx.requireArgument(numberArg))
                        }
                    }
                }
            }
        }
    }

    fun register() {
        registrarContext.registerWhenReady(createNode())
    }
}
