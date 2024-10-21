package ru.astrainteractive.astratemplate.command.rickmorty.di

import kotlinx.coroutines.CoroutineScope
import org.bukkit.plugin.java.JavaPlugin
import ru.astrainteractive.astralibs.kyori.KyoriComponentSerializer
import ru.astrainteractive.astratemplate.api.remote.RickMortyApi
import ru.astrainteractive.astratemplate.core.PluginTranslation
import ru.astrainteractive.klibs.kstorage.api.Krate
import ru.astrainteractive.klibs.mikro.core.dispatchers.KotlinDispatchers

internal interface RickMortyCommandDependencies {
    val plugin: JavaPlugin
    val scope: CoroutineScope
    val dispatchers: KotlinDispatchers
    val rmApi: RickMortyApi
    val translationKrate: Krate<PluginTranslation>
    val kyoriKrate: Krate<KyoriComponentSerializer>
    fun getRabdomInt(): Int
}
