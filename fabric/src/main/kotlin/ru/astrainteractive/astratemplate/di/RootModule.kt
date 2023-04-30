@file:OptIn(UnsafeApi::class)

package ru.astrainteractive.astratemplate.di

import com.astrainteractive.astratemplate.api.local.di.DatabaseFactory
import com.astrainteractive.astratemplate.api.local.di.LocalApiFactory
import com.astrainteractive.astratemplate.api.remote.di.RickMortyApiFactory
import org.jetbrains.kotlin.tooling.core.UnsafeApi
import ru.astrainteractive.astralibs.Single
import ru.astrainteractive.astralibs.async.AsyncComponent
import ru.astrainteractive.astralibs.async.KDispatchers
import ru.astrainteractive.astralibs.async.KotlinDispatchers
import ru.astrainteractive.astralibs.async.PluginScope
import ru.astrainteractive.astralibs.http.HttpClient

object RootModule {
    val scope = Single { PluginScope as AsyncComponent }
    val dispatchers = Single { KDispatchers as KotlinDispatchers }

    val databaseModule = Single {
        DatabaseFactory("data.db").build()
    }

    val rmApiModule = Single {
        RickMortyApiFactory(HttpClient).build()
    }

    val localApi = Single {
        LocalApiFactory(LocalApiModuleImpl).build()
    }

    val helloWorldModule = Single {
        "Hello world"
    }
}
