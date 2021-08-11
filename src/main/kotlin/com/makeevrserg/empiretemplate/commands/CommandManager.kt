import com.makeevrserg.empiretemplate.EmpireTemplate
import com.makeevrserg.empiretemplate.commands.EmpireTabCompleter
import com.makeevrserg.empiretemplate.commands.Reload
import com.makeevrserg.empiretemplate.utils.EmpirePermissions
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
        EmpireTemplate.instance.getCommand("etemp")!!.tabCompleter = tabCompletion
        EmpireTemplate.instance.getCommand("etempreload")!!.setExecutor(Reload())
    }

}