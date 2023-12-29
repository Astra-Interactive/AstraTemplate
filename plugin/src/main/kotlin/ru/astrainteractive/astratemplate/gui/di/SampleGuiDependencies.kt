package ru.astrainteractive.astratemplate.gui.di

import ru.astrainteractive.astralibs.async.BukkitDispatchers
import ru.astrainteractive.astralibs.string.BukkitTranslationContext
import ru.astrainteractive.astratemplate.api.ItemStackSpigotAPI
import ru.astrainteractive.astratemplate.di.RootModule
import ru.astrainteractive.astratemplate.gui.DefaultSampleGUIComponent
import ru.astrainteractive.astratemplate.gui.domain.GetRandomColorUseCaseImpl
import ru.astrainteractive.astratemplate.gui.domain.SetDisplayNameUseCaseImpl
import ru.astrainteractive.astratemplate.core.PluginTranslation
import ru.astrainteractive.klibs.kdi.Factory
import ru.astrainteractive.klibs.kdi.Module
import ru.astrainteractive.klibs.kdi.getValue

interface SampleGuiDependencies : Module {
    val translation: PluginTranslation
    val dispatchers: BukkitDispatchers
    val bukkitTranslationContext: BukkitTranslationContext
    val viewModelFactory: Factory<DefaultSampleGUIComponent>

    class Default(rootModule: RootModule) : SampleGuiDependencies {
        override val translation by rootModule.coreModule.translation
        override val dispatchers by rootModule.bukkitModule.bukkitDispatchers
        override val bukkitTranslationContext by rootModule.bukkitModule.bukkitTranslationContext
        private val getRandomColorUseCase = GetRandomColorUseCaseImpl()
        private val setDisplayNameUseCase = SetDisplayNameUseCaseImpl(getRandomColorUseCase)
        override val viewModelFactory: Factory<DefaultSampleGUIComponent> = Factory {
            val localApi = rootModule.apiLocalModule.localApi
            DefaultSampleGUIComponent(
                localApi = localApi,
                itemStackSpigotAPi = ItemStackSpigotAPI,
                getRandomColorUseCase = getRandomColorUseCase,
                setDisplayNameUseCase = setDisplayNameUseCase
            )
        }
    }
}
