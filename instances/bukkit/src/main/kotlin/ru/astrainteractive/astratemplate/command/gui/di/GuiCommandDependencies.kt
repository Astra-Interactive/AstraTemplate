package ru.astrainteractive.astratemplate.command.gui.di

import org.bukkit.plugin.java.JavaPlugin
import ru.astrainteractive.astralibs.kyori.KyoriComponentSerializer
import ru.astrainteractive.astratemplate.core.plugin.PluginTranslation
import ru.astrainteractive.astratemplate.gui.router.Router
import ru.astrainteractive.klibs.kstorage.api.Krate

internal interface GuiCommandDependencies {
    val plugin: JavaPlugin
    val translationKrate: Krate<PluginTranslation>
    val kyoriKrate: Krate<KyoriComponentSerializer>
    val translation: PluginTranslation
    val kyori: KyoriComponentSerializer
    val router: Router
}
