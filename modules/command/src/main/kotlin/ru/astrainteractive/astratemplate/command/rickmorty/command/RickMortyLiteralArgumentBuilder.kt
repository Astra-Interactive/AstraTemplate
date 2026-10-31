package ru.astrainteractive.astratemplate.command.rickmorty.command

import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.astrainteractive.astralibs.command.api.brigadier.command.MultiplatformCommand
import ru.astrainteractive.astralibs.command.api.brigadier.sender.KCommandSender
import ru.astrainteractive.astratemplate.command.api.CommandErrorHandler
import ru.astrainteractive.astratemplate.core.plugin.model.PluginTranslation
import ru.astrainteractive.klibs.kstorage.api.CachedKrate
import ru.astrainteractive.klibs.kstorage.api.getValue
import kotlin.random.Random

internal class RickMortyLiteralArgumentBuilder(
    private val scope: CoroutineScope,
    private val fetchRandomCharacter: suspend (Int) -> Result<Any>,
    private val multiplatformCommand: MultiplatformCommand,
    private val errorHandler: CommandErrorHandler,
    translationKrate: CachedKrate<PluginTranslation>
) {
    private val translation by translationKrate

    private fun send(sender: KCommandSender, number: Int) {
        scope.launch {
            fetchRandomCharacter.invoke(number)
                .onSuccess { character ->
                    sender.sendMessage(translation.rickMorty.success(character.toString()))
                }
                .onFailure { error ->
                    sender.sendMessage(translation.rickMorty.failure(error.message.orEmpty()))
                }
        }
    }

    @Suppress("MagicNumber")
    fun create(): LiteralArgumentBuilder<*> {
        return with(multiplatformCommand) {
            command("rickandmorty") {
                literal("random") {
                    runs(errorHandler::onFailure) { ctx ->
                        send(ctx.getSender(), Random.nextInt(0, 100))
                    }
                }
                literal("specific") {
                    argument(
                        "number",
                        IntegerArgumentType.integer()
                    ) { numberArg ->
                        runs(errorHandler::onFailure) { ctx ->
                            send(ctx.getSender(), ctx.requireArgument(numberArg))
                        }
                    }
                }
            }
        }
    }
}
