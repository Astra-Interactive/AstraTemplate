package com.astrainteractive.astratemplate.utils

import ru.astrainteractive.astralibs.AstraYamlParser
import ru.astrainteractive.astralibs.EmpireSerializer
import ru.astrainteractive.astralibs.Logger


/**
 * Example config file with 3 types of initialization
 */
@kotlinx.serialization.Serializable
data class PluginConfig(
    val config1: String = "NONE",
    val config2: Int = 0,
    val config3: Boolean = false,
    val section: Section = Section(),
    val list: List<String> = listOf(),
    val another_list: List<Section> = listOf()
) {
    @kotlinx.serialization.Serializable
    data class Section(
        val value1: String = "NONE",
        val value2: Int = -1
    )

    companion object {
        lateinit var instance: PluginConfig

        /**
         * If you are lazy - you can use auto parser for your config
         * This is an old version using Gson
         */
        fun create(): PluginConfig {
            val config =
                AstraYamlParser.fileConfigurationToClass<PluginConfig>(Files.configFile.fileConfiguration)!!
            instance = config
            Logger.log("$config", "EmpireConfig")
            return config
        }

        /**
         * This is a new version using kotlinx.serialization
         * It will send console message when one of paramter in config file is wrong
         */
        fun kotlinxSerializaion(): PluginConfig {
            val config = EmpireSerializer.toClass<PluginConfig>(Files.configFile) ?: PluginConfig()
            instance = config
            Logger.log("$config", "EmpireConfig")
            return config
        }
    }
}
