package com.astrainteractive.astratemplate.modules

import com.astrainteractive.astratemplate.utils.PluginTranslation
import ru.astrainteractive.astralibs.di.IReloadable

object TranslationProvider : IReloadable<PluginTranslation>() {
    override fun initializer(): PluginTranslation = PluginTranslation()
}