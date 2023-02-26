package ru.astrainteractive.astratemplate.modules

import CommandManager
import com.astrainteractive.astratemplate.domain.Repository
import com.astrainteractive.astratemplate.domain.local.entities.RatingRelationTable
import com.astrainteractive.astratemplate.domain.local.entities.UserTable
import com.astrainteractive.astratemplate.domain.remote.RickMortyApi
import com.astrainteractive.astratemplate.domain.remote.RickMortyApiImpl
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
    val PluginConfigModule = reloadable {
        EmpireSerializer.toClass<MainConfiguration>(Files.configFile) ?: MainConfiguration()
    }
    val TranslationModule = reloadable {
        Translation()
    }

    val RestApiModule = module {
        RickMortyApiImpl() as RickMortyApi
    }

    val SQLDatabaseModule = module {
        runBlocking {
            val connection = DBConnection.SQLite("${AstraLibs.instance.dataFolder}${File.separator}data.db")
            DefaultDatabase(connection, DBSyntax.SQLite).also {
                it.openConnection()
                UserTable.create(it)
                RatingRelationTable.create(it)
            }
        }
    }

    val RepositoryModule = module {
        val restApi by RestApiModule
        val sqlDatabase by SQLDatabaseModule
        Repository(sqlDatabase, restApi)
    }

    val eventHandlerModule = module {
        EventManager(translationModule = TranslationModule)
    }
    val commandManager = module {
        CommandManager(
            translationModule = TranslationModule,
            repositoryModule = RepositoryModule,
            guiFactories = Guis
        )
    }

    object ViewModels {
        val SampleGuiViewModelFactory = factory {
            val repository by RepositoryModule
            SampleGUIViewModel(repository, ItemStackSpigotAPI)
        }
    }

    object Guis {
        fun sampleGuiFactory(player: Player) = factory {
            SampleGUI(
                player = player,
                translationModule = TranslationModule,
                viewModel = ViewModels.SampleGuiViewModelFactory.value
            )
        }
    }
}
