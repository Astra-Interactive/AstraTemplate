package ru.astrainteractive.astratemplate.command.damage.di

import org.bukkit.plugin.java.JavaPlugin
import ru.astrainteractive.astralibs.string.BukkitTranslationContext
import ru.astrainteractive.astratemplate.core.PluginTranslation

interface DamageCommandDependencies {
    val plugin: JavaPlugin
    val translation: PluginTranslation
    val translationContext: BukkitTranslationContext
}
