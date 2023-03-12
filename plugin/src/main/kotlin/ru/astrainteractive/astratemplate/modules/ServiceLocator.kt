package ru.astrainteractive.astratemplate.modules

import CommandManager
import com.astrainteractive.astratemplate.api.local.LocalApiFactory
import com.astrainteractive.astratemplate.api.local.entities.UserRatingTable
import com.astrainteractive.astratemplate.api.local.entities.UserTable
import com.astrainteractive.astratemplate.api.remote.RickMortyApiFactory
import kotlinx.coroutines.runBlocking
import org.bukkit.entity.Player
import ru.astrainteractive.astralibs.AstraLibs
import ru.astrainteractive.astralibs.EmpireSerializer
import ru.astrainteractive.astralibs.di.factory
import ru.astrainteractive.astralibs.di.getValue
import ru.astrainteractive.astralibs.di.module
import ru.astrainteractive.astralibs.di.reloadable
import ru.astrainteractive.astralibs.orm.DBConnection
import ru.astrainteractive.astralibs.orm.DBSyntax
import ru.astrainteractive.astralibs.orm.DefaultDatabase
import ru.astrainteractive.astralibs.utils.toClass
import ru.astrainteractive.astratemplate.api.ItemStackSpigotAPI
import ru.astrainteractive.astratemplate.events.EventManager
import ru.astrainteractive.astratemplate.gui.SampleGUI
import ru.astrainteractive.astratemplate.gui.SampleGUIViewModel
import ru.astrainteractive.astratemplate.plugin.Files
import ru.astrainteractive.astratemplate.plugin.MainConfiguration
import ru.astrainteractive.astratemplate.plugin.Translation
import java.io.File

object ServiceLocator {
    val configurationModule = reloadable {
        EmpireSerializer.toClass<MainConfiguration>(Files.configFile) ?: MainConfiguration()
    }
    val translationModule = reloadable {
        Translation()
    }

    val rmApiModule = module {
        RickMortyApiFactory().value
    }

    val databaseModule = module {
        runBlocking {
            val connection = DBConnection.SQLite("${AstraLibs.instance.dataFolder}${File.separator}data.db")
            DefaultDatabase(connection, DBSyntax.SQLite).also {
                it.openConnection()
                UserTable.create(it)
                UserRatingTable.create(it)
            }
        }
    }
    val localApiModule = module {
        val database by databaseModule
        LocalApiFactory(database).value
    }

    val eventHandlerModule = module {
        EventManager(translationModule = translationModule)
    }

    val commandManager = module {
        CommandManager(
            translationModule = translationModule,
            rmApiModule = rmApiModule,
            guiFactories = Guis
        )
    }

    object ViewModels {
        val SampleGuiViewModelFactory = factory {
            val rmApi by rmApiModule
            val localApi by localApiModule
            SampleGUIViewModel(rmApi, localApi, ItemStackSpigotAPI)
        }
    }

    object Guis {
        fun sampleGuiFactory(player: Player) = factory {
            SampleGUI(
                player = player,
                translationModule = translationModule,
                viewModel = ViewModels.SampleGuiViewModelFactory.value
            )
        }
    }
}
