package ru.astrainteractive.astratemplate.gui.di

import ru.astrainteractive.astratemplate.api.local.di.ApiLocalModule
import ru.astrainteractive.astratemplate.core.di.CoreModule
import ru.astrainteractive.astratemplate.di.BukkitModule
import ru.astrainteractive.astratemplate.gui.router.Router
import ru.astrainteractive.astratemplate.gui.router.RouterImpl

internal interface GuiModule {
    val router: Router

    class Default(
        coreModule: CoreModule,
        bukkitModule: BukkitModule,
        apiLocalModule: ApiLocalModule
    ) : GuiModule {
        override val router: Router = RouterImpl(
            scope = coreModule.ioScope,
            dispatchers = bukkitModule.dispatchers,
            dependencies = SampleGuiDependencies.Default(
                coreModule = coreModule,
                bukkitModule = bukkitModule,
                apiLocalModule = apiLocalModule
            )
        )
    }
}
