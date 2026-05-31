package ru.astrainteractive.astratemplate.api.local.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.TransactionManager
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import ru.astrainteractive.astralibs.lifecycle.Lifecycle
import ru.astrainteractive.astratemplate.api.local.dao.LocalDao
import ru.astrainteractive.astratemplate.api.local.dao.LocalDaoImpl
import ru.astrainteractive.astratemplate.api.local.entity.UserRatingTable
import ru.astrainteractive.astratemplate.api.local.entity.UserTable
import ru.astrainteractive.astratemplate.core.plugin.PluginConfiguration
import ru.astrainteractive.klibs.mikro.exposed.util.connectAsFlow

class ApiLocalModule(
    configFlow: Flow<PluginConfiguration>,
    ioScope: CoroutineScope
) {
    private val databaseFlow = configFlow
        .map { pluginConfiguration -> pluginConfiguration.database }
        .distinctUntilChanged()
        .flatMapLatest { configuration -> configuration.connectAsFlow() }
        .onEach { database ->
            TransactionManager.manager.defaultIsolationLevel = java.sql.Connection.TRANSACTION_SERIALIZABLE
            transaction(database) {
                SchemaUtils.create(UserRatingTable, UserTable)
            }
        }
        .shareIn(ioScope, SharingStarted.Eagerly, 1)

    val localDao: LocalDao = LocalDaoImpl(
        databaseFlow = databaseFlow,
    )

    val lifecycle: Lifecycle by lazy {
        Lifecycle.Lambda()
    }
}
