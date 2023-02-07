package ru.astrainteractive.astratemplate

import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import org.apache.logging.log4j.LogManager

@Mod("astratemplate")
class ForgeEntryPoint {
    private val logger = LogManager.getLogger()

    private fun setup(event: FMLCommonSetupEvent) {
        logger.info("ForgeEntryPoint.setup")
    }

    init {
        FMLJavaModLoadingContext.get().modEventBus.addListener(::setup)
    }
}
