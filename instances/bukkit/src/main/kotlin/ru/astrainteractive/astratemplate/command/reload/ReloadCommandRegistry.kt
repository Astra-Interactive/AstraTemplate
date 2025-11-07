package ru.astrainteractive.astratemplate.command.reload

import com.mojang.brigadier.tree.LiteralCommandNode
import io.papermc.paper.command.brigadier.CommandSourceStack
import ru.astrainteractive.astralibs.command.api.util.command
import ru.astrainteractive.astralibs.command.api.util.requirePermission
import ru.astrainteractive.astralibs.command.api.util.runs
import ru.astrainteractive.astralibs.kyori.KyoriComponentSerializer
import ru.astrainteractive.astralibs.kyori.unwrap
import ru.astrainteractive.astralibs.lifecycle.LifecyclePlugin
import ru.astrainteractive.astratemplate.command.errorhandler.DefaultErrorHandler
import ru.astrainteractive.astratemplate.core.plugin.PluginPermission
import ru.astrainteractive.astratemplate.core.plugin.PluginTranslation
import ru.astrainteractive.klibs.kstorage.api.CachedKrate
import ru.astrainteractive.klibs.kstorage.util.getValue

internal class ReloadCommandRegistry(
    translationKrate: CachedKrate<PluginTranslation>,
    kyoriKrate: CachedKrate<KyoriComponentSerializer>,
    private val plugin: LifecyclePlugin,
    private val errorHandler: DefaultErrorHandler
) : KyoriComponentSerializer by kyoriKrate.unwrap() {
    private val translation by translationKrate

    fun createNode(): LiteralCommandNode<CommandSourceStack> {
        return command("atempreload") {
            runs(errorHandler::handle) { ctx ->
                ctx.requirePermission(PluginPermission.Damage)
                ctx.source.sender.sendMessage(translation.general.reload.component)
                plugin.onReload()
                ctx.source.sender.sendMessage(translation.general.reloadComplete.component)
            }
        }.build()
    }
}
