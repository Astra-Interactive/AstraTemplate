package ru.astrainteractive.astratemplate.command.gui.di

import org.bukkit.plugin.java.JavaPlugin
import ru.astrainteractive.astralibs.string.BukkitTranslationContext
import ru.astrainteractive.astratemplate.core.PluginTranslation
import ru.astrainteractive.astratemplate.gui.router.Router

interface GuiCommandDependencies {
    val plugin: JavaPlugin
    val translation: PluginTranslation
    val translationContext: BukkitTranslationContext
    val router: Router
}
