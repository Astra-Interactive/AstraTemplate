package ru.astrainteractive.astratemplate.di

import ru.astrainteractive.astralibs.lifecycle.Lifecycle
import ru.astrainteractive.astratemplate.api.local.di.ApiLocalModule
import ru.astrainteractive.astratemplate.api.remote.di.ApiRemoteModule
import ru.astrainteractive.astratemplate.core.di.CoreModule
import ru.astrainteractive.astratemplate.event.di.EventModule
import java.io.File

interface RootModule {
    val lifecycle: Lifecycle

    val coreModule: CoreModule

    val apiLocalModule: ApiLocalModule

    val apiRemoteModule: ApiRemoteModule

    val eventsModule: EventModule

    class Default : RootModule {

        override val coreModule: CoreModule by lazy {
            CoreModule.Default(
                dataFolder = File("./")
            )
        }

        override val apiLocalModule: ApiLocalModule by lazy {
            ApiLocalModule.Default(
                dataFolder = File("./"),
                configFlow = coreModule.configurationModule.cachedStateFlow,
                scope = coreModule.pluginScope
            )
        }

        override val apiRemoteModule: ApiRemoteModule by lazy {
            ApiRemoteModule.Default()
        }

        override val eventsModule: EventModule by lazy {
            EventModule.Default()
        }

        private val lifecycles: List<Lifecycle>
            get() = listOf(eventsModule.lifecycle)

        override val lifecycle: Lifecycle by lazy {
            Lifecycle.Lambda(
                onEnable = {
                    lifecycles.forEach(Lifecycle::onEnable)
                },
                onDisable = {
                    lifecycles.forEach(Lifecycle::onDisable)
                },
                onReload = {
                    lifecycles.forEach(Lifecycle::onReload)
                }
            )
        }
    }
}
