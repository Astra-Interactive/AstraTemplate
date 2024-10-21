package ru.astrainteractive.astratemplate.command.damage.di

import org.bukkit.plugin.java.JavaPlugin
import ru.astrainteractive.astralibs.kyori.KyoriComponentSerializer
import ru.astrainteractive.astratemplate.core.PluginTranslation
import ru.astrainteractive.klibs.kstorage.api.Krate

internal interface DamageCommandDependencies {
    val plugin: JavaPlugin
    val translation: PluginTranslation
    val kyori: KyoriComponentSerializer
    val translationKrate: Krate<PluginTranslation>
    val kyoriKrate: Krate<KyoriComponentSerializer>
}
