package ru.astrainteractive.astratemplate.modules

import ru.astrainteractive.astralibs.di.IReloadable
import ru.astrainteractive.astratemplate.utils.PluginTranslation
import kotlin.reflect.KProperty

object TranslationProvider : IReloadable<PluginTranslation>() {
    override fun initializer(): PluginTranslation = PluginTranslation()
}