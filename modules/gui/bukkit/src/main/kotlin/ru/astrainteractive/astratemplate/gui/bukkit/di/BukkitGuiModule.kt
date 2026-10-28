package ru.astrainteractive.astratemplate.gui.bukkit.di

import ru.astrainteractive.astratemplate.core.di.CoreModule
import ru.astrainteractive.astratemplate.data.dao.user.api.UserRepository
import ru.astrainteractive.astratemplate.gui.api.di.GuiModule
import ru.astrainteractive.astratemplate.gui.api.router.api.Router
import ru.astrainteractive.astratemplate.gui.bukkit.context.SlotContext
import ru.astrainteractive.astratemplate.gui.bukkit.router.BukkitRouter
import ru.astrainteractive.astratemplate.gui.bukkit.sample.component.DefaultSampleGUIComponent
import ru.astrainteractive.astratemplate.gui.bukkit.sample.gui.SampleGUI
import kotlin.random.Random

class BukkitGuiModule(
    coreModule: CoreModule,
    userRepository: UserRepository
) : GuiModule {
    override val router: Router = BukkitRouter(
        ioScope = coreModule.ioScope,
        dispatchers = coreModule.dispatchers,
        createSlotContext = { locale -> SlotContext(coreModule.translationKrate, locale) },
        userRepository = userRepository,
        random = Random.Default,
        sampleComponentFactory = ::DefaultSampleGUIComponent,
        sampleGuiFactory = { slotContext, sampleComponent ->
            SampleGUI(
                slotContext = slotContext,
                dispatchers = coreModule.dispatchers,
                sampleComponent = sampleComponent
            )
        }
    )
}
