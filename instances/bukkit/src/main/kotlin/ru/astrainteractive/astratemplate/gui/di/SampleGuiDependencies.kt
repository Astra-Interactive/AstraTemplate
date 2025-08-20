package ru.astrainteractive.astratemplate.gui.di

import ru.astrainteractive.astralibs.kyori.KyoriComponentSerializer
import ru.astrainteractive.astratemplate.api.local.di.ApiLocalModule
import ru.astrainteractive.astratemplate.core.plugin.PluginTranslation
import ru.astrainteractive.astratemplate.core.di.CoreModule
import ru.astrainteractive.astratemplate.core.util.getValue
import ru.astrainteractive.astratemplate.di.BukkitModule
import ru.astrainteractive.astratemplate.gui.api.ItemStackSpigotAPI
import ru.astrainteractive.astratemplate.gui.domain.GetRandomColorUseCaseImpl
import ru.astrainteractive.astratemplate.gui.domain.SetDisplayNameUseCaseImpl
import ru.astrainteractive.astratemplate.gui.sample.DefaultSampleGUIComponent
import ru.astrainteractive.klibs.mikro.core.dispatchers.KotlinDispatchers

internal interface SampleGuiDependencies {
    val translation: PluginTranslation
    val dispatchers: KotlinDispatchers
    val kyoriComponentSerializer: KyoriComponentSerializer
    fun createDefaultSampleGUIComponent(): DefaultSampleGUIComponent

    class Default(
        coreModule: CoreModule,
        bukkitModule: BukkitModule,
        private val apiLocalModule: ApiLocalModule
    ) : SampleGuiDependencies {
        override val translation by coreModule.translationKrate
        override val dispatchers = bukkitModule.dispatchers
        override val kyoriComponentSerializer by bukkitModule.kyoriComponentSerializer
        private val getRandomColorUseCase = GetRandomColorUseCaseImpl()
        private val setDisplayNameUseCase = SetDisplayNameUseCaseImpl(getRandomColorUseCase)
        override fun createDefaultSampleGUIComponent(): DefaultSampleGUIComponent {
            val localApi = apiLocalModule.localDao
            return DefaultSampleGUIComponent(
                localDao = localApi,
                itemStackSpigotAPi = ItemStackSpigotAPI,
                getRandomColorUseCase = getRandomColorUseCase,
                setDisplayNameUseCase = setDisplayNameUseCase
            )
        }
    }
}
