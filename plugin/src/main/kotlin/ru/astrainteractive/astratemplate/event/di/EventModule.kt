package ru.astrainteractive.astratemplate.event.di

import org.bukkit.plugin.Plugin
import ru.astrainteractive.astralibs.event.EventListener
import ru.astrainteractive.astratemplate.plugin.Translation

interface EventModule {
    val eventListener: EventListener
    val plugin: Plugin
    val translation: Translation
}
