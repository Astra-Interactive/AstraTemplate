package ru.astrainteractive.astratemplate.gui.di

import ru.astrainteractive.astralibs.async.BukkitDispatchers
import ru.astrainteractive.astralibs.serialization.KyoriComponentSerializer
import ru.astrainteractive.astratemplate.api.ItemStackSpigotAPI
import ru.astrainteractive.astratemplate.di.RootModule
import ru.astrainteractive.astratemplate.gui.SampleGUIViewModel
import ru.astrainteractive.astratemplate.shared.core.Translation
import ru.astrainteractive.klibs.kdi.Factory
import ru.astrainteractive.klibs.kdi.Module
import ru.astrainteractive.klibs.kdi.getValue

interface SampleGuiDependencies : Module {
    val translation: Translation
    val dispatchers: BukkitDispatchers
    val kyoriComponentSerializer: KyoriComponentSerializer
    val viewModelFactory: Factory<SampleGUIViewModel>

    class Default(rootModule: RootModule) : SampleGuiDependencies {
        override val translation by rootModule.sharedModule.translation
        override val dispatchers by rootModule.bukkitModule.bukkitDispatchers
        override val kyoriComponentSerializer by rootModule.bukkitModule.kyoriComponentSerializer
        override val viewModelFactory: Factory<SampleGUIViewModel> = Factory {
            val localApi = rootModule.apiLocalModule.localApi
            SampleGUIViewModel(localApi, ItemStackSpigotAPI)
        }
    }
}
