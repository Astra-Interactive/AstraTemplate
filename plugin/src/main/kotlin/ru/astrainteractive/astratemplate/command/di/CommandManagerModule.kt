package ru.astrainteractive.astratemplate.command.di

import org.bukkit.entity.Player
import ru.astrainteractive.astralibs.async.AsyncComponent
import ru.astrainteractive.astralibs.async.BukkitDispatchers
import ru.astrainteractive.astratemplate.AstraTemplate
import ru.astrainteractive.astratemplate.api.remote.RickMortyApi
import ru.astrainteractive.astratemplate.gui.SampleGUI
import ru.astrainteractive.astratemplate.plugin.Translation
import ru.astrainteractive.klibs.kdi.Factory
import ru.astrainteractive.klibs.kdi.Provider

interface CommandManagerModule {
    val plugin: AstraTemplate
    val translation: Translation
    val rmApi: RickMortyApi
    val pluginScope: AsyncComponent
    val dispatchers: BukkitDispatchers
    val randomIntProvider: Provider<Int>
    fun sampleGuiFactory(player: Player): Factory<SampleGUI>
}
