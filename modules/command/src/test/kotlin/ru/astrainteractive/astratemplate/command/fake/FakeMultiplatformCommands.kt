package ru.astrainteractive.astratemplate.command.fake

import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import ru.astrainteractive.astralibs.command.api.brigadier.command.MultiplatformCommands
import ru.astrainteractive.astralibs.command.api.brigadier.sender.KCommandSender

internal class FakeMultiplatformCommands(
    private val sender: KCommandSender?
) : MultiplatformCommands {
    override fun literal(literal: String): LiteralArgumentBuilder<Any> {
        return LiteralArgumentBuilder.literal(literal)
    }

    override fun <T : Any> argument(
        name: String,
        argumentType: ArgumentType<T>
    ): RequiredArgumentBuilder<Any, T> {
        return RequiredArgumentBuilder.argument(name, argumentType)
    }

    override fun getSender(context: CommandContext<*>): KCommandSender {
        return sender ?: error("Could not wrap sender")
    }
}
