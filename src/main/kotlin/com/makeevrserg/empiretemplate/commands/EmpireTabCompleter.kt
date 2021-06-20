package com.makeevrserg.empiretemplate.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

public class EmpireTabCompleter() : TabCompleter {


    //Доделать
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ): List<String>? {
        if (args.isEmpty())
            return on0Args()
        return null
    }

    private fun on0Args(): List<String> {
        val list = mutableListOf<String>()
        list.add("etemp")
        return list.toList()
    }


}