package ru.astrainteractive.astratemplate.command

import ru.astrainteractive.astralibs.command.api.context.BukkitCommandContext
import ru.astrainteractive.astralibs.command.api.error.ErrorHandler
import ru.astrainteractive.astralibs.command.api.exception.ArgumentTypeException
import ru.astrainteractive.astralibs.command.api.exception.BadArgumentException
import ru.astrainteractive.astralibs.command.api.exception.NoPermissionException
import ru.astrainteractive.astralibs.command.api.exception.NoPlayerException
import ru.astrainteractive.astralibs.command.api.exception.NoPotionEffectTypeException
import ru.astrainteractive.astralibs.kyori.KyoriComponentSerializer
import ru.astrainteractive.astralibs.logging.JUtiltLogger
import ru.astrainteractive.astralibs.logging.Logger
import ru.astrainteractive.astratemplate.command.additem.AddItemCommand
import ru.astrainteractive.astratemplate.command.gui.GuiCommand
import ru.astrainteractive.astratemplate.core.plugin.PluginTranslation
import ru.astrainteractive.klibs.kstorage.api.Krate

internal class DefaultErrorHandler(
    translationKrate: Krate<PluginTranslation>,
    kyoriKrate: Krate<KyoriComponentSerializer>
) : ErrorHandler<BukkitCommandContext>,
    Logger by JUtiltLogger("AstraTemplate-DefaultErrorHandler") {
    private val translation by translationKrate
    private val kyori by kyoriKrate

    override fun handle(ctx: BukkitCommandContext, throwable: Throwable) {
        with(kyori) {
            when (throwable) {
                is AddItemCommand.Error -> {
                    when (throwable) {
                        AddItemCommand.Error.ItemNotfound -> {
                            ctx.sender.sendMessage(translation.fault.itemNotFound.component)
                        }

                        AddItemCommand.Error.SenderNotPlayer -> {
                            ctx.sender.sendMessage(translation.fault.notPlayer.component)
                        }
                    }
                }

                is GuiCommand.Error -> {
                    when (throwable) {
                        GuiCommand.Error.NotPlayer -> {
                            ctx.sender.sendMessage(translation.fault.notPlayer.component)
                        }
                    }
                }

                is NoPlayerException -> {
                    ctx.sender.sendMessage(translation.fault.playerNotExists.component)
                }

                is NoPermissionException -> {
                    ctx.sender.sendMessage(translation.fault.noPermission.component)
                }

                is NoPotionEffectTypeException,
                is BadArgumentException,
                is ArgumentTypeException -> {
                    error { "#handle intentionally unhandled error: $throwable" }
                }

                else -> {
                    error { "#handle unhandled error: $throwable" }
                }
            }
        }
    }
}
