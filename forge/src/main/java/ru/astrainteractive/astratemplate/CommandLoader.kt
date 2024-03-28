package ru.astrainteractive.astratemplate

import net.minecraftforge.event.RegisterCommandsEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import ru.astrainteractive.astratemplate.command.HelloWorldCommand
import ru.astrainteractive.astratemplate.command.core.DefaultCommandRegistry

@Mod.EventBusSubscriber(modid = BuildKonfig.id)
object CommandLoader {

    @Suppress("UnusedPrivateMember")
    fun commonSetup(event: FMLCommonSetupEvent) = Unit

    @SubscribeEvent
    @JvmStatic
    fun registerCommands(event: RegisterCommandsEvent) {
        ForgeEntryPoint.rootModule.coreModule.logger.value.info("ForgeEntryPoint", "registerCommands")
        val registry = DefaultCommandRegistry(event.dispatcher)
        registry.register(HelloWorldCommand())
    }
}
