import ru.astrainteractive.astralibs.commands.registerCommand
import ru.astrainteractive.astralibs.di.getValue
import ru.astrainteractive.astratemplate.AstraTemplate
import ru.astrainteractive.astratemplate.commands.addCommand
import ru.astrainteractive.astratemplate.commands.addCommandCompleter
import ru.astrainteractive.astratemplate.commands.damageCommand
import ru.astrainteractive.astratemplate.commands.damageCompleter
import ru.astrainteractive.astratemplate.commands.randomRickAndMortyCharacter
import ru.astrainteractive.astratemplate.commands.reload
import ru.astrainteractive.astratemplate.commands.tabCompleter
import ru.astrainteractive.astratemplate.commands.tempGUI
import ru.astrainteractive.astratemplate.modules.TranslationModule

/**
 * Command handler for your plugin
 * It's better to create different executors for different commands
 * @see Reload
 */
class CommandManager {
    val translation by TranslationModule

    /**
     * Here you should declare commands for your plugin
     *
     * Commands stored in plugin.yml
     *
     * etemp has TabCompleter
     */
    init {
        tabCompleter()
        addCommandCompleter()
        addCommand()
        damageCompleter()
        damageCommand()
        tempGUI()
        reload()
        randomRickAndMortyCharacter()

        // It shows that [val XXX by IReloadable] is actually affected by reloading
        AstraTemplate.instance.registerCommand("translation") {
            sender.sendMessage(translation.getByByCheck)
        }
    }
}
