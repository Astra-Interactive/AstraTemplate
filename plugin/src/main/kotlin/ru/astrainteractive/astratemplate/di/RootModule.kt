@file:OptIn(UnsafeApi::class)

package ru.astrainteractive.astratemplate.di

import CommandManager
import com.astrainteractive.astratemplate.api.local.di.DatabaseFactory
import com.astrainteractive.astratemplate.api.local.di.LocalApiFactory
import com.astrainteractive.astratemplate.api.remote.di.RickMortyApiFactory
import org.jetbrains.kotlin.tooling.core.UnsafeApi
import ru.astrainteractive.astralibs.Lateinit
import ru.astrainteractive.astralibs.Module
import ru.astrainteractive.astralibs.Reloadable
import ru.astrainteractive.astralibs.Single
import ru.astrainteractive.astralibs.async.DefaultBukkitDispatchers
import ru.astrainteractive.astralibs.async.PluginScope
import ru.astrainteractive.astralibs.configloader.ConfigLoader
import ru.astrainteractive.astralibs.getValue
import ru.astrainteractive.astralibs.http.HttpClient
import ru.astrainteractive.astralibs.logging.Logger
import ru.astrainteractive.astralibs.orm.Database
import ru.astrainteractive.astralibs.utils.buildWithSpigot
import ru.astrainteractive.astratemplate.AstraTemplate
import ru.astrainteractive.astratemplate.di.factories.CustomConfigurationFactory
import ru.astrainteractive.astratemplate.events.EventManager
import ru.astrainteractive.astratemplate.plugin.MainConfiguration
import ru.astrainteractive.astratemplate.plugin.Translation
import java.io.File

object RootModule : Module {
    val plugin = Lateinit<AstraTemplate>()
    val logger = Single {
        Logger.buildWithSpigot("AstraTemplate", plugin.value)
    }
    val configurationModule = Reloadable {
        val configFile by FilesModule.configFile
        ConfigLoader.toClassOrDefault(configFile.configFile, ::MainConfiguration)
    }
    val translationModule = Reloadable {
        val plugin by plugin
        Translation(plugin)
    }
    val bukkitDispatchers = Single {
        DefaultBukkitDispatchers(plugin.value)
    }
    val database: Single<Database> = Single {
        val plugin by RootModule.plugin
        DatabaseFactory("${plugin.dataFolder}${File.separator}data.db").build()
    }
    val rmApiModule = Single {
        RickMortyApiFactory(HttpClient).build()
    }

    val localApiModule = Single {
        LocalApiFactory(LocalApiModuleImpl).build()
    }
    val pluginScope = Single {
        PluginScope
    }

    val eventHandlerModule = Single {
        EventManager(EventModuleImpl)
    }

    val commandManager = Single {
        CommandManager(CommandManagerModuleImpl)
    }
    val customConfiguration = Reloadable {
        CustomConfigurationFactory.build()
    }
}
