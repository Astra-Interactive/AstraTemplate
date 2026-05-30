package ru.astrainteractive.astratemplate.core.plugin

import com.charleskorn.kaml.YamlComment
import java.io.Serial
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.astrainteractive.klibs.mikro.exposed.model.DatabaseConfiguration

@Serializable
data class PluginConfiguration(
    @YamlComment("First line description for config1", "Second line description for config2")
    @SerialName("config_1")
    val config1: String = "NONE",
    @YamlComment("Some description for config2")
    @SerialName("config_2")
    val config2: Int = 0,
    @SerialName("config_3")
    val config3: Boolean = false,
    @SerialName("section")
    val section: Section = Section(),
    @SerialName("list")
    val list: List<String> = listOf(),
    @SerialName("another_list")
    @YamlComment("Description for list")
    val anotherList: List<Section> = listOf(),
    @SerialName("database")
    val database: DatabaseConfiguration = DatabaseConfiguration.H2("db")
) {
    @Serializable
    data class Section(
        @YamlComment("Description for value1")
        @SerialName("value_1")
        val value1: String = "NONE",
        @SerialName("value_2")
        val value2: Int = -1
    )
}
