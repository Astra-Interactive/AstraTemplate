package com.astrainteractive.astratemplate.api.local

import com.astrainteractive.astratemplate.api.local.entities.UserRatingTable
import com.astrainteractive.astratemplate.api.local.entities.UserTable
import kotlinx.coroutines.runBlocking
import ru.astrainteractive.astralibs.di.Factory
import ru.astrainteractive.astralibs.orm.DBConnection
import ru.astrainteractive.astralibs.orm.DBSyntax
import ru.astrainteractive.astralibs.orm.Database
import ru.astrainteractive.astralibs.orm.DefaultDatabase

class DatabaseFactory(
    private val path: String
) : Factory<Database>() {
    override fun initializer(): Database = runBlocking {
        val connection = DBConnection.SQLite(path)
        DefaultDatabase(connection, DBSyntax.SQLite).also {
            it.openConnection()
            UserTable.create(it)
            UserRatingTable.create(it)
        }
    }
}
