package ru.astrainteractive.astratemplate.di.impl

import ru.astrainteractive.astratemplate.api.ItemStackSpigotAPI
import ru.astrainteractive.astratemplate.di.RootModule
import ru.astrainteractive.astratemplate.gui.SampleGUIViewModel
import ru.astrainteractive.astratemplate.gui.di.SampleGuiModule
import ru.astrainteractive.klibs.kdi.Factory
import ru.astrainteractive.klibs.kdi.getValue

internal class SampleGuiModuleImpl(
    private val rootModule: RootModule
) : SampleGuiModule {

    override val translation by rootModule.servicesModule.translation
    override val dispatchers by rootModule.servicesModule.bukkitDispatchers
    override val viewModelFactory: Factory<SampleGUIViewModel> = Factory {
        val localApi = rootModule.apiLocalModule.localApi
        SampleGUIViewModel(localApi, ItemStackSpigotAPI)
    }
}
