package ru.astrainteractive.astratemplate.utils


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
}
