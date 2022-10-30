package ru.astrainteractive.astratemplate.modules

import ru.astrainteractive.astralibs.di.IFactory
import ru.astrainteractive.astratemplate.api.ItemStackSpigotAPI
import ru.astrainteractive.astratemplate.gui.SampleGUIViewModel

/**
 * Also multiple ways to instantiate a factory
 */
object SampleGuiViewModelFactory : IFactory<SampleGUIViewModel> {
    override fun provide(): SampleGUIViewModel {
        return SampleGUIViewModel(RepositoryModule.value, ItemStackSpigotAPI)
    }
}

//val SampleGuiViewModelFactory = IFactory {
//    SampleGUIViewModel(RepositoryModule.value, ItemStackSpigotAPI)
//}