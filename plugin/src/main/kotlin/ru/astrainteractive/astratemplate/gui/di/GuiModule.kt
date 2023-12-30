package ru.astrainteractive.astratemplate.gui.di

import ru.astrainteractive.astratemplate.api.local.di.ApiLocalModule
import ru.astrainteractive.astratemplate.core.di.CoreModule
import ru.astrainteractive.astratemplate.di.BukkitModule
import ru.astrainteractive.astratemplate.gui.router.DefaultRouter
import ru.astrainteractive.astratemplate.gui.router.Router
import ru.astrainteractive.klibs.kdi.Dependency
import ru.astrainteractive.klibs.kdi.Single
import ru.astrainteractive.klibs.kdi.getValue

interface GuiModule {
    val router: Dependency<Router>

    class Default(
        coreModule: CoreModule,
        bukkitModule: BukkitModule,
        apiLocalModule: ApiLocalModule
    ) : GuiModule {
        override val router: Dependency<Router> = Single {
            DefaultRouter(
                scope = coreModule.pluginScope.value,
                dispatchers = bukkitModule.bukkitDispatchers.value,
                dependencies = SampleGuiDependencies.Default(
                    coreModule = coreModule,
                    bukkitModule = bukkitModule,
                    apiLocalModule = apiLocalModule
                )
            )
        }
    }
}
