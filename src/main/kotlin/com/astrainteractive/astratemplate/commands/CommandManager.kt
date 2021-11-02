import com.astrainteractive.astratemplate.AstraTemplate
import com.astrainteractive.astratemplate.commands.EmpireTabCompleter
import com.astrainteractive.astratemplate.commands.Reload


/**
 * Command handler for your plugin
 * It's better to create different executors for different commands
 * @see Reload
 */
class CommandManager {

    /**
     * Tab completer for your plugin
     * @see EmpireTabCompleter
     */
    private var tabCompletion: EmpireTabCompleter = EmpireTabCompleter()

    /**
     * Here you should declare commands for your plugin
     *
     * Commands stored in plugin.yml
     *
     * etemp has TabCompleter
     */
    init {
        AstraTemplate.instance.getCommand("atemp")!!.tabCompleter = tabCompletion
        AstraTemplate.instance.getCommand("atempreload")!!.setExecutor(Reload())
    }

}