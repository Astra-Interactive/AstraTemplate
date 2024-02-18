package ru.astrainteractive.astratemplate.command.di

import ru.astrainteractive.astralibs.lifecycle.Lifecycle
import ru.astrainteractive.astratemplate.command.additem.AddItemCommandRegistry
import ru.astrainteractive.astratemplate.command.common.CommonCommandsRegistry
import ru.astrainteractive.astratemplate.command.damage.DamageCommandRegistry
import ru.astrainteractive.astratemplate.command.gui.GuiCommandRegistry
import ru.astrainteractive.astratemplate.command.reload.ReloadCommandRegistry
import ru.astrainteractive.astratemplate.command.rickmorty.RandomRickAndMortyCommandRegistry
import ru.astrainteractive.astratemplate.di.RootModule

interface CommandModule {
    val lifecycle: Lifecycle

    class Default(rootModule: RootModule) : CommandModule {
        override val lifecycle: Lifecycle by lazy {
            Lifecycle.Lambda(
                onEnable = {
                    val dependencies = CommandManagerDependencies.Default(rootModule)
                    AddItemCommandRegistry(dependencies).register()
                    CommonCommandsRegistry(dependencies).register()
                    DamageCommandRegistry(dependencies).register()
                    GuiCommandRegistry(dependencies).register()
                    ReloadCommandRegistry(dependencies).register()
                    RandomRickAndMortyCommandRegistry(dependencies).register()
                }
            )
        }
    }
}
