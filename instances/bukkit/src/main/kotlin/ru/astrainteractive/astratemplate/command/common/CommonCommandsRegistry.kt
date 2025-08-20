package ru.astrainteractive.astratemplate.command.common

import org.bukkit.plugin.java.JavaPlugin
import ru.astrainteractive.astralibs.kyori.KyoriComponentSerializer
import ru.astrainteractive.astralibs.kyori.unwrap
import ru.astrainteractive.astralibs.util.withEntry
import ru.astrainteractive.astratemplate.core.plugin.PluginTranslation
import ru.astrainteractive.klibs.kstorage.api.CachedKrate
import ru.astrainteractive.klibs.kstorage.util.getValue

internal class CommonCommandsRegistry(
    private val plugin: JavaPlugin,
    translationKrate: CachedKrate<PluginTranslation>,
    kyoriKrate: CachedKrate<KyoriComponentSerializer>
) : KyoriComponentSerializer by kyoriKrate.unwrap() {
    private val translation by translationKrate

    fun register() {
        plugin.getCommand("atemp")?.setTabCompleter { sender, command, label, args ->
            when {
                args.isEmpty() -> listOf("atemp", "atempreload")
                args.size == 1 -> listOf("atemp", "atempreload").withEntry(args.last())
                else -> emptyList()
            }
        }
        plugin.getCommand("translation")?.setExecutor { sender, command, label, args ->
            sender.sendMessage(translation.general.getByByCheck.component)
            true
        }
    }
}
