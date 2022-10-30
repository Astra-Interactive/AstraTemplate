package com.astrainteractive.astratemplate.modules

import com.astrainteractive.astratemplate.domain.Repository
import ru.astrainteractive.astralibs.di.IModule

object RepositoryModule : IModule<Repository>() {
    override fun initializer(): Repository {
        return Repository(SQLDatabaseModule.value, RestApiModule.value)
    }
}