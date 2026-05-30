package ru.astrainteractive.astratemplate.feature.gui.di

import ru.astrainteractive.astratemplate.api.local.di.ApiLocalModule
import ru.astrainteractive.astratemplate.core.di.CoreModule
import ru.astrainteractive.astratemplate.feature.gui.api.ItemStackSpigotAPI
import ru.astrainteractive.astratemplate.feature.gui.domain.GetRandomColorUseCaseImpl
import ru.astrainteractive.astratemplate.feature.gui.domain.SetDisplayNameUseCaseImpl
import ru.astrainteractive.astratemplate.feature.gui.router.Router
import ru.astrainteractive.astratemplate.feature.gui.router.RouterImpl

class BukkitGuiModule(
    coreModule: CoreModule,
    apiLocalModule: ApiLocalModule
) : GuiModule {
    private val getRandomColorUseCase = GetRandomColorUseCaseImpl()
    override val router: Router = RouterImpl(
        ioScope = coreModule.ioScope,
        dispatchers = coreModule.dispatchers,
        kyoriKrate = coreModule.kyoriKrate,
        translationKrate = coreModule.translationKrate,
        localDao = apiLocalModule.localDao,
        itemStackSpigotAPi = ItemStackSpigotAPI,
        getRandomColorUseCase = getRandomColorUseCase,
        setDisplayNameUseCase = SetDisplayNameUseCaseImpl(getRandomColorUseCase)
    )
}
