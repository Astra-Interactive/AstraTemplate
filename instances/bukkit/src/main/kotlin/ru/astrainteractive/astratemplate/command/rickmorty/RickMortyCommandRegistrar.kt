package ru.astrainteractive.astratemplate.command.rickmorty

import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.tree.LiteralCommandNode
import io.papermc.paper.command.brigadier.CommandSourceStack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.bukkit.command.CommandSender
import ru.astrainteractive.astralibs.command.api.util.argument
import ru.astrainteractive.astralibs.command.api.util.command
import ru.astrainteractive.astralibs.command.api.util.literal
import ru.astrainteractive.astralibs.command.api.util.requireArgument
import ru.astrainteractive.astralibs.command.api.util.runs
import ru.astrainteractive.astratemplate.api.remote.RickMortyApi
import ru.astrainteractive.astratemplate.command.errorhandler.DefaultErrorHandler
import ru.astrainteractive.klibs.mikro.core.dispatchers.KotlinDispatchers
import kotlin.random.Random

internal class RickMortyCommandRegistrar(
    private val scope: CoroutineScope,
    private val dispatchers: KotlinDispatchers,
    private val rmApi: RickMortyApi,
    private val errorHandler: DefaultErrorHandler
) {

    private fun send(sender: CommandSender, number: Int) {
        scope.launch(dispatchers.IO) {
            val result = rmApi.getRandomCharacter(number)
            result.onSuccess {
                sender.sendMessage("Got response: $it")
            }
            result.onFailure {
                it.printStackTrace()
                sender.sendMessage("Fail: ${it.message}")
            }
        }
    }

    fun createNode(): LiteralCommandNode<CommandSourceStack> {
        return command("rickandmorty") {
            literal("random") {
                runs(errorHandler::handle) { ctx ->
                    send(
                        sender = ctx.source.sender,
                        number = Random.nextInt(0, 100)
                    )
                }
            }
            literal("specific") {
                argument("number", IntegerArgumentType.integer()) { numberArg ->
                    runs(errorHandler::handle) { ctx ->
                        send(
                            sender = ctx.source.sender,
                            number = ctx.requireArgument(numberArg)
                        )
                    }
                }
            }
        }.build()
    }
}
