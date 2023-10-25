package ru.astrainteractive.astratemplate.command.di

import org.bukkit.entity.Player
import ru.astrainteractive.astralibs.async.AsyncComponent
import ru.astrainteractive.astralibs.async.BukkitDispatchers
import ru.astrainteractive.astralibs.permission.PermissionManager
import ru.astrainteractive.astratemplate.AstraTemplate
import ru.astrainteractive.astratemplate.api.remote.RickMortyApi
import ru.astrainteractive.astratemplate.di.RootModule
import ru.astrainteractive.astratemplate.gui.SampleGUI
import ru.astrainteractive.astratemplate.gui.di.SampleGuiDependencies
import ru.astrainteractive.astratemplate.shared.core.Translation
import ru.astrainteractive.klibs.kdi.Factory
import ru.astrainteractive.klibs.kdi.Provider
import ru.astrainteractive.klibs.kdi.getValue
import kotlin.random.Random

interface CommandManagerDependencies {
    val plugin: AstraTemplate
    val translation: Translation
    val rmApi: RickMortyApi
    val pluginScope: AsyncComponent
    val dispatchers: BukkitDispatchers
    val randomIntProvider: Provider<Int>
    val permissionManager: PermissionManager

    fun sampleGuiFactory(player: Player): Factory<SampleGUI>

    class Default(rootModule: RootModule) : CommandManagerDependencies {
        override val plugin by rootModule.bukkitModule.plugin
        override val translation by rootModule.sharedModule.translation
        override val rmApi = rootModule.apiRemoteModule.rickMortyApi
        override val pluginScope by rootModule.sharedModule.pluginScope
        override val dispatchers by rootModule.bukkitModule.bukkitDispatchers
        override val randomIntProvider: Provider<Int> = Provider { Random.nextInt(1, 100) }

        override val permissionManager: PermissionManager by rootModule.bukkitModule.permissionManager

        private val sampleGuiDependencies by Provider {
            SampleGuiDependencies.Default(rootModule)
        }
        override fun sampleGuiFactory(player: Player): Factory<SampleGUI> = Factory {
            val sampleGuiDependencies: SampleGuiDependencies by sampleGuiDependencies
            SampleGUI(
                player = player,
                module = sampleGuiDependencies
            )
        }
    }
}
