package ru.astrainteractive.astratemplate.feature.command.additem

import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import ru.astrainteractive.astralibs.command.api.argumenttype.ArgumentConverter
import ru.astrainteractive.astralibs.command.api.brigadier.command.MultiplatformCommand
import ru.astrainteractive.astralibs.command.api.registrar.CommandRegistrarContext
import ru.astrainteractive.astralibs.kyori.KyoriComponentSerializer
import ru.astrainteractive.astralibs.kyori.unwrap
import ru.astrainteractive.astratemplate.feature.command.errorhandler.DefaultErrorHandler
import ru.astrainteractive.klibs.kstorage.api.CachedKrate

internal class AddItemCommandRegistry(
    kyoriKrate: CachedKrate<KyoriComponentSerializer>,
    private val registrarContext: CommandRegistrarContext,
    private val multiplatformCommand: MultiplatformCommand,
    private val errorHandler: DefaultErrorHandler,
    private val executor: AddItemExecutor
) : KyoriComponentSerializer by kyoriKrate.unwrap() {

    private object ItemNameArgumentConverter : ArgumentConverter<String> {
        override fun transform(argument: String): String {
            return argument.takeIf { it.isNotBlank() } ?: throw AddItemCommand.Error.ItemNotfound()
        }
    }

    @Suppress("MagicNumber")
    private fun createNode(): LiteralArgumentBuilder<*> {
        return with(multiplatformCommand) {
            command("add") {
                argument("material", StringArgumentType.word()) { materialArg ->
                    argument("amount", IntegerArgumentType.integer(1, 64)) { amountArg ->
                        runs(errorHandler::handle) { ctx ->
                            val player = ctx.requirePlayer()
                            val itemName = ctx.requireArgument(materialArg, ItemNameArgumentConverter)
                            val amount = ctx.requireArgument(amountArg)
                            executor.execute(
                                AddItemCommand.Result(
                                    player = player,
                                    amount = amount,
                                    itemName = itemName
                                )
                            )
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
