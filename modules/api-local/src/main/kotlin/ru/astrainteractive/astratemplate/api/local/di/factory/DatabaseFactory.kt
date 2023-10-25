package ru.astrainteractive.astratemplate.api.local.di.factory

import kotlinx.coroutines.runBlocking
import ru.astrainteractive.astralibs.orm.DBConnection
import ru.astrainteractive.astralibs.orm.DBSyntax
import ru.astrainteractive.astralibs.orm.Database
import ru.astrainteractive.astralibs.orm.DefaultDatabase
import ru.astrainteractive.astratemplate.api.local.entity.UserRatingTable
import ru.astrainteractive.astratemplate.api.local.entity.UserTable
import ru.astrainteractive.klibs.kdi.Factory

internal class DatabaseFactory(
    private val path: String
) : Factory<Database> {
    override fun create(): Database = runBlocking {
        val connection = DBConnection.SQLite(path)
        DefaultDatabase(connection, DBSyntax.SQLite).also {
            it.openConnection()
            UserTable.create(it)
            UserRatingTable.create(it)
        }
    }
}
