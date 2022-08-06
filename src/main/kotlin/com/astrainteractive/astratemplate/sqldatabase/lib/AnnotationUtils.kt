package com.astrainteractive.astratemplate.sqldatabase.lib

import com.astrainteractive.astralibs.utils.ReflectionUtil
import com.astrainteractive.astralibs.utils.catching
import java.lang.reflect.AccessibleObject
import java.lang.reflect.Field
import java.lang.reflect.Parameter
import java.sql.ResultSet
import kotlin.reflect.KProperty1

fun ReflectionUtil.getDeclaredFieldValue(clazz: Class<*>, name: String, origin: Any): Any? = catching {
    val field = clazz.getDeclaredField(name)
    field.isAccessible = true
    val value = field.get(origin)
    field.isAccessible = false
    return value
}

object AnnotationUtils {

    inline fun <reified T> getAnnotation(it: Class<*>): T? = getAnnotation(it.annotations)
    inline fun <reified T> getAnnotation(it: Field): T? = getAnnotation(it.annotations)
    inline fun <reified T> getAnnotation(it: Parameter): T? = getAnnotation(it.annotations)
    inline fun <reified T> getAnnotation(it: AccessibleObject): T? = getAnnotation(it.annotations)

    inline fun <reified T> getAnnotation(annotations: Array<out Annotation>): T? =
        annotations.firstNotNullOfOrNull { it as? T }

    inline fun <reified T> annotationWithIndex(parameterAnnotations: kotlin.Array<out Array<out Annotation>>): List<Pair<Int, T & Any>> {
        var i = -1
        return parameterAnnotations.mapNotNull {
            i++
            it.firstNotNullOfOrNull { it as? T }?.let { i to it }
        }
    }

    fun <T> annotationsToParam(indexed: List<Pair<Int, T>>, args: Array<out Any>?): List<Pair<Any, T>> {
        return indexed.mapNotNull { (i, annotation) ->
            args?.getOrNull(i)?.let { it to annotation }
        }
    }

    fun resolveType(parameter: Parameter): String {
        return when (parameter.type) {
            Int::class.java -> "INTEGER"
            String::class.java -> "TEXT"
            Double::class.java -> "REAL"
            Float::class.java -> "REAL"
            ByteArray::class.java -> "VARBINARY"
            Byte::class.java -> "BIT"
            Long::class.java -> "INTEGER"
            Boolean::class.java -> "BIT"
            else -> throw Exception("Type could not be resolved")
        }
    }

    data class EntityInfo(
        val entity: Entity,
        val columns: List<EntityColumnInfo>?,
    ) {
        data class EntityColumnInfo(
            val columnInfo: ColumnInfo,
            val primaryKey: PrimaryKey?,
            val fieldName: String,
            val parameter: Parameter,
            private val fieldValue: Any?,
        ) {
            val sqlFieldValue: String?
                get() = fieldValue?.let {
                    (it as? String)?.let { "\"$it\"" } ?: it.toString()
                }
        }

        companion object
    }


}

fun <T> AnnotationUtils.EntityInfo.Companion.create(
    clazz: Class<out T>,
    instance: T? = null,
): AnnotationUtils.EntityInfo? {
    val entity = AnnotationUtils.getAnnotation<Entity>(clazz) ?: return null

    /**
     * We have a class; When we call declaredFields it returns all field including companion object and it's childrens
     * So we need to remove companion from this list
     * Companion always first; Default constructor values goes after companion and followed by it's children
     * So we need to create a sublist with size of default constructor parameters count
     */
    val fieldsNames = clazz.declaredFields.mapNotNull {
        if (it.name == "Companion") null else it.name
    }.subList(0, clazz.constructors[0].parameterCount)

    val list = clazz.constructors[0].parameters?.mapIndexedNotNull { i, it ->
        val info = AnnotationUtils.getAnnotation<ColumnInfo>(it) ?: return@mapIndexedNotNull null
        val primaryKey = AnnotationUtils.getAnnotation<PrimaryKey>(it)
        val fieldName = fieldsNames[i]
        val fieldValue = instance?.let { ReflectionUtil.getDeclaredFieldValue(clazz, fieldName, it) }
        AnnotationUtils.EntityInfo.EntityColumnInfo(
            columnInfo = info,
            primaryKey = primaryKey,
            fieldName = fieldName,
            parameter = it,
            fieldValue = fieldValue
        )
    }
    return AnnotationUtils.EntityInfo(entity, list)

}

/**
 * ex: User::id.columnInfo
 */
inline val <reified T, V> KProperty1<T, V>.columnInfo: ColumnInfo?
    get() = AnnotationUtils.EntityInfo.create(T::class.java)?.columns?.firstOrNull { it.fieldName == name }?.columnInfo

inline val <reified T, V> KProperty1<T, V>.columnName: String?
    get() = columnInfo?.name

@Suppress("UNCHECKED_CAST")
fun <T> fromResultSet(clazz: Class<out T>, info: AnnotationUtils.EntityInfo?, rs: ResultSet): T? {
    val constructor = info?.columns?.map {
        rs.getObject(it.columnInfo.name)
    } ?: return null
    return clazz.constructors[0].newInstance(*constructor.toTypedArray()) as? T?
}