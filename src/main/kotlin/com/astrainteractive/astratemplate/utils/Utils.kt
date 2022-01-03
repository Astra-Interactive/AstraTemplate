package com.astrainteractive.astratemplate.utils

import com.astrainteractive.astratemplate.sqldatabase.Callback
import com.astrainteractive.astratemplate.sqldatabase.Database
import java.lang.Exception
import java.sql.ResultSet


/**
 * For loop for [ResultSet]
 */
inline fun ResultSet.forEach(rs: (ResultSet) -> Unit) {
    while (this.next()) {
        rs(this)
    }
}

/**
 * mapNotNull for [ResultSet]
 * @return List<R>
 */
public inline fun <R : Any> ResultSet.mapNotNull(rs: (ResultSet) -> R?): List<R> {
    return mapNotNullTo(ArrayList<R>(), rs)
}

/**
 * Helper for [ResultSet.mapNotNull]
 */
public inline fun <R : Any, C : MutableCollection<in R>> ResultSet.mapNotNullTo(
    destination: C,
    rs: (ResultSet) -> R?
): C {
    forEach { element -> rs(element)?.let { destination.add(it) } }
    return destination
}

/**
 * Catching function with callback implementation
 * @param block Function with or without return type
 * @param callback Callback of section
 * @return T?
 */
public suspend inline fun <T> callbackCatching(callback: Callback? = null, block: () -> T?): T? = try {
    if (!Database.isInitialized)
        throw Exception("Database not initialized")
    block.invoke()
} catch (e: Exception) {
    e.printStackTrace()
    callback?.onFailure(e)
    null
}
