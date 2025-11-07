package ru.astrainteractive.astratemplate.api.local.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Slf4jSqlDebugLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import ru.astrainteractive.astralibs.lifecycle.Lifecycle
import ru.astrainteractive.astratemplate.api.local.dao.LocalDao
import ru.astrainteractive.astratemplate.api.local.dao.LocalDaoImpl
import ru.astrainteractive.astratemplate.api.local.entity.UserRatingTable
import ru.astrainteractive.astratemplate.api.local.entity.UserTable
import ru.astrainteractive.astratemplate.core.plugin.PluginConfiguration
import ru.astrainteractive.klibs.mikro.core.coroutines.mapCached
import ru.astrainteractive.klibs.mikro.exposed.model.DatabaseConfiguration
import ru.astrainteractive.klibs.mikro.exposed.util.connect
import java.io.File

class ApiLocalModule(
    dataFolder: File,
    configFlow: Flow<PluginConfiguration>,
    scope: CoroutineScope
) {
    private val databaseFlow = configFlow
        .map { it.database }
        .distinctUntilChanged()
        .mapCached(scope) { config, previous: Database? ->
            previous?.connector?.invoke()?.close()
            previous?.run(TransactionManager::closeAndUnregister)
            val database = dataFolder.resolve("users_db").absolutePath
                .let(DatabaseConfiguration::H2)
                .connect()
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

    val localDao: LocalDao = LocalDaoImpl(
        databaseFlow = databaseFlow,
    )

    val lifecycle: Lifecycle by lazy {
        Lifecycle.Lambda(
            onDisable = {
                GlobalScope.launch {
                    databaseFlow.firstOrNull()?.let { database ->
                        database.connector.invoke().close()
                        database.run(TransactionManager::closeAndUnregister)
                    }
                }
            }
        )
    }
}
