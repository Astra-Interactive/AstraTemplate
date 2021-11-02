package com.astrainteractive.astratemplate.utils.config

import com.astrainteractive.astratemplate.AstraTemplate

object Config {
    var boolV: Boolean = true
        private set
    var strV: String = "String"
        private set
    var intV: Int = 0
        private set
    var doubleV: Double = 0.0
        private set
    private var _listV: List<String> = listOf("1", "2")
    val listV: List<String>
        get() = _listV

    fun load() {
        val config = AstraTemplate.empireFiles.configFile3.getConfig()
        val section = config.getConfigurationSection("section") ?: return
        boolV = section.getBoolean("boolV", true)
        strV = section.getString("strV", "String")!!
        intV = section.getInt("intV", 0)
        doubleV = section.getDouble("doubleV", 0.0)
        _listV = section.getStringList("listV")
    }
    fun print(){
        println("-------------------------------")
        println("$boolV = boolV")
        println("$strV = strV")
        println("$intV = intV")
        println("$doubleV = doubleV")
        println("$_listV = _listV")
    }
}