package ru.astrainteractive.astratemplate.gui.di

import ru.astrainteractive.astratemplate.api.local.di.ApiLocalModule
import ru.astrainteractive.astratemplate.core.di.CoreModule
import ru.astrainteractive.astratemplate.di.BukkitModule
import ru.astrainteractive.astratemplate.gui.api.ItemStackSpigotAPI
import ru.astrainteractive.astratemplate.gui.domain.GetRandomColorUseCaseImpl
import ru.astrainteractive.astratemplate.gui.domain.SetDisplayNameUseCaseImpl
import ru.astrainteractive.astratemplate.gui.router.Router
import ru.astrainteractive.astratemplate.gui.router.RouterImpl

internal class GuiModule(
    coreModule: CoreModule,
    bukkitModule: BukkitModule,
    apiLocalModule: ApiLocalModule
) {
    private val getRandomColorUseCase = GetRandomColorUseCaseImpl()
    val router: Router = RouterImpl(
        scope = coreModule.ioScope,
        dispatchers = bukkitModule.dispatchers,
        kyoriKrate = bukkitModule.kyoriKrate,
        translationKrate = coreModule.translationKrate,
        localDao = apiLocalModule.localDao,
        itemStackSpigotAPi = ItemStackSpigotAPI,
        getRandomColorUseCase = getRandomColorUseCase,
        setDisplayNameUseCase = SetDisplayNameUseCaseImpl(getRandomColorUseCase)
    )
}
