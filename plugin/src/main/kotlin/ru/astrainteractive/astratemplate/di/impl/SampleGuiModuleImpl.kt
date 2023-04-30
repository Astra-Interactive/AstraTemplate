package ru.astrainteractive.astratemplate.di.impl

import ru.astrainteractive.astralibs.Dependency
import ru.astrainteractive.astralibs.Factory
import ru.astrainteractive.astralibs.async.BukkitDispatchers
import ru.astrainteractive.astralibs.getValue
import ru.astrainteractive.astratemplate.api.ItemStackSpigotAPI
import ru.astrainteractive.astratemplate.gui.SampleGUIViewModel
import ru.astrainteractive.astratemplate.gui.di.SampleGuiModule
import ru.astrainteractive.astratemplate.plugin.Translation

object SampleGuiModuleImpl : SampleGuiModule {
    private val rootModule by RootModuleImpl
    private val pluginModule by rootModule.pluginModule

    override val translation: Dependency<Translation> = rootModule.translationModule
    override val dispatchers: Dependency<BukkitDispatchers> = pluginModule.bukkitDispatchers
    override val viewModel: Factory<SampleGUIViewModel> = Factory {
        val localApi by RootModuleImpl.localApiModule
        SampleGUIViewModel(localApi, ItemStackSpigotAPI)
    }
}
