package ru.astrainteractive.astratemplate.modules

import com.astrainteractive.astratemplate.domain.Repository
import com.astrainteractive.astratemplate.domain.local.entities.RatingRelationTable
import com.astrainteractive.astratemplate.domain.local.entities.UserTable
import com.astrainteractive.astratemplate.domain.remote.RickMortyApi
import com.astrainteractive.astratemplate.domain.remote.RickMortyApiImpl
import kotlinx.coroutines.runBlocking
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
import ru.astrainteractive.astratemplate.gui.SampleGUIViewModel
import ru.astrainteractive.astratemplate.utils.Files
import ru.astrainteractive.astratemplate.utils.PluginConfig
import ru.astrainteractive.astratemplate.utils.PluginTranslation
import java.io.File

val PluginConfigModule = reloadable {
    EmpireSerializer.toClass<PluginConfig>(Files.configFile) ?: PluginConfig()
}
val TranslationModule = reloadable {
    PluginTranslation()
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

val SampleGuiViewModelFactory = factory {
    val repository by RepositoryModule
    SampleGUIViewModel(repository, ItemStackSpigotAPI)
}
val eventHandlerModule = module {
    EventManager()
}