package ru.astrainteractive.astratemplate.command.fake

import net.kyori.adventure.text.Component
import ru.astrainteractive.astralibs.command.api.brigadier.sender.ConsoleKCommandSender
import ru.astrainteractive.astralibs.localization.component.LocalizableComponent
import ru.astrainteractive.astralibs.server.permission.Permission
import java.util.Locale

internal class RecordingConsoleKCommandSender : ConsoleKCommandSender {
    val messages = mutableListOf<LocalizableComponent>()

    override val locale: Locale = Locale.ROOT

    override fun sendMessage(component: Component) {
        error("Commands must send localizable messages, got $component")
    }

    override fun sendMessage(message: LocalizableComponent) {
        messages.add(message)
    }

    override fun hasPermission(permission: Permission): Boolean = false

    override fun maxPermissionSize(permission: Permission): Int? = null

    override fun minPermissionSize(permission: Permission): Int? = null

    override fun permissionSizes(permission: Permission): List<Int> = emptyList()

    override fun dispatchCommand(command: String) = Unit
}
