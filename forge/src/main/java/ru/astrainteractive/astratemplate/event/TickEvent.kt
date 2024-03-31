package ru.astrainteractive.astratemplate.event

import net.minecraftforge.event.TickEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import ru.astrainteractive.astratemplate.event.core.ForgeEventBusListener

class TickEvent : ForgeEventBusListener {

    @SubscribeEvent
    @Suppress("UnusedPrivateMember")
    fun onTickEvent(e: TickEvent) {
        println("Tick happened!")
    }
}
