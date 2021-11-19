import com.astrainteractive.astralibs.AstraCommandBuilder
import com.astrainteractive.astralibs.ETimer
import com.astrainteractive.astralibs.Logger
import com.astrainteractive.astratemplate.AstraTemplate
import com.astrainteractive.astratemplate.commands.EmpireTabCompleter
import com.astrainteractive.astratemplate.commands.Gui
import com.astrainteractive.astratemplate.commands.Reload
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender


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
        AstraTemplate.instance.getCommand("atempgui")!!.setExecutor(Gui())
    }


}