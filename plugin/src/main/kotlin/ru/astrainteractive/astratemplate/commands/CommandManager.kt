import ru.astrainteractive.astralibs.AstraLibs
import ru.astrainteractive.astralibs.di.getValue
import ru.astrainteractive.astralibs.utils.registerCommand
import ru.astrainteractive.astratemplate.commands.*
import ru.astrainteractive.astratemplate.modules.TranslationProvider


/**
 * Command handler for your plugin
 * It's better to create different executors for different commands
 * @see Reload
 */
class CommandManager {
    val translation by TranslationProvider

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
        RandomRickAndMortyCharacter()

        // It shows that [val XXX by IReloadable] is actually affected by reloading
        AstraLibs.registerCommand("translation") { sender, _ ->
            sender.sendMessage(translation.getByByCheck)
        }
    }


}