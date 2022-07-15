package com.astrainteractive.astratemplate.utils

import com.astrainteractive.astralibs.AstraYamlParser
import com.astrainteractive.astralibs.EmpireSerializer
import com.astrainteractive.astralibs.Logger

val EmpireConfig: _EmpireConfig
    get() = _EmpireConfig.instance

/**
 * Example config file with 3 types of initialization
 */
@kotlinx.serialization.Serializable
data class _EmpireConfig(
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
        lateinit var instance: _EmpireConfig

        /**
         * If you are lazy - you can use auto parser for your config
         * This is an old version using Gson
         */
        fun create(): _EmpireConfig {
            val config =
                AstraYamlParser.fileConfigurationToClass<_EmpireConfig>(Files.configFile.getConfig())!!
            instance = config
            Logger.log("$config", "EmpireConfig")
            return config
        }

        /**
         * This is a new version using kotlinx.serialization
         * It will send console message when one of paramter in config file is wrong
         */
        fun kotlinxSerializaion(): _EmpireConfig {
            val config = EmpireSerializer.toClass<_EmpireConfig>(Files.configFile) ?: _EmpireConfig()
            instance = config
            Logger.log("$config", "EmpireConfig")
            return config
        }
    }
}
