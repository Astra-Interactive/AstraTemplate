package ru.astrainteractive.astratemplate.command.rickmorty.di

import kotlinx.coroutines.CoroutineScope
import org.bukkit.plugin.java.JavaPlugin
import ru.astrainteractive.astralibs.async.BukkitDispatchers
import ru.astrainteractive.astratemplate.api.remote.RickMortyApi
import ru.astrainteractive.klibs.kdi.Provider

interface RickMortyCommandDependencies {
    val plugin: JavaPlugin
    val scope: CoroutineScope
    val dispatchers: BukkitDispatchers
    val rmApi: RickMortyApi
    val randomIntProvider: Provider<Int>
}
