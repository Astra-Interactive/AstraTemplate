@file:OptIn(UnsafeApi::class)

package ru.astrainteractive.astratemplate.di.impl

import CommandManager
import com.astrainteractive.astratemplate.api.local.di.DatabaseFactory
import com.astrainteractive.astratemplate.api.local.di.LocalApiFactory
import com.astrainteractive.astratemplate.api.remote.di.RickMortyApiFactory
import org.jetbrains.kotlin.tooling.core.UnsafeApi
import ru.astrainteractive.astralibs.Reloadable
import ru.astrainteractive.astralibs.Single
import ru.astrainteractive.astralibs.configloader.ConfigLoader
import ru.astrainteractive.astralibs.getValue
import ru.astrainteractive.astralibs.http.HttpClient
import ru.astrainteractive.astralibs.orm.Database
import ru.astrainteractive.astratemplate.di.PluginModule
import ru.astrainteractive.astratemplate.di.RootModule
import ru.astrainteractive.astratemplate.di.factories.CustomConfigurationFactory
import ru.astrainteractive.astratemplate.events.EventManager
import ru.astrainteractive.astratemplate.plugin.MainConfiguration
import ru.astrainteractive.astratemplate.plugin.Translation
import java.io.File

internal object RootModuleImpl : RootModule {
    override val pluginModule: PluginModule by PluginModuleImpl

    override val configurationModule = Reloadable {
        val configFile by FilesModuleImpl.configFile
        ConfigLoader.toClassOrDefault(configFile.configFile, ::MainConfiguration)
    }

    override val translationModule = Reloadable {
        val plugin by pluginModule.plugin
        Translation(plugin)
    }

    override val database: Single<Database> = Single {
        val plugin by pluginModule.plugin
        DatabaseFactory("${plugin.dataFolder}${File.separator}data.db").build()
    }

    override val rmApiModule = Single {
        RickMortyApiFactory(HttpClient).build()
    }

    override val localApiModule = Single {
        LocalApiFactory(LocalApiModuleImpl).build()
    }

    override val eventHandlerModule = Single {
        EventManager(EventModuleImpl)
    }

    override val commandManager = Single {
        CommandManager(CommandManagerModuleImpl)
    }

    override val customConfiguration = Reloadable {
        CustomConfigurationFactory.build()
    }
}
