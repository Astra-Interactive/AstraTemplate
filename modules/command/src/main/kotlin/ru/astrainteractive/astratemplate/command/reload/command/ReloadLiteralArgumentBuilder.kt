package ru.astrainteractive.astratemplate.command.reload.command

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import ru.astrainteractive.astralibs.command.api.brigadier.command.MultiplatformCommand
import ru.astrainteractive.astralibs.lifecycle.Lifecycle
import ru.astrainteractive.astratemplate.command.error.CommandErrorHandler
import ru.astrainteractive.astratemplate.core.plugin.model.PluginPermission
import ru.astrainteractive.astratemplate.core.plugin.model.PluginTranslation
import ru.astrainteractive.klibs.kstorage.api.CachedKrate
import ru.astrainteractive.klibs.kstorage.api.getValue

internal class ReloadLiteralArgumentBuilder(
    translationKrate: CachedKrate<PluginTranslation>,
    private val lifecyclePlugin: Lifecycle,
    private val multiplatformCommand: MultiplatformCommand,
    private val errorHandler: CommandErrorHandler
) {
    private val translation by translationKrate

    fun create(): LiteralArgumentBuilder<*> {
        return with(multiplatformCommand) {
            command("atempreload") {
                runs(errorHandler::handle) { ctx ->
                    ctx.requirePermission(PluginPermission.Reload)
                    ctx.getSender().sendMessage(translation.reload.started)
                    lifecyclePlugin.onReload()
                    ctx.getSender().sendMessage(translation.reload.completed)
                }
            }
        }
    }
}
