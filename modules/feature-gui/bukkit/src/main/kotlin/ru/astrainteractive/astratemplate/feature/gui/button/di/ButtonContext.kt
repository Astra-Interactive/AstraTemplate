package ru.astrainteractive.astratemplate.feature.gui.button.di

import ru.astrainteractive.astralibs.kyori.KyoriComponentSerializer
import ru.astrainteractive.astratemplate.core.di.CoreModule
import ru.astrainteractive.astratemplate.core.plugin.PluginTranslation
import ru.astrainteractive.klibs.kstorage.api.getValue
import ru.astrainteractive.klibs.mikro.core.logging.JUtiltLogger
import ru.astrainteractive.klibs.mikro.core.logging.Logger

internal interface ButtonContext : KyoriComponentSerializer, Logger {
    val translation: PluginTranslation

    class Default(
        coreModule: CoreModule
    ) : ButtonContext,
        Logger by JUtiltLogger("AstraTemplate-ButtonContext"),
        KyoriComponentSerializer by coreModule.kyoriKrate.cachedValue {
        override val translation by coreModule.translationKrate
    }
}
