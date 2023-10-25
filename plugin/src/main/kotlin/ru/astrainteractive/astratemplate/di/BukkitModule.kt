package ru.astrainteractive.astratemplate.di

import CommandManager
import ru.astrainteractive.astralibs.async.BukkitDispatchers
import ru.astrainteractive.astralibs.event.EventListener
import ru.astrainteractive.astralibs.menu.event.DefaultInventoryClickEvent
import ru.astrainteractive.astralibs.permission.PermissionManager
import ru.astrainteractive.astralibs.serialization.KyoriComponentSerializer
import ru.astrainteractive.astratemplate.AstraTemplate
import ru.astrainteractive.astratemplate.event.EventManager
import ru.astrainteractive.klibs.kdi.Dependency
import ru.astrainteractive.klibs.kdi.Lateinit
import ru.astrainteractive.klibs.kdi.Single

interface BukkitModule {
    val plugin: Lateinit<AstraTemplate>
    val bukkitDispatchers: Dependency<BukkitDispatchers>
    val eventListener: Single<EventListener>
    val inventoryClickEvent: Single<DefaultInventoryClickEvent>
    val permissionManager: Single<PermissionManager>
    val kyoriComponentSerializer: Single<KyoriComponentSerializer>
    val eventManager: Single<EventManager>
    val commandManager: Single<CommandManager>
}
