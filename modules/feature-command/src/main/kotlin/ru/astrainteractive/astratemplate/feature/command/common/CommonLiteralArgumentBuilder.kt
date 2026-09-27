package ru.astrainteractive.astratemplate.feature.command.common

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import ru.astrainteractive.astralibs.command.api.brigadier.command.MultiplatformCommand
import ru.astrainteractive.astratemplate.core.plugin.PluginTranslation
import ru.astrainteractive.klibs.kstorage.api.CachedKrate
import ru.astrainteractive.klibs.kstorage.api.getValue

internal class CommonLiteralArgumentBuilder(
    translationKrate: CachedKrate<PluginTranslation>,
    private val multiplatformCommand: MultiplatformCommand
) {
    private val translation by translationKrate

    fun create(): LiteralArgumentBuilder<*> {
        return with(multiplatformCommand) {
            command("translation") {
                runs { ctx ->
                    ctx.getSender().sendMessage(translation.general.getByByCheck)
                }
            }
        }
    }
}
