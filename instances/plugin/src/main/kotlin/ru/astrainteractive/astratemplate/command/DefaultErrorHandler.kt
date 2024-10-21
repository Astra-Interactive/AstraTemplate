package ru.astrainteractive.astratemplate.command

import ru.astrainteractive.astralibs.command.api.context.BukkitCommandContext
import ru.astrainteractive.astralibs.command.api.error.ErrorHandler
import ru.astrainteractive.astralibs.command.api.exception.BukkitCommandException
import ru.astrainteractive.astralibs.command.api.exception.DefaultCommandException
import ru.astrainteractive.astralibs.kyori.KyoriComponentSerializer
import ru.astrainteractive.astralibs.logging.JUtiltLogger
import ru.astrainteractive.astralibs.logging.Logger
import ru.astrainteractive.astratemplate.command.additem.AddItemCommand
import ru.astrainteractive.astratemplate.command.gui.GuiCommand
import ru.astrainteractive.astratemplate.core.PluginTranslation
import ru.astrainteractive.astratemplate.core.util.getValue
import ru.astrainteractive.klibs.kstorage.api.Krate

internal class DefaultErrorHandler(
    translationKrate: Krate<PluginTranslation>,
    kyoriKrate: Krate<KyoriComponentSerializer>
) : ErrorHandler<BukkitCommandContext>,
    Logger by JUtiltLogger("AstraTemplate-DefaultErrorHandler") {
    private val translation by translationKrate
    private val kyori by kyoriKrate

    override fun handle(commandContext: BukkitCommandContext, throwable: Throwable) {
        with(kyori) {
            when (throwable) {
                is AddItemCommand.Error -> {
                    when (throwable) {
                        AddItemCommand.Error.ItemNotfound -> {
                            commandContext.sender.sendMessage(translation.fault.itemNotFound.component)
                        }

                        AddItemCommand.Error.SenderNotPlayer -> {
                            commandContext.sender.sendMessage(translation.fault.notPlayer.component)
                        }
                    }
                }

                is GuiCommand.Error -> {
                    when (throwable) {
                        GuiCommand.Error.NotPlayer -> {
                            commandContext.sender.sendMessage(translation.fault.notPlayer.component)
                        }
                    }
                }

                is BukkitCommandException.NoPlayerException -> {
                    commandContext.sender.sendMessage(translation.fault.playerNotExists.component)
                }

                is DefaultCommandException.NoPermissionException -> {
                    commandContext.sender.sendMessage(translation.fault.noPermission.component)
                }

                is BukkitCommandException.NoPotionEffectTypeException,
                is DefaultCommandException.BadArgumentException,
                is DefaultCommandException.ArgumentTypeException -> {
                    error { "#handle intentionally unhandled error: $throwable" }
                }

                else -> {
                    error { "#handle unhandled error: $throwable" }
                }
            }
        }
    }
}
