package ru.astrainteractive.astratemplate.commands.di

import com.astrainteractive.astratemplate.api.remote.RickMortyApi
import org.bukkit.entity.Player
import ru.astrainteractive.astralibs.Dependency
import ru.astrainteractive.astralibs.Factory
import ru.astrainteractive.astralibs.Provider
import ru.astrainteractive.astralibs.async.AsyncComponent
import ru.astrainteractive.astralibs.async.BukkitDispatchers
import ru.astrainteractive.astratemplate.AstraTemplate
import ru.astrainteractive.astratemplate.gui.SampleGUI
import ru.astrainteractive.astratemplate.plugin.Translation

interface CommandManagerModule {
    val plugin: Dependency<AstraTemplate>
    val translationModule: Dependency<Translation>
    val rmApiModule: Dependency<RickMortyApi>
    val pluginScope: Dependency<AsyncComponent>
    val dispatchers: Dependency<BukkitDispatchers>
    val randomIntProvider: Provider<Int>
    fun sampleGuiFactory(player: Player): Factory<SampleGUI>
}
