package com.astrainteractive.astratemplate.utils

import com.astrainteractive.astralibs.valueOfOrNull
import org.bukkit.Bukkit

enum class ServerVersion {
    v1_18_R1, UNMAINTAINED;

    override fun toString(): String = name

    companion object {
        var version: ServerVersion = UNMAINTAINED
            private set

        fun getServerVersion(): ServerVersion {
            val v = Bukkit.getServer().javaClass.packageName.split(".").last()
            version = valueOfOrNull<ServerVersion>(v) ?: UNMAINTAINED
            return version
        }
    }
}
