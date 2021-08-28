package com.makeevrserg.empiretemplate.empirelibs

import net.md_5.bungee.api.ChatColor
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.inventory.ItemStack
import java.util.regex.Pattern


/**
 * Converting string from file configuration to hex with default param
 */
fun ConfigurationSection.getHEXString(path: String, def: String): String {
    return EmpireUtils.HEXPattern(getString(path, def)!!)
}

/**
 * Converting string from file configuration to hex without default param
 */
fun FileConfiguration.getHEXString(path: String): String? {
    return EmpireUtils.HEXPattern(getString(path))
}

/**
 * Converting string list from file configuration to hex without default param
 */
fun ConfigurationSection.getHEXStringList(path: String): List<String> {
    return EmpireUtils.HEXPattern(getStringList(path))
}

/**
 * Converting string to hex
 */
fun String.HEX(): String {
    return EmpireUtils.HEXPattern(this)
}

fun ItemStack.setDisplayName(name:String){
    val itemMeta = this.itemMeta
    itemMeta.setDisplayName(name)
    this.itemMeta = itemMeta
}

/**
 * If you have list with entries {"entry","ementry","emementry"} and entry="me", you'll have returned list {"ementry","emementry"}.
 *
 * Very useful for TabCompleter
 *
 */
fun List<String>.withEntry(entry:String,ignoreCase:Boolean=true): List<String> {
    val list = mutableListOf<String>()
    for (line in this)
        if (line.contains(entry,ignoreCase = true))
            list.add(line)
    return list
}


/**
 * Utils class
 *
 * Usage: EmpireUtils.fun()
 */
object EmpireUtils {


        private val hexPattern =
            Pattern.compile("#[a-fA-F0-9]{6}|&#[a-fA-F0-9]{6}")

        @JvmName("HEXPattern1")
        fun HEXPattern(_list: List<String>?): List<String> {
            val list = _list?.toMutableList() ?: return mutableListOf()
            for (i in list.indices) list[i] = HEXPattern(list[i])
            return list
        }

        fun HEXPattern(list: MutableList<String>?): List<String> {
            list ?: return mutableListOf()
            for (i in list.indices) list[i] = HEXPattern(list[i])
            return list
        }

        @JvmName("HEXPattern1")
        fun HEXPattern(line: String?): String? {
            line ?: return line
            return HEXPattern(line)
        }

        fun HEXPattern(l: String): String {
            var line = l
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