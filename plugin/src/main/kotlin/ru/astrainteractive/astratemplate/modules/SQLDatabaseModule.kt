package ru.astrainteractive.astratemplate.modules

import com.astrainteractive.astratemplate.domain.local.entities.RatingRelationTable
import com.astrainteractive.astratemplate.domain.local.entities.UserTable
import kotlinx.coroutines.runBlocking
import ru.astrainteractive.astralibs.AstraLibs
import ru.astrainteractive.astralibs.database_v2.Database
import ru.astrainteractive.astralibs.di.IModule
import java.io.File

/**
 * There's two different ways to instantiate a module
 */
//val SQLDatabaseModule = module {
//    val database = Database()
//    database.openConnection("${AstraLibs.instance.dataFolder}${File.separator}data.db")
//    database
//}

object SQLDatabaseModule : IModule<Database>() {
    override fun initializer(): Database = runBlocking {
        val database = Database()
        database.openConnection("jdbc:sqlite:${AstraLibs.instance.dataFolder}${File.separator}data.db", "org.sqlite.JDBC")
        UserTable.create(database)
        RatingRelationTable.create(database)
        database
    }
}

