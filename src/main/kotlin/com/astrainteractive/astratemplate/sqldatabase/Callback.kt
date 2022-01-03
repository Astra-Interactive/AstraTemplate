package com.astrainteractive.astratemplate.sqldatabase

import java.lang.Exception

/**
 * Callback class
 *
 * You can create own extensions
 */
abstract class Callback {
    open suspend fun <T> onSuccess(result: T?) {}
    open suspend fun onFailure(e: Exception) {}
}