package ru.astrainteractive.astratemplate.api.local.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Slf4jSqlDebugLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import ru.astrainteractive.astralibs.exposed.factory.DatabaseFactory
import ru.astrainteractive.astralibs.lifecycle.Lifecycle
import ru.astrainteractive.astralibs.util.FlowExt.mapCached
import ru.astrainteractive.astratemplate.api.local.dao.LocalDao
import ru.astrainteractive.astratemplate.api.local.dao.LocalDaoImpl
import ru.astrainteractive.astratemplate.api.local.entity.UserRatingTable
import ru.astrainteractive.astratemplate.api.local.entity.UserTable
import ru.astrainteractive.astratemplate.core.PluginConfiguration
import java.io.File

interface ApiLocalModule {
    val localDao: LocalDao
    val lifecycle: Lifecycle

    class Default(
        dataFolder: File,
        configFlow: Flow<PluginConfiguration>,
        scope: CoroutineScope
    ) : ApiLocalModule {

        private val databaseFlow = configFlow
            .map { it.database }
            .distinctUntilChanged()
            .mapCached(scope) { config, previous: Database? ->
                previous?.connector?.invoke()?.close()
                previous?.run(TransactionManager::closeAndUnregister)
                val database = DatabaseFactory(dataFolder).create(config)
                TransactionManager.manager.defaultIsolationLevel = java.sql.Connection.TRANSACTION_SERIALIZABLE
                transaction(database) {
                    addLogger(Slf4jSqlDebugLogger)
                    SchemaUtils.create(
                        UserRatingTable,
                        UserTable
                    )
                }
                database
            }

        override val localDao: LocalDao = LocalDaoImpl(
            databaseFlow = databaseFlow,
        )

        override val lifecycle: Lifecycle by lazy {
            Lifecycle.Lambda(
                onDisable = {
                    runBlocking {
                        databaseFlow.firstOrNull()?.let { database ->
                            database.connector.invoke().close()
                            database.run(TransactionManager::closeAndUnregister)
                        }
                    }
                }
            )
        }
    }
}
