package ru.astrainteractive.astratemplate.command

import com.astrainteractive.astratemplate.api.dto.UserDTO
import com.mojang.brigadier.context.CommandContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.minecraft.server.command.ServerCommandSource
import ru.astrainteractive.astralibs.async.PluginScope
import ru.astrainteractive.astralibs.di.getValue
import ru.astrainteractive.astratemplate.ServiceLocator
import java.util.UUID

fun command(literal: String, block: (CommandContext<ServerCommandSource>) -> Unit) = object : Command(literal) {
    override fun onCommand(commandContext: CommandContext<ServerCommandSource>): Int {
        block(commandContext)
        return 1
    }
}

val HelloWorldCommand = command("helloworld") {
    println(ServiceLocator.helloWorldModule.value)
}
val RickMortyCommand = command("rickmorty") {
    val rmApi by ServiceLocator.rmApiModule
    PluginScope.launch(Dispatchers.IO) {
        println(rmApi.getRandomCharacter(1))
    }
}
val InsertUserCommand = command("insertuser") {
    val localApi by ServiceLocator.localApi
    PluginScope.launch(Dispatchers.IO) {
        println(localApi.insertUser(UserDTO(-1, UUID.randomUUID().toString(), UUID.randomUUID().toString())))
    }
}
val GetAllUsersCommand = command("getallusers") {
    val localApi by ServiceLocator.localApi
    PluginScope.launch(Dispatchers.IO) {
        println(localApi.getAllUsers())
    }
}
