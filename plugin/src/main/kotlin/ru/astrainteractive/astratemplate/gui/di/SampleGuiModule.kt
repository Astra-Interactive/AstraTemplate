package ru.astrainteractive.astratemplate.gui.di

import ru.astrainteractive.astralibs.Dependency
import ru.astrainteractive.astralibs.Factory
import ru.astrainteractive.astralibs.Module
import ru.astrainteractive.astralibs.async.BukkitDispatchers
import ru.astrainteractive.astratemplate.gui.SampleGUIViewModel
import ru.astrainteractive.astratemplate.plugin.Translation

interface SampleGuiModule : Module {
    val translation: Dependency<Translation>
    val dispatchers: Dependency<BukkitDispatchers>
    val viewModel: Factory<SampleGUIViewModel>
}
