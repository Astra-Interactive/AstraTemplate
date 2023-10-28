package ru.astrainteractive.astratemplate.di.impl

import CommandManager
import ru.astrainteractive.astralibs.async.DefaultBukkitDispatchers
import ru.astrainteractive.astralibs.event.EventListener
import ru.astrainteractive.astralibs.menu.event.DefaultInventoryClickEvent
import ru.astrainteractive.astralibs.permission.BukkitPermissionManager
import ru.astrainteractive.astralibs.permission.PermissionManager
import ru.astrainteractive.astralibs.serialization.KyoriComponentSerializer
import ru.astrainteractive.astralibs.string.BukkitTranslationContext
import ru.astrainteractive.astratemplate.AstraTemplate
import ru.astrainteractive.astratemplate.command.di.CommandManagerDependencies
import ru.astrainteractive.astratemplate.di.BukkitModule
import ru.astrainteractive.astratemplate.di.RootModule
import ru.astrainteractive.astratemplate.event.EventManager
import ru.astrainteractive.astratemplate.event.di.EventDependencies
import ru.astrainteractive.klibs.kdi.Lateinit
import ru.astrainteractive.klibs.kdi.Single

class BukkitModuleImpl(rootModule: RootModule) : BukkitModule {
    override val plugin = Lateinit<AstraTemplate>()

    override val bukkitDispatchers = Single {
        DefaultBukkitDispatchers(plugin.value)
    }

    override val eventListener: Single<EventListener> = Single {
        object : EventListener {}
    }
    override val inventoryClickEvent: Single<DefaultInventoryClickEvent> = Single {
        DefaultInventoryClickEvent()
    }

    override val permissionManager: Single<PermissionManager> = Single {
        BukkitPermissionManager()
    }

    override val kyoriComponentSerializer: Single<KyoriComponentSerializer> = Single {
        KyoriComponentSerializer.Legacy
    }

    override val eventManager: Single<EventManager> = Single {
        val dependencies = EventDependencies.Default(rootModule)
        EventManager(dependencies)
    }

    override val commandManager: Single<CommandManager> = Single {
        val dependencies = CommandManagerDependencies.Default(rootModule)
        CommandManager(dependencies)
    }
    override val bukkitTranslationContext: Single<BukkitTranslationContext> = Single {
        BukkitTranslationContext.Default { kyoriComponentSerializer.value }
    }
}
