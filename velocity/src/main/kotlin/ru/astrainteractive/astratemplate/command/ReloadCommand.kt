package ru.astrainteractive.astratemplate.command

import com.velocitypowered.api.command.SimpleCommand
import ru.astrainteractive.astratemplate.AstraTemplate
import ru.astrainteractive.astratemplate.shared.core.Permissions


class ReloadCommand(private val plugin: AstraTemplate) : SimpleCommand {

    override fun execute(invocation: SimpleCommand.Invocation) {
        plugin.reload()
    }

    override fun hasPermission(invocation: SimpleCommand.Invocation): Boolean {
        return invocation.source().hasPermission(Permissions.Reload.value)
    }
}
