package ru.astrainteractive.astratemplate.feature.gui.button.di

import ru.astrainteractive.astratemplate.core.di.CoreModule
import ru.astrainteractive.astratemplate.core.plugin.PluginTranslation
import ru.astrainteractive.klibs.kstorage.api.getValue
import ru.astrainteractive.klibs.mikro.core.logging.JUtiltLogger
import ru.astrainteractive.klibs.mikro.core.logging.Logger
import java.util.Locale

/** What the buttons of one menu need, including the language of the player who opened the menu. */
internal interface ButtonContext : Logger {
    val translation: PluginTranslation
    val locale: Locale

    class Default(
        coreModule: CoreModule,
        override val locale: Locale
    ) : ButtonContext,
        Logger by JUtiltLogger("AstraTemplate-ButtonContext") {
        override val translation by coreModule.translationKrate
    }
}
