package ru.astrainteractive.astratemplate.api.local.di

import kotlinx.coroutines.runBlocking
import ru.astrainteractive.astralibs.lifecycle.Lifecycle
import ru.astrainteractive.astralibs.orm.Database
import ru.astrainteractive.astratemplate.api.local.LocalApi
import ru.astrainteractive.astratemplate.api.local.LocalApiImpl
import ru.astrainteractive.astratemplate.api.local.di.factory.DatabaseFactory
import ru.astrainteractive.astratemplate.api.local.mapping.RatingMapper
import ru.astrainteractive.astratemplate.api.local.mapping.RatingMapperImpl
import ru.astrainteractive.astratemplate.api.local.mapping.UserMapper
import ru.astrainteractive.astratemplate.api.local.mapping.UserMapperImpl
import ru.astrainteractive.klibs.kdi.Provider
import ru.astrainteractive.klibs.kdi.Single
import ru.astrainteractive.klibs.kdi.getValue

interface ApiLocalModule {
    val database: Database
    val localApi: LocalApi
    val lifecycle: Lifecycle

    class Default(databasePath: String) : ApiLocalModule {

        override val database: Database by Single {
            DatabaseFactory(databasePath).create()
        }

        private val ratingMapper: RatingMapper by Provider {
            RatingMapperImpl
        }

        private val userMapper: UserMapper by Provider {
            UserMapperImpl
        }

        override val localApi: LocalApi by Provider {
            LocalApiImpl(
                database = database,
                ratingMapper = ratingMapper,
                userMapper = userMapper
            )
        }
        override val lifecycle: Lifecycle by lazy {
            Lifecycle.Lambda(
                onDisable = {
                    runBlocking { database.closeConnection() }
                }
            )
        }
    }
}
