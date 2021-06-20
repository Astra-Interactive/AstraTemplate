package com.makeevrserg.empiretemplate.utils

import net.md_5.bungee.api.ChatColor
import java.util.regex.Pattern

class EmpireUtils{
    companion object{private val hexPattern =
        Pattern.compile("#[a-fA-F0-9]{6}|&#[a-fA-F0-9]{6}")

        fun HEXPattern(list: MutableList<String>?): List<String> {
            list ?: return mutableListOf()
            for (i in list.indices) list[i] = HEXPattern(list[i])
            return list
        }

        @JvmName("HEXPattern1")
        fun HEXPattern(line:String?):String?{
            line?:return  null
            return HEXPattern(line!!)
        }

        fun HEXPattern(line: String): String {
            var line = line
            var match = hexPattern.matcher(line)
            while (match.find()) {
                val color = line.substring(match.start(), match.end())
                line = line.replace(
                    color, ChatColor.of(
                        if (color.startsWith("&")) color.substring(1) else color
                    ).toString() + ""
                )
                match = hexPattern.matcher(line)
            }
            return org.bukkit.ChatColor.translateAlternateColorCodes('&', line)
        }

    }
}