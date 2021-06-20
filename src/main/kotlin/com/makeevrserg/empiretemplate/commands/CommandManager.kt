import com.makeevrserg.empiretemplate.EmpireTemplate.Companion.plugin
import com.makeevrserg.empiretemplate.commands.EmpireTabCompleter
import com.makeevrserg.empiretemplate.utils.EmpirePermissions
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class CommandManager() : CommandExecutor {

    private var tabCompletion: EmpireTabCompleter = EmpireTabCompleter()

    init {
        plugin.getCommand("etemp")!!.tabCompleter = tabCompletion
        plugin.getCommand("etempreload")!!.setExecutor(this)
    }


    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        if (label.equals("etempreload", ignoreCase = true) && sender.hasPermission(EmpirePermissions.RELOAD)) {
            sender.sendMessage(plugin.empireTranslations.RELOAD)
            plugin.disablePlugin()
            plugin.initPlugin()
            sender.sendMessage(plugin.empireTranslations.RELOAD_COMPLETE)
        }

        return false
    }
}