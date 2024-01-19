package ru.astrainteractive.astratemplate.command.common

import ru.astrainteractive.astralibs.util.StringListExt.withEntry
import ru.astrainteractive.astratemplate.command.common.di.CommonCommandsDependencies
import ru.astrainteractive.klibs.kdi.Factory

class CommonCommandsFactory(
    dependencies: CommonCommandsDependencies
) : Factory<CommonCommands>,
    CommonCommandsDependencies by dependencies {

    override fun create(): CommonCommands {
        plugin.getCommand("atemp")?.setTabCompleter { sender, command, label, args ->
            when {
                args.isEmpty() -> listOf("atemp", "atempreload")
                args.size == 1 -> listOf("atemp", "atempreload").withEntry(args.last())
                else -> emptyList()
            }
        }
        plugin.getCommand("translation")?.setExecutor { sender, command, label, args ->
            sender.sendMessage(translation.general.getByByCheck.let(kyoriComponentSerializer::toComponent))
            true
        }
        return CommonCommands.Stub
    }
}
