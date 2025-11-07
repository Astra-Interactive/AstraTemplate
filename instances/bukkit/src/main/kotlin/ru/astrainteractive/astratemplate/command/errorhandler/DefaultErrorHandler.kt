package ru.astrainteractive.astratemplate.command.errorhandler

import com.mojang.brigadier.context.CommandContext
import io.papermc.paper.command.brigadier.CommandSourceStack
import ru.astrainteractive.astralibs.command.api.exception.ArgumentConverterException
import ru.astrainteractive.astralibs.command.api.exception.BadArgumentException
import ru.astrainteractive.astralibs.command.api.exception.NoPermissionException
import ru.astrainteractive.astralibs.command.api.exception.NoPlayerException
import ru.astrainteractive.astralibs.command.api.exception.NoPotionEffectTypeException
import ru.astrainteractive.astralibs.kyori.KyoriComponentSerializer
import ru.astrainteractive.astralibs.kyori.unwrap
import ru.astrainteractive.astratemplate.command.additem.AddItemCommand
import ru.astrainteractive.astratemplate.core.plugin.PluginTranslation
import ru.astrainteractive.klibs.kstorage.api.CachedKrate
import ru.astrainteractive.klibs.kstorage.util.getValue
import ru.astrainteractive.klibs.mikro.core.logging.JUtiltLogger
import ru.astrainteractive.klibs.mikro.core.logging.Logger

internal class DefaultErrorHandler(
    translationKrate: CachedKrate<PluginTranslation>,
    kyoriKrate: CachedKrate<KyoriComponentSerializer>
) : Logger by JUtiltLogger("AstraTemplate-DefaultErrorHandler"),
    KyoriComponentSerializer by kyoriKrate.unwrap() {
    private val translation by translationKrate

    fun handle(ctx: CommandContext<CommandSourceStack>, throwable: Throwable) {
        when (throwable) {
            is AddItemCommand.Error -> {
                when (throwable) {
                    AddItemCommand.Error.ItemNotfound -> {
                        ctx.source.sender.sendMessage(translation.fault.itemNotFound.component)
                    }

                    AddItemCommand.Error.SenderNotPlayer -> {
                        ctx.source.sender.sendMessage(translation.fault.notPlayer.component)
                    }
                }
            }

            is NoPlayerException -> {
                ctx.source.sender.sendMessage(translation.fault.playerNotExists.component)
            }

            is NoPermissionException -> {
                ctx.source.sender.sendMessage(translation.fault.noPermission.component)
            }

            is NoPotionEffectTypeException,
            is BadArgumentException,
            is ArgumentConverterException -> {
                error { "#handle intentionally unhandled error: $throwable" }
            }

            else -> {
                error { "#handle unhandled error: $throwable" }
            }
        }
    }
}
