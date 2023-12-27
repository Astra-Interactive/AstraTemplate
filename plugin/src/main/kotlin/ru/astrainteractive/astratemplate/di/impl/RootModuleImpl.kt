package ru.astrainteractive.astratemplate.di.impl

import ru.astrainteractive.astratemplate.api.local.di.ApiLocalModule
import ru.astrainteractive.astratemplate.api.remote.di.ApiRemoteModule
import ru.astrainteractive.astratemplate.di.BukkitModule
import ru.astrainteractive.astratemplate.di.RootModule
import ru.astrainteractive.astratemplate.shared.di.CoreModule
import ru.astrainteractive.klibs.kdi.Single
import ru.astrainteractive.klibs.kdi.getValue
import java.io.File

internal class RootModuleImpl : RootModule {

    override val bukkitModule: BukkitModule by Single {
        BukkitModuleImpl(this)
    }

    override val apiLocalModule: ApiLocalModule by Single {
        ApiLocalModule.Default("${bukkitModule.plugin.value.dataFolder}${File.separator}data.db")
    }

    override val apiRemoteModule: ApiRemoteModule by Single {
        ApiRemoteModule.Default()
    }

    override val coreModule: CoreModule by Single {
        CoreModule.Default(bukkitModule.plugin.value.dataFolder)
    }
}
