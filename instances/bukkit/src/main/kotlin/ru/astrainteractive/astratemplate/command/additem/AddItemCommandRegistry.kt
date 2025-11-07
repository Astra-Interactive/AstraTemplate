package ru.astrainteractive.astratemplate.command.additem

import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.tree.LiteralCommandNode
import io.papermc.paper.command.brigadier.CommandSourceStack
import org.bukkit.Bukkit
import org.bukkit.Material
import ru.astrainteractive.astralibs.command.api.argumenttype.ArgumentConverter
import ru.astrainteractive.astralibs.command.api.exception.NoPlayerException
import ru.astrainteractive.astralibs.command.api.util.argument
import ru.astrainteractive.astralibs.command.api.util.command
import ru.astrainteractive.astralibs.command.api.util.requireArgument
import ru.astrainteractive.astralibs.command.api.util.runs
import ru.astrainteractive.astralibs.kyori.KyoriComponentSerializer
import ru.astrainteractive.astralibs.kyori.unwrap
import ru.astrainteractive.astratemplate.command.errorhandler.DefaultErrorHandler
import ru.astrainteractive.klibs.kstorage.api.CachedKrate

internal class AddItemCommandRegistry(
    kyoriKrate: CachedKrate<KyoriComponentSerializer>,
    private val errorHandler: DefaultErrorHandler,
    private val executor: AddItemExecutor
) : KyoriComponentSerializer by kyoriKrate.unwrap() {
    private object MaterialArgumentConverter : ArgumentConverter<Material> {
        override fun transform(argument: String): Material {
            return Material.getMaterial(argument) ?: throw AddItemCommand.Error.ItemNotfound()
        }
    }

    fun createNode(): LiteralCommandNode<CommandSourceStack> {
        return command("add") {
            argument("player", StringArgumentType.word()) { playerArg ->
                argument("material", StringArgumentType.word()) { materialArg ->
                    argument("amount", IntegerArgumentType.integer(1, 64)) { amountArg ->
                        runs(errorHandler::handle) { ctx ->
                            val playerName = ctx.requireArgument(playerArg)
                            val player = Bukkit.getPlayerExact(playerName) ?: throw NoPlayerException(playerName)

                            val material = ctx.requireArgument(materialArg, MaterialArgumentConverter)

                            val amount = ctx.requireArgument(amountArg)
                            executor.execute(
                                AddItemCommand.Result(
                                    player = player,
                                    amount = amount,
                                    item = material
                                )
                            )
                        }
                    }
                }
            }
        }.build()
    }
}
