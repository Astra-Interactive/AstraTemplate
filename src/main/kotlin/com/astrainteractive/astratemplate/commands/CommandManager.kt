import com.astrainteractive.astralibs.AstraLibs
import com.astrainteractive.astralibs.registerTabCompleter
import com.astrainteractive.astralibs.withEntry
import com.astrainteractive.astratemplate.AstraTemplate
import com.astrainteractive.astratemplate.commands.EmpireTabCompleter
import com.astrainteractive.astratemplate.commands.Gui
import com.astrainteractive.astratemplate.commands.Reload


/**
 * Command handler for your plugin
 * It's better to create different executors for different commands
 * @see Reload
 */
class CommandManager {
    /**
     * Here you should declare commands for your plugin
     *
     * Commands stored in plugin.yml
     *
     * etemp has TabCompleter
     */
    init {
        AstraLibs.registerTabCompleter("atemp"){sender, args ->
            if (args.isEmpty())
                return@registerTabCompleter listOf("etemp", "etempreload")
            if (args.size == 1)
                return@registerTabCompleter listOf("etemp", "etempreload").withEntry(args.last())
            return@registerTabCompleter listOf<String>()
        }
        Reload()
        Gui()
    }


}