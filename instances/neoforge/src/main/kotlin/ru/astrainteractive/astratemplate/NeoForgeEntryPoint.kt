package ru.astrainteractive.astratemplate

import net.neoforged.fml.common.Mod
import ru.astrainteractive.astralibs.lifecycle.ForgeLifecycleServer
import ru.astrainteractive.klibs.mikro.core.logging.JUtiltLogger
import ru.astrainteractive.klibs.mikro.core.logging.Logger
import javax.annotation.ParametersAreNonnullByDefault
import ru.astrainteractive.aspekt.BuildKonfig
import ru.astrainteractive.astratemplate.di.RootModule

@Mod(BuildKonfig.id)
@ParametersAreNonnullByDefault
class NeoForgeEntryPoint :
    Logger by JUtiltLogger("${BuildKonfig.id}-NeoForgeEntryPoint"),
    ForgeLifecycleServer() {
    private val rootModule = RootModule()

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
