package com.astrainteractive.astratemplate.sqldatabase.lib

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Entity(val tableName: String)

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class PrimaryKey(val autoIncrement: Boolean = true)

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class ColumnInfo(
    val name: String,
    val nullable: Boolean = false,
    val unique: Boolean = false,
)