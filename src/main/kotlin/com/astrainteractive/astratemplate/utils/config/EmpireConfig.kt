package com.astrainteractive.astratemplate.utils.config

import com.astrainteractive.astralibs.AstraYamlParser
import com.astrainteractive.astralibs.getHEXString
import com.google.gson.reflect.TypeToken
import com.astrainteractive.astratemplate.AstraTemplate
import org.bukkit.configuration.ConfigurationSection

data class Section(
    val value1: String = "NONE",
    val value2: Int = -1
) {
    companion object {
        fun get1(s: ConfigurationSection?): Section {
            return AstraYamlParser.parser.configurationSectionToClass<Section>(s ?: return Section())!!
        }

        fun get2(s: ConfigurationSection?): Section {
            s ?: return Section()
            val value1 = s.getHEXString("value1", "AAAA")
            val value2 = s.getInt("value2")
            return Section(value1 = value1, value2 = value2)
        }

        fun getList(s: List<*>?): List<Section> {
            println("Get list ${s}")
            return s?.map {
                val s = (it as LinkedHashMap<String, Any>)
                Section(value1 = s.getOrDefault("value1", "") as String, value2 = s.getOrDefault("value2", 0) as Int)
            } ?: listOf()

        }
    }
}

/**
 * Example config file with 3 types of initialization
 */
data class EmpireConfig(
    val config1: String = "NONE",
    val config2: Int = 0,
    val config3: Boolean = false,
    val section: Section = Section(),
    val list: List<String> = listOf(),
    val another_list: List<Section> = listOf()
) {
    companion object {
        /**
         * If you are lazy - you can use auto parser for your config
         */
        fun new1(): EmpireConfig {
            val config =
                AstraYamlParser.parser.fileConfigurationToClass<EmpireConfig>(AstraTemplate.empireFiles.configFile.getConfig())!!
            println(config)
            return config
        }

        fun new2(): EmpireConfig {
            val c = AstraTemplate.empireFiles.configFile.getConfig()
            val config1 = c.getString("config1", "NONE")!!
            val config2 = c.getInt("config2", 5)
            val config3 = c.getBoolean("config3", true)
            val section = Section.get1(c.getConfigurationSection("section"))
//            val section = Section.get2(c.getConfigurationSection("section"))
            val list = c.getStringList("list")
            val another_list = Section.getList(c.getList("another_list"))
            return EmpireConfig(
                config1 = config1,
                config2 = config2,
                config3 = config3,
                section = section,
                list = list,
                another_list = another_list
            )
        }
    }
}
