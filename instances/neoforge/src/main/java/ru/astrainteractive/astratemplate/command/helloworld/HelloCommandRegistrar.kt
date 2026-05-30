package ru.astrainteractive.astratemplate.command.helloworld

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import net.minecraft.commands.CommandSourceStack
import net.minecraft.network.chat.Component
import ru.astrainteractive.astralibs.command.util.command
import ru.astrainteractive.astralibs.command.util.runs

class HelloCommandRegistrar {
    fun createNode(): LiteralArgumentBuilder<CommandSourceStack> {
        return command("helloworld") {
            runs { ctx ->
                val component = Component.literal("Hello world!")
                ctx.source.source.sendSystemMessage(component)
            }
        }
    }
}
