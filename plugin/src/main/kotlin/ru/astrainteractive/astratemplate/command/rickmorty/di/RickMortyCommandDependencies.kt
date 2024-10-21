package ru.astrainteractive.astratemplate.command.rickmorty.di

import kotlinx.coroutines.CoroutineScope
import org.bukkit.plugin.java.JavaPlugin
import ru.astrainteractive.astratemplate.api.remote.RickMortyApi
import ru.astrainteractive.klibs.mikro.core.dispatchers.KotlinDispatchers

interface RickMortyCommandDependencies {
    val plugin: JavaPlugin
    val scope: CoroutineScope
    val dispatchers: KotlinDispatchers
    val rmApi: RickMortyApi
    fun getRabdomInt(): Int
}
