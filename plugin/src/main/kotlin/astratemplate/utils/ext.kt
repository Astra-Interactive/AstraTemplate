package com.astrainteractive.astratemplate.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.astrainteractive.astralibs.async.BukkitMain
import ru.astrainteractive.astralibs.async.PluginScope
import kotlin.coroutines.CoroutineContext

fun <T> StateFlow<T>.collectOn(context: CoroutineContext = Dispatchers.BukkitMain, block: (T) -> Unit) =
    PluginScope.launch {
        collectLatest {
            withContext(context) {
                block(it)
            }
        }
    }