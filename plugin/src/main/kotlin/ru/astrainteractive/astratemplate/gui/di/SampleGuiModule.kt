package ru.astrainteractive.astratemplate.gui.di

import ru.astrainteractive.astralibs.async.BukkitDispatchers
import ru.astrainteractive.astratemplate.gui.SampleGUIViewModel
import ru.astrainteractive.astratemplate.plugin.Translation
import ru.astrainteractive.klibs.kdi.Factory
import ru.astrainteractive.klibs.kdi.Module

interface SampleGuiModule : Module {
    val translation: Translation
    val dispatchers: BukkitDispatchers
    val viewModelFactory: Factory<SampleGUIViewModel>
}
