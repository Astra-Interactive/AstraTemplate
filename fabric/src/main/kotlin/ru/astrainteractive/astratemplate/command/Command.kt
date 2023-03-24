package ru.astrainteractive.astratemplate.command

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.command.CommandRegistryAccess
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource

abstract class Command(val literal: String) : CommandRegistrationCallback {

    abstract fun onCommand(commandContext: CommandContext<ServerCommandSource>): Int

    override fun register(
        dispatcher: CommandDispatcher<ServerCommandSource>?,
        registryAccess: CommandRegistryAccess?,
        environment: CommandManager.RegistrationEnvironment?
    ) {
        dispatcher?.register(CommandManager.literal(literal).executes(::onCommand))
    }
}
