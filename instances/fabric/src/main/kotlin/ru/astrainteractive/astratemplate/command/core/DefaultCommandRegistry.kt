package ru.astrainteractive.astratemplate.command.core

import com.mojang.brigadier.CommandDispatcher
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.command.CommandRegistryAccess
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource

object DefaultCommandRegistry : CommandRegistry {

    private class CommandRegistrationCallbackImpl(
        private val literal: String,
        private val command: Command
    ) : CommandRegistrationCallback {
        override fun register(
            dispatcher: CommandDispatcher<ServerCommandSource>?,
            registryAccess: CommandRegistryAccess?,
            environment: CommandManager.RegistrationEnvironment?
        ) {
            dispatcher?.register(CommandManager.literal(literal).executes(command::onCommand))
        }
    }

    override fun register(command: Command) {
        val callback = CommandRegistrationCallbackImpl(literal = command.alias, command = command)
        CommandRegistrationCallback.EVENT.register(callback)
    }
}
