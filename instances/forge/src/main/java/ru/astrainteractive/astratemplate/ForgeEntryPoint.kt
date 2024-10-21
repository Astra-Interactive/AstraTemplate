@file:Suppress("UnusedPrivateMember")

package ru.astrainteractive.astratemplate

import net.minecraftforge.event.RegisterCommandsEvent
import net.minecraftforge.event.server.ServerStartedEvent
import net.minecraftforge.event.server.ServerStoppingEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import ru.astrainteractive.astralibs.logging.JUtiltLogger
import ru.astrainteractive.astralibs.logging.Logger
import ru.astrainteractive.astratemplate.command.CommandLoader
import ru.astrainteractive.astratemplate.di.RootModule
import ru.astrainteractive.astratemplate.event.core.ForgeEventBusListener
import javax.annotation.ParametersAreNonnullByDefault

@Mod(BuildKonfig.id)
@ParametersAreNonnullByDefault
class ForgeEntryPoint :
    ForgeEventBusListener,
    Logger by JUtiltLogger("ForgeEntryPoint") {
    private val rootModule: RootModule by lazy { RootModule.Default() }

    @SubscribeEvent
    fun onEnable(e: ServerStartedEvent) {
        info { "#onEnable" }
        rootModule.lifecycle.onEnable()
    }

    @SubscribeEvent
    fun onDisable(e: ServerStoppingEvent) {
        info { "#onDisable" }
        rootModule.lifecycle.onDisable()
        unregister()
    }

    @SubscribeEvent
    fun onCommandRegister(e: RegisterCommandsEvent) {
        info { "#onCommandRegister" }
        val commandLoader = CommandLoader()
        commandLoader.registerCommands(e)
    }

    fun onReload() {
        rootModule.lifecycle.onReload()
    }

    init {
        register()
    }
}
