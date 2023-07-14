import ru.astrainteractive.astratemplate.command.addCommand
import ru.astrainteractive.astratemplate.command.addCommandCompleter
import ru.astrainteractive.astratemplate.command.damageCommand
import ru.astrainteractive.astratemplate.command.damageCompleter
import ru.astrainteractive.astratemplate.command.di.CommandManagerModule
import ru.astrainteractive.astratemplate.command.randomRickAndMortyCharacter
import ru.astrainteractive.astratemplate.command.reload
import ru.astrainteractive.astratemplate.command.tabCompleter
import ru.astrainteractive.astratemplate.command.tempGUI
import ru.astrainteractive.astratemplate.command.translation

/**
 * Command handler for your plugin
 * It's better to create different executors for different commands
 */
class CommandManager(module: CommandManagerModule) : CommandManagerModule by module {
    /**
     * Here you should declare commands for your plugin
     *
     * Commands stored in plugin.yml
     *
     * etemp has TabCompleter
     */
    init {
        addCommandCompleter()
        tabCompleter()
        addCommand()

        randomRickAndMortyCharacter()
        damageCompleter()
        damageCommand()
        translation()
        tempGUI()
        reload()
    }
}
