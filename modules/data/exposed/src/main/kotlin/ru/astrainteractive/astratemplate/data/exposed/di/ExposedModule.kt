package ru.astrainteractive.astratemplate.data.exposed.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.TransactionManager
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import ru.astrainteractive.astralibs.lifecycle.Lifecycle
import ru.astrainteractive.astratemplate.core.plugin.model.PluginConfiguration
import ru.astrainteractive.astratemplate.data.dao.user.api.UserRepository
import ru.astrainteractive.astratemplate.data.exposed.user.ExposedUserRepository
import ru.astrainteractive.astratemplate.data.exposed.user.table.UserRatingTable
import ru.astrainteractive.astratemplate.data.exposed.user.table.UserTable
import ru.astrainteractive.klibs.mikro.exposed.model.DatabaseConfiguration
import ru.astrainteractive.klibs.mikro.exposed.util.connectAsFlow

class ExposedModule(
    configFlow: Flow<PluginConfiguration>,
    ioScope: CoroutineScope
) {
    private val databaseFlow = configFlow
        .map(::databaseConfiguration)
        .distinctUntilChanged()
        .flatMapLatest(::connectDatabase)
        .onEach { database ->
            TransactionManager.manager.defaultIsolationLevel = java.sql.Connection.TRANSACTION_SERIALIZABLE
            transaction(database) {
                SchemaUtils.create(UserRatingTable, UserTable)
            }
        }
        .shareIn(
            scope = ioScope,
            started = SharingStarted.Eagerly,
            replay = 1
        )

    val userRepository: UserRepository = ExposedUserRepository(
        databaseFlow = databaseFlow,
    )

    val lifecycle: Lifecycle by lazy {
        Lifecycle.Lambda()
    }

    private fun databaseConfiguration(config: PluginConfiguration): DatabaseConfiguration {
        return config.database
    }

    private fun connectDatabase(configuration: DatabaseConfiguration): Flow<Database> {
        return configuration.connectAsFlow()
    }
}
