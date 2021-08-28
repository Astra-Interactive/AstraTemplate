package com.makeevrserg.empiretemplate.utils.config

import com.google.gson.reflect.TypeToken
import com.makeevrserg.empiretemplate.EmpireTemplate
import empirelibs.EmpireYamlParser

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
            val config = EmpireYamlParser.fromYAML<EmpireConfig>(
                EmpireTemplate.empireFiles.configFile.getConfig(),
                EmpireConfig::class.java
            )!!
            println(config)
            return config
        }

        fun new2() {
            val config = EmpireYamlParser.fromYAML<EmpireConfig>(
                EmpireTemplate.empireFiles.configFile2.getConfig(),
                object : TypeToken<EmpireConfig?>() {}.type,
                listOf("config__")
            )!!
            println(config)
        }

        fun new3() {
            val config = EmpireYamlParser.fromYAML<EmpireConfig>(
                EmpireTemplate.empireFiles.configFile2.getConfig(),
                object : TypeToken<EmpireConfig?>() {}.type,
                listOf("config2__","config__2_3")
            )!!
            println(config)
        }
    }
}
