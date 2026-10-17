package ru.astrainteractive.astratemplate.command.error

import com.mojang.brigadier.context.CommandContext
import ru.astrainteractive.astralibs.command.api.brigadier.command.MultiplatformCommand
import ru.astrainteractive.astralibs.command.api.exception.ArgumentConverterException
import ru.astrainteractive.astralibs.command.api.exception.BadArgumentException
import ru.astrainteractive.astralibs.command.api.exception.NoPermissionException
import ru.astrainteractive.astralibs.command.api.exception.NoPotionEffectTypeException
import ru.astrainteractive.astratemplate.command.additem.model.AddItemError
import ru.astrainteractive.astratemplate.core.plugin.model.PluginTranslation
import ru.astrainteractive.klibs.kstorage.api.CachedKrate
import ru.astrainteractive.klibs.kstorage.api.getValue
import ru.astrainteractive.klibs.mikro.core.logging.JUtiltLogger
import ru.astrainteractive.klibs.mikro.core.logging.Logger

internal class CommandErrorHandler(
    private val multiplatformCommand: MultiplatformCommand,
    translationKrate: CachedKrate<PluginTranslation>
) : Logger by JUtiltLogger("AstraTemplate-CommandErrorHandler") {
    private val translation by translationKrate

    fun handle(ctx: CommandContext<Any>, throwable: Throwable) {
        with(multiplatformCommand) {
            val sender = ctx.getSender()
            when (throwable) {
                is AddItemError -> {
                    when (throwable) {
                        is AddItemError.ItemNotFound -> {
                            sender.sendMessage(translation.addItem.itemNotFound)
                        }
                        is AddItemError.SenderNotPlayer -> {
                            sender.sendMessage(translation.commandError.notPlayer)
                        }
                    }
                }
                is NoPermissionException -> {
                    sender.sendMessage(translation.commandError.noPermission)
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
