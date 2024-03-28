@file:Suppress("UnusedPrivateMember")

package ru.astrainteractive.astratemplate

import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import org.apache.logging.log4j.LogManager
import ru.astrainteractive.astratemplate.di.RootModule
import javax.annotation.ParametersAreNonnullByDefault

@Mod(BuildKonfig.id)
@ParametersAreNonnullByDefault
class ForgeEntryPoint {
    companion object {
        val rootModule by lazy { RootModule.Default() }
    }

    private val logger = LogManager.getLogger()

    private fun setup(event: FMLCommonSetupEvent) {
        rootModule.coreModule.logger.value.info("ForgeEntryPoint", "setup")
        CommandLoader.commonSetup(event)
    }

    init {
        FMLJavaModLoadingContext.get().modEventBus.addListener(::setup)
    }
}
