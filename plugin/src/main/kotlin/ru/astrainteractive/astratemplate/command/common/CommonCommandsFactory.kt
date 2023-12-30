package ru.astrainteractive.astratemplate.command.common

import ru.astrainteractive.astralibs.command.registerCommand
import ru.astrainteractive.astralibs.command.registerTabCompleter
import ru.astrainteractive.astralibs.util.withEntry
import ru.astrainteractive.astratemplate.command.common.di.CommonCommandsDependencies
import ru.astrainteractive.klibs.kdi.Factory

class CommonCommandsFactory(
    dependencies: CommonCommandsDependencies
) : Factory<CommonCommands>,
    CommonCommandsDependencies by dependencies {

    override fun create(): CommonCommands {
        plugin.registerTabCompleter("atemp") {
            when {
                args.isEmpty() -> listOf("atemp", "atempreload")
                args.size == 1 -> listOf("atemp", "atempreload").withEntry(args.last())
                else -> emptyList()
            }
        }
        plugin.registerCommand("translation") {
            with(translationContext) {
                sender.sendMessage(translation.general.getByByCheck)
            }
        }
        return CommonCommands.Stub
    }
}
