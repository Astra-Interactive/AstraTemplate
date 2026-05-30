package ru.astrainteractive.astratemplate.feature.command.common

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import ru.astrainteractive.astralibs.command.api.brigadier.command.MultiplatformCommand
import ru.astrainteractive.astralibs.command.api.registrar.CommandRegistrarContext
import ru.astrainteractive.astralibs.kyori.KyoriComponentSerializer
import ru.astrainteractive.astralibs.kyori.unwrap
import ru.astrainteractive.astratemplate.core.plugin.PluginTranslation
import ru.astrainteractive.klibs.kstorage.api.CachedKrate
import ru.astrainteractive.klibs.kstorage.api.getValue

internal class CommonCommandsRegistry(
    translationKrate: CachedKrate<PluginTranslation>,
    kyoriKrate: CachedKrate<KyoriComponentSerializer>,
    private val registrarContext: CommandRegistrarContext,
    private val multiplatformCommand: MultiplatformCommand
) : KyoriComponentSerializer by kyoriKrate.unwrap() {
    private val translation by translationKrate

    private fun createNode(): LiteralArgumentBuilder<*> {
        return with(multiplatformCommand) {
            command("translation") {
                runs { ctx ->
                    ctx.getSender().sendMessage(translation.general.getByByCheck.component)
                }
            }
        }
    }

    fun register() {
        registrarContext.registerWhenReady(createNode())
    }
}
