package ru.astrainteractive.astratemplate.plugin

import kotlinx.serialization.SerialName

/**
 * Example config file with kotlinx.serialization
 */
@kotlinx.serialization.Serializable
data class MainConfiguration(
    val config1: String = "NONE",
    val config2: Int = 0,
    val config3: Boolean = false,
    val section: Section = Section(),
    val list: List<String> = listOf(),
    @SerialName("another_list")
    val anotherList: List<Section> = listOf()
) {
    @kotlinx.serialization.Serializable
    data class Section(
        val value1: String = "NONE",
        val value2: Int = -1
    )
}
