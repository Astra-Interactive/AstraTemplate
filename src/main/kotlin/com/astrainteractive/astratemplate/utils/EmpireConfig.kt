package com.astrainteractive.astratemplate.utils

import com.astrainteractive.astralibs.AstraYamlParser
import com.astrainteractive.astralibs.Logger


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
    data class Section(
        val value1: String = "NONE",
        val value2: Int = -1
    )

    companion object {
        lateinit var instance: EmpireConfig

        /**
         * If you are lazy - you can use auto parser for your config
         */
        fun create(): EmpireConfig {
            val config =
                AstraYamlParser.fileConfigurationToClass<EmpireConfig>(Files.configFile.getConfig())!!
            instance = config
            Logger.log("$config", "EmpireConfig")
            return config
        }
    }
}
