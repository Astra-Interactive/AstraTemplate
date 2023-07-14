@file:OptIn(UnsafeApi::class)

package ru.astrainteractive.astratemplate.di

import org.jetbrains.kotlin.tooling.core.UnsafeApi
import ru.astrainteractive.astralibs.async.AsyncComponent
import ru.astrainteractive.astralibs.async.KDispatchers
import ru.astrainteractive.astralibs.async.KotlinDispatchers
import ru.astrainteractive.astralibs.async.PluginScope
import ru.astrainteractive.astralibs.http.HttpClient
import ru.astrainteractive.astratemplate.api.local.di.DatabaseFactory
import ru.astrainteractive.astratemplate.api.local.di.LocalApiFactory
import ru.astrainteractive.astratemplate.api.remote.di.RickMortyApiFactory
import ru.astrainteractive.klibs.kdi.Single

object RootModule {
    val scope = Single { PluginScope as AsyncComponent }
    val dispatchers = Single { KDispatchers as KotlinDispatchers }

    val databaseModule = Single {
        DatabaseFactory("data.db").create()
    }

    val rmApiModule = Single {
        RickMortyApiFactory(HttpClient).create()
    }

    val localApi = Single {
        LocalApiFactory(LocalApiModuleImpl).create()
    }

    val helloWorldModule = Single {
        "Hello world"
    }
}
