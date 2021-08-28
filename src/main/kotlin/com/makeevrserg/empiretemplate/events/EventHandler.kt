package com.makeevrserg.empiretemplate.events

import com.makeevrserg.empiretemplate.empirelibs.IEmpireListener
import com.makeevrserg.empiretemplate.empirelibs.IEventManager


/**
 * Handler for all your events
 */
class EventHandler() : IEventManager {

    /**
     * Example event
     * @see TemplateEvent
     */
    private val templateEvent:TemplateEvent = TemplateEvent()
    override val handlers: MutableList<IEmpireListener> = mutableListOf()


    /**
     * Every event you declared in EventHandler must have onDisable method and should be called in this onDisable method
     */
    public override fun onDisable(){
        templateEvent.onDisable()
    }
}