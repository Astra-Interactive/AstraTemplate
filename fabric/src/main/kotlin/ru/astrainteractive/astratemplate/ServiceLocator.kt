package ru.astrainteractive.astratemplate

import com.astrainteractive.astratemplate.api.local.DatabaseFactory
import com.astrainteractive.astratemplate.api.local.LocalApiFactory
import com.astrainteractive.astratemplate.api.remote.RickMortyApiFactory
import ru.astrainteractive.astralibs.di.getValue
import ru.astrainteractive.astralibs.di.module

object ServiceLocator {
    val databaseModule = module {
        DatabaseFactory("data.db").value
    }

    val rmApiModule = module {
        RickMortyApiFactory().value
    }
    val localApi = module {
        val database by databaseModule
        LocalApiFactory(database).value
    }
    val helloWorldModule = module {
        "Hello world"
    }
}
