package ru.astrainteractive.astratemplate.command.additem.di

import org.bukkit.plugin.java.JavaPlugin
import ru.astrainteractive.astralibs.kyori.KyoriComponentSerializer
import ru.astrainteractive.astratemplate.core.PluginTranslation
import ru.astrainteractive.klibs.kstorage.api.Krate

internal interface AddItemCommandDependencies {
    val plugin: JavaPlugin
    val translationKrate: Krate<PluginTranslation>
    val kyoriKrate: Krate<KyoriComponentSerializer>
}
