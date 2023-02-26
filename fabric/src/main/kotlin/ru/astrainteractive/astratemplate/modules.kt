package ru.astrainteractive.astratemplate

import com.astrainteractive.astratemplate.domain.Repository
import com.astrainteractive.astratemplate.domain.local.entities.RatingRelationTable
import com.astrainteractive.astratemplate.domain.local.entities.UserTable
import com.astrainteractive.astratemplate.domain.remote.RickMortyApi
import com.astrainteractive.astratemplate.domain.remote.RickMortyApiImpl
import kotlinx.coroutines.runBlocking
import ru.astrainteractive.astralibs.di.module
import ru.astrainteractive.astralibs.orm.DBConnection
import ru.astrainteractive.astralibs.orm.DBSyntax
import ru.astrainteractive.astralibs.orm.DefaultDatabase

val sqlModule = module {
    runBlocking {
        val connection = DBConnection.SQLite("data.db")
        DefaultDatabase(connection, DBSyntax.SQLite).also {
            it.openConnection()
            UserTable.create(it)
            RatingRelationTable.create(it)
        }
    }
}

val restModule = module {
    RickMortyApiImpl() as RickMortyApi
}
val repositoryModule = module {
    Repository(sqlModule.value, restModule.value)
}
