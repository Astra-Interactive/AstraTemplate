package ru.astrainteractive.astratemplate

import net.minecraftforge.fml.common.Mod
import ru.astrainteractive.astralibs.lifecycle.ForgeLifecycleServer
import ru.astrainteractive.astratemplate.di.RootModule
import ru.astrainteractive.klibs.mikro.core.logging.JUtiltLogger
import ru.astrainteractive.klibs.mikro.core.logging.Logger
import javax.annotation.ParametersAreNonnullByDefault

@Mod(BuildKonfig.id)
@ParametersAreNonnullByDefault
class ForgeEntryPoint :
    Logger by JUtiltLogger("${BuildKonfig.id}-ForgeEntryPoint"),
    ForgeLifecycleServer() {
    private val rootModule = RootModule(this)

    override fun onEnable() {
        info { "#onEnable" }
        rootModule.lifecycle.onEnable()
    }

    override fun onDisable() {
        info { "#onDisable" }
        rootModule.lifecycle.onDisable()
    }

    override fun onReload() {
        info { "#onReaload" }
        rootModule.lifecycle.onReload()
    }
}
