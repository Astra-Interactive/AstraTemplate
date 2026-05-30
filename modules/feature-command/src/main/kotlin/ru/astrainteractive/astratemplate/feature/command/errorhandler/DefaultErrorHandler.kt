package ru.astrainteractive.astratemplate.feature.command.errorhandler

import com.mojang.brigadier.context.CommandContext
import ru.astrainteractive.astralibs.command.api.brigadier.command.MultiplatformCommand
import ru.astrainteractive.astralibs.command.api.exception.ArgumentConverterException
import ru.astrainteractive.astralibs.command.api.exception.BadArgumentException
import ru.astrainteractive.astralibs.command.api.exception.NoPermissionException
import ru.astrainteractive.astralibs.command.api.exception.NoPotionEffectTypeException
import ru.astrainteractive.astralibs.kyori.KyoriComponentSerializer
import ru.astrainteractive.astralibs.kyori.unwrap
import ru.astrainteractive.astratemplate.core.plugin.PluginTranslation
import ru.astrainteractive.astratemplate.feature.command.additem.AddItemCommand
import ru.astrainteractive.klibs.kstorage.api.CachedKrate
import ru.astrainteractive.klibs.kstorage.api.getValue
import ru.astrainteractive.klibs.mikro.core.logging.JUtiltLogger
import ru.astrainteractive.klibs.mikro.core.logging.Logger

internal class DefaultErrorHandler(
    private val multiplatformCommand: MultiplatformCommand,
    translationKrate: CachedKrate<PluginTranslation>,
    kyoriKrate: CachedKrate<KyoriComponentSerializer>
) : Logger by JUtiltLogger("AstraTemplate-DefaultErrorHandler"),
    KyoriComponentSerializer by kyoriKrate.unwrap() {
    private val translation by translationKrate

    fun handle(ctx: CommandContext<Any>, throwable: Throwable) {
        with(multiplatformCommand) {
            val sender = ctx.getSender()
            when (throwable) {
                is AddItemCommand.Error -> {
                    when (throwable) {
                        is AddItemCommand.Error.ItemNotfound -> {
                            sender.sendMessage(translation.fault.itemNotFound.component)
                        }
                        is AddItemCommand.Error.SenderNotPlayer -> {
                            sender.sendMessage(translation.fault.notPlayer.component)
                        }
                    }
                }
                is NoPermissionException -> {
                    sender.sendMessage(translation.fault.noPermission.component)
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
}
