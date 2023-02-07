package ru.astrainteractive.astratemplate.command

import com.astrainteractive.astratemplate.domain.local.dto.UserDTO
import com.mojang.brigadier.context.CommandContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.minecraft.server.command.ServerCommandSource
import ru.astrainteractive.astralibs.async.PluginScope
import ru.astrainteractive.astralibs.di.getValue
import ru.astrainteractive.astratemplate.HelloWorldModule
import ru.astrainteractive.astratemplate.repositoryModule
import java.util.UUID

fun command(literal: String, block: (CommandContext<ServerCommandSource>) -> Unit) = object : ICommand(literal) {
    override fun onCommand(commandContext: CommandContext<ServerCommandSource>): Int {
        block(commandContext)
        return 1
    }
}

val HelloWorldCommand = command("helloworld") {
    println(HelloWorldModule.value)
}
val RickMortyCommand = command("rickmorty") {
    val repository by repositoryModule
    PluginScope.launch(Dispatchers.IO) {
        println(repository.getRandomCharacter(1))
    }
}
val InsertUserCommand = command("insertuser") {
    val repository by repositoryModule
    PluginScope.launch(Dispatchers.IO) {
        println(repository.insertUser(UserDTO(-1, UUID.randomUUID().toString(), UUID.randomUUID().toString())))
    }
}
val GetAllUsersCommand = command("getallusers") {
    val repository by repositoryModule
    PluginScope.launch(Dispatchers.IO) {
        println(repository.getAllUsers())
    }
}
