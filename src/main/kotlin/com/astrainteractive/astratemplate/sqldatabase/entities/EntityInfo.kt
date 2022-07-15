package com.astrainteractive.astratemplate.sqldatabase.entities

/**
 * Class for better handling database, so you won't mistake while writing database's value names
 */
data class EntityInfo(
    val name: String,
    val type: String,
    val primaryKey: Boolean = false,
    val autoIncrement: Boolean = false,
    val nullable: Boolean = false,
    val unique:Boolean = false
)