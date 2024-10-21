package ru.astrainteractive.astratemplate.gui.di

import ru.astrainteractive.astratemplate.api.local.di.ApiLocalModule
import ru.astrainteractive.astratemplate.core.di.CoreModule
import ru.astrainteractive.astratemplate.di.BukkitModule
import ru.astrainteractive.astratemplate.gui.router.DefaultRouter
import ru.astrainteractive.astratemplate.gui.router.Router

interface GuiModule {
    val router: Router

    class Default(
        coreModule: CoreModule,
        bukkitModule: BukkitModule,
        apiLocalModule: ApiLocalModule
    ) : GuiModule {
        override val router: Router = DefaultRouter(
            scope = coreModule.pluginScope,
            dispatchers = bukkitModule.dispatchers,
            dependencies = SampleGuiDependencies.Default(
                coreModule = coreModule,
                bukkitModule = bukkitModule,
                apiLocalModule = apiLocalModule
            )
        )
    }
}
