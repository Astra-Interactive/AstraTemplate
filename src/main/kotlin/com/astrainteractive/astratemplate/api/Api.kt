package com.astrainteractive.astratemplate.api

import com.astrainteractive.astralibs.observer.LiveData
import com.astrainteractive.astralibs.observer.MutableLiveData
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import java.util.*
import kotlin.random.Random

object Api {
    private val _items = MutableLiveData(
        IntRange(0, 70).map { randomItemStack() }
    )
    val items: LiveData<List<ItemStack>>
        get() = _items

    private fun randomItemStack(): ItemStack {
        val values = Material.values()
        val size = values.size
        val i = Random.nextInt(size)
        return ItemStack(values[i])
    }

    private lateinit var timer: Timer
    fun onEnable() {
        timer = kotlin.concurrent.timer("", daemon = true, 0L, 1000L) {
            val max = Random.nextInt(200)
            _items.value = IntRange(0, max).map { randomItemStack() }
        }
    }

    fun onDisable() {
        timer.cancel()
    }
}