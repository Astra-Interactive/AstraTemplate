package ru.astrainteractive.astratemplate.gui.di

import ru.astrainteractive.astralibs.async.BukkitDispatchers
import ru.astrainteractive.astralibs.serialization.KyoriComponentSerializer
import ru.astrainteractive.astratemplate.api.local.di.ApiLocalModule
import ru.astrainteractive.astratemplate.core.PluginTranslation
import ru.astrainteractive.astratemplate.core.di.CoreModule
import ru.astrainteractive.astratemplate.di.BukkitModule
import ru.astrainteractive.astratemplate.gui.api.ItemStackSpigotAPI
import ru.astrainteractive.astratemplate.gui.domain.GetRandomColorUseCaseImpl
import ru.astrainteractive.astratemplate.gui.domain.SetDisplayNameUseCaseImpl
import ru.astrainteractive.astratemplate.gui.sample.DefaultSampleGUIComponent
import ru.astrainteractive.klibs.kdi.Factory
import ru.astrainteractive.klibs.kdi.Module
import ru.astrainteractive.klibs.kdi.getValue

interface SampleGuiDependencies : Module {
    val translation: PluginTranslation
    val dispatchers: BukkitDispatchers
    val kyoriComponentSerializer: KyoriComponentSerializer
    val viewModelFactory: Factory<DefaultSampleGUIComponent>

    class Default(
        coreModule: CoreModule,
        bukkitModule: BukkitModule,
        apiLocalModule: ApiLocalModule
    ) : SampleGuiDependencies {
        override val translation by coreModule.translation
        override val dispatchers by bukkitModule.bukkitDispatchers
        override val kyoriComponentSerializer by bukkitModule.kyoriComponentSerializer
        private val getRandomColorUseCase = GetRandomColorUseCaseImpl()
        private val setDisplayNameUseCase = SetDisplayNameUseCaseImpl(getRandomColorUseCase)
        override val viewModelFactory: Factory<DefaultSampleGUIComponent> = Factory {
            val localApi = apiLocalModule.localApi
            DefaultSampleGUIComponent(
                localApi = localApi,
                itemStackSpigotAPi = ItemStackSpigotAPI,
                getRandomColorUseCase = getRandomColorUseCase,
                setDisplayNameUseCase = setDisplayNameUseCase
            )
        }
    }
}
