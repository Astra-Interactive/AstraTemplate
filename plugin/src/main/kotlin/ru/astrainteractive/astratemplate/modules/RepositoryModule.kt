package ru.astrainteractive.astratemplate.modules

import com.astrainteractive.astratemplate.domain.Repository
import ru.astrainteractive.astralibs.di.IModule
import ru.astrainteractive.astratemplate.api.ItemStackSpigotAPI
import ru.astrainteractive.astratemplate.gui.SampleGUIViewModel
import kotlin.reflect.KProperty

object RepositoryModule : IModule<Repository>() {
    override fun initializer(): Repository {
        return Repository(SQLDatabaseModule.value, RestApiModule.value)
    }
}

