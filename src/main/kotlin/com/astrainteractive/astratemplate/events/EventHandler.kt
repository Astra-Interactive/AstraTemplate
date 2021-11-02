package com.astrainteractive.astratemplate.events

import com.astrainteractive.astralibs.IAstraListener
import com.astrainteractive.astralibs.IAstraManager


/**
 * Handler for all your events
 */
class EventHandler() : IAstraManager {

    /**
     * Example event
     * @see TemplateEvent
     */
    private val templateEvent: TemplateEvent = TemplateEvent()
    override val handlers: MutableList<IAstraListener> = mutableListOf()


    /**
     * Every event you declared in EventHandler must have onDisable method and should be called in this onDisable method
     */
    public override fun onDisable(){
        templateEvent.onDisable()
    }
}