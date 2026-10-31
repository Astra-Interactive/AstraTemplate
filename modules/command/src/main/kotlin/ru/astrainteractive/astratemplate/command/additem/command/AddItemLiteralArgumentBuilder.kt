package ru.astrainteractive.astratemplate.command.additem.command

import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import ru.astrainteractive.astralibs.command.api.brigadier.command.MultiplatformCommand
import ru.astrainteractive.astratemplate.command.additem.model.AddItemCommand
import ru.astrainteractive.astratemplate.command.api.CommandErrorHandler

internal class AddItemLiteralArgumentBuilder(
    private val multiplatformCommand: MultiplatformCommand,
    private val errorHandler: CommandErrorHandler,
    private val executor: AddItemExecutor
) {
    @Suppress("MagicNumber")
    fun create(): LiteralArgumentBuilder<*> {
        return with(multiplatformCommand) {
            command("add") {
                argument(
                    "material",
                    StringArgumentType.word()
                ) { materialArg ->
                    argument(
                        "amount",
                        IntegerArgumentType.integer(1, 64)
                    ) { amountArg ->
                        runs(errorHandler::onFailure) { ctx ->
                            val player = ctx.requirePlayer()
                            val itemName = ctx.requireArgument(materialArg, ItemNameArgumentConverter)
                            val amount = ctx.requireArgument(amountArg)
                            executor.execute(
                                AddItemCommand(
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
}
