package com.astrainteractive.astratemplate.utils.config

import com.astrainteractive.astralibs.AstraYamlParser
import com.google.gson.reflect.TypeToken
import com.astrainteractive.astratemplate.AstraTemplate
data class Section(
    val value1:String,
    val value2:Int
)
/**
 * Example config file with 3 types of initialization
 */
data class EmpireConfig(
    val config1: String,
    val config2: Int,
    val config3: Boolean,
    val section: Section,
    val list: List<String>,
    val another_list: List<Section>
) {
    companion object {
        fun new1(): EmpireConfig {
            val config = AstraYamlParser.fromYAML<EmpireConfig>(
                AstraTemplate.empireFiles.configFile.getConfig(),
                EmpireConfig::class.java
            )!!
            println(config)
            return config
        }

        fun new2() {
            val config = AstraYamlParser.fromYAML<EmpireConfig>(
                AstraTemplate.empireFiles.configFile2.getConfig(),
                object : TypeToken<EmpireConfig?>() {}.type,
                listOf("config__")
            )!!
            println(config)
        }

        fun new3() {
            val config = AstraYamlParser.fromYAML<EmpireConfig>(
                AstraTemplate.empireFiles.configFile2.getConfig(),
                object : TypeToken<EmpireConfig?>() {}.type,
                listOf("config2__","config__2_3")
            )!!
            println(config)
        }
    }
}
