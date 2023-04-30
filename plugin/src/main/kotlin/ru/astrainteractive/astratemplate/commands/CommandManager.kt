import ru.astrainteractive.astralibs.getValue
import ru.astrainteractive.astratemplate.commands.addCommand
import ru.astrainteractive.astratemplate.commands.addCommandCompleter
import ru.astrainteractive.astratemplate.commands.damageCommand
import ru.astrainteractive.astratemplate.commands.damageCompleter
import ru.astrainteractive.astratemplate.commands.di.CommandManagerModule
import ru.astrainteractive.astratemplate.commands.randomRickAndMortyCharacter
import ru.astrainteractive.astratemplate.commands.reload
import ru.astrainteractive.astratemplate.commands.tabCompleter
import ru.astrainteractive.astratemplate.commands.tempGUI
import ru.astrainteractive.astratemplate.commands.translation

/**
 * Command handler for your plugin
 * It's better to create different executors for different commands
 */
class CommandManager(module: CommandManagerModule) {
    private val plugin by module.plugin

    /**
     * Here you should declare commands for your plugin
     *
     * Commands stored in plugin.yml
     *
     * etemp has TabCompleter
     */
    init {
        addCommandCompleter(plugin)
        tabCompleter(plugin)
        addCommand(plugin)

        randomRickAndMortyCharacter(plugin, module)
        damageCompleter(plugin, module)
        damageCommand(plugin, module)
        translation(plugin, module)
        tempGUI(plugin, module)
        reload(plugin, module)
    }
}
