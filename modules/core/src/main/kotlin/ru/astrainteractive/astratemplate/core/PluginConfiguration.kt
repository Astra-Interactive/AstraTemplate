package ru.astrainteractive.astratemplate.core

import com.charleskorn.kaml.YamlComment
import kotlinx.serialization.SerialName
import ru.astrainteractive.astralibs.exposed.model.DatabaseConfiguration

/**
 * Example config file with kotlinx.serialization
 */
@kotlinx.serialization.Serializable
data class PluginConfiguration(
    @YamlComment("First line description for config1", "Second line description for config2")
    val config1: String = "NONE",
    @YamlComment("Some description for config2")
    val config2: Int = 0,
    val config3: Boolean = false,
    val section: Section = Section(),
    val list: List<String> = listOf(),
    @SerialName("another_list")
    @YamlComment("Description for list")
    val anotherList: List<Section> = listOf(),
    val database: DatabaseConfiguration = DatabaseConfiguration.H2("db")
) {
    @kotlinx.serialization.Serializable
    data class Section(
        @YamlComment("Description for value1")
        val value1: String = "NONE",
        val value2: Int = -1
    )
}
