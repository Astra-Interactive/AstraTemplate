package ru.astrainteractive.astratemplate.command.di

import ru.astrainteractive.astralibs.lifecycle.Lifecycle
import ru.astrainteractive.astratemplate.command.additem.AddItemCommandFactory
import ru.astrainteractive.astratemplate.command.common.CommonCommandsFactory
import ru.astrainteractive.astratemplate.command.damage.DamageCommandFactory
import ru.astrainteractive.astratemplate.command.gui.GuiCommandFactory
import ru.astrainteractive.astratemplate.command.reload.ReloadCommandFactory
import ru.astrainteractive.astratemplate.command.rickmorty.RandomRickAndMortyCommandFactory
import ru.astrainteractive.astratemplate.di.RootModule

interface CommandModule {
    val lifecycle: Lifecycle

    class Default(rootModule: RootModule) : CommandModule {
        override val lifecycle: Lifecycle by lazy {
            Lifecycle.Lambda(
                onEnable = {
                    val dependencies = CommandManagerDependencies.Default(rootModule)
                    AddItemCommandFactory(dependencies).create()
                    CommonCommandsFactory(dependencies).create()
                    DamageCommandFactory(dependencies).create()
                    GuiCommandFactory(dependencies).create()
                    ReloadCommandFactory(dependencies).create()
                    RandomRickAndMortyCommandFactory(dependencies).create()
                }
            )
        }
    }
}
