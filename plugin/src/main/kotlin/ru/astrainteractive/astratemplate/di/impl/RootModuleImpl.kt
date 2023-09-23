@file:OptIn(UnsafeApi::class)

package ru.astrainteractive.astratemplate.di.impl

import CommandManager
import kotlinx.serialization.encodeToString
import org.jetbrains.kotlin.tooling.core.UnsafeApi
import ru.astrainteractive.astralibs.async.AsyncComponent
import ru.astrainteractive.astralibs.async.DefaultBukkitDispatchers
import ru.astrainteractive.astralibs.configloader.ConfigLoader
import ru.astrainteractive.astralibs.logging.Logger
import ru.astrainteractive.astralibs.orm.Database
import ru.astrainteractive.astralibs.util.buildWithSpigot
import ru.astrainteractive.astratemplate.AstraTemplate
import ru.astrainteractive.astratemplate.api.local.mapping.RatingMapperImpl
import ru.astrainteractive.astratemplate.api.local.mapping.UserMapperImpl
import ru.astrainteractive.astratemplate.di.FilesModule
import ru.astrainteractive.astratemplate.di.RootModule
import ru.astrainteractive.astratemplate.di.factory.DatabaseFactory
import ru.astrainteractive.astratemplate.di.factory.LocalApiFactory
import ru.astrainteractive.astratemplate.di.factory.RickMortyApiFactory
import ru.astrainteractive.astratemplate.event.EventManager
import ru.astrainteractive.astratemplate.plugin.MainConfiguration
import ru.astrainteractive.astratemplate.plugin.Translation
import ru.astrainteractive.klibs.kdi.Lateinit
import ru.astrainteractive.klibs.kdi.Reloadable
import ru.astrainteractive.klibs.kdi.Single
import ru.astrainteractive.klibs.kdi.getValue
import java.io.File

internal class RootModuleImpl : RootModule {
    override val plugin = Lateinit<AstraTemplate>()
    override val configLoader: Single<ConfigLoader> = Single {
        ConfigLoader()
    }
    override val logger = Single {
        Logger.buildWithSpigot("AstraTemplate", plugin.value)
    }
    override val bukkitDispatchers = Single {
        DefaultBukkitDispatchers(plugin.value)
    }
    override val filesModule: FilesModule by Single {
        FilesModuleImpl(this)
    }
    override val pluginScope = Single {
        object : AsyncComponent() {} as AsyncComponent
    }

    override val configurationModule = Reloadable {
        val filesModule by filesModule
        val configFile by filesModule.configFile
        val configLoader by configLoader
        val configuration = configLoader.toClassOrDefault(configFile.configFile, ::MainConfiguration)
        if (!configFile.configFile.exists()) {
            configFile.configFile.createNewFile()
            configFile.configFile.writeText(configLoader.yaml.encodeToString(configuration))
        }
        configuration
    }

    override val translation = Reloadable {
        val plugin by plugin
        Translation(plugin)
    }

    override val database: Single<Database> = Single {
        val plugin by plugin
        DatabaseFactory("${plugin.dataFolder}${File.separator}data.db").create()
    }

    override val rmApiModule = Single {
        RickMortyApiFactory().create()
    }

    override val localApiModule = Single {
        LocalApiFactory(
            database = database.value,
            ratingMapper = RatingMapperImpl,
            userMapper = UserMapperImpl
        ).create()
    }

    override val eventHandlerModule = Single {
        val eventModule = EventModuleImpl(this)
        EventManager(eventModule)
    }

    override val commandManager = Single {
        val commandManagerModule = CommandManagerModuleImpl(this)
        CommandManager(commandManagerModule)
    }
}
