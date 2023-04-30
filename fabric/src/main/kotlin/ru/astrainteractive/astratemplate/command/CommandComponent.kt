package ru.astrainteractive.astratemplate.command

import com.astrainteractive.astratemplate.api.dto.UserDTO
import com.mojang.brigadier.context.CommandContext
import kotlinx.coroutines.launch
import net.minecraft.server.command.ServerCommandSource
import ru.astrainteractive.astralibs.getValue
import ru.astrainteractive.astratemplate.di.RootModule
import java.util.UUID

class CommandComponent(
    module: RootModule
) {
    private val scope by module.scope
    private val dispatchers by module.dispatchers
    private fun command(literal: String, block: (CommandContext<ServerCommandSource>) -> Unit) =
        object : Command(literal) {
            override fun onCommand(commandContext: CommandContext<ServerCommandSource>): Int {
                block(commandContext)
                return 1
            }
        }

    val helloWorldCommand = command("helloworld") {
        println(module.helloWorldModule.value)
    }
    val rickMortyCommand = command("rickmorty") {
        val rmApi by module.rmApiModule
        scope.launch(dispatchers.IO) {
            println(rmApi.getRandomCharacter(1))
        }
    }
    val insertUserCommand = command("insertuser") {
        val localApi by module.localApi
        scope.launch(dispatchers.IO) {
            println(localApi.insertUser(UserDTO(-1, UUID.randomUUID().toString(), UUID.randomUUID().toString())))
        }
    }
    val getAllUsersCommand = command("getallusers") {
        val localApi by module.localApi
        scope.launch(dispatchers.IO) {
            println(localApi.getAllUsers())
        }
    }
}
