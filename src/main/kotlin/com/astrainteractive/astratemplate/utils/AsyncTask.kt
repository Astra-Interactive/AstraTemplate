package com.astrainteractive.astratemplate.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.bukkit.Bukkit
import kotlin.coroutines.CoroutineContext
import org.bukkit.Bukkit.getScheduler

/**
 * Async task interface for multithreading.
 * It's better than [Bukkit.getScheduler]
 *
 * You probably shouldn't change [job] or [coroutineContext]. To launch AsyncTask you need to write [launch] in your code
 */
interface AsyncTask : CoroutineScope {
    private val job: Job
        get() = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO
}