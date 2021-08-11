package com.makeevrserg.empiretemplate.events


/**
 * Handler for all your events
 */
class EventHandler {

    /**
     * Example event
     * @see TemplateEvent
     */
    private val templateEvent:TemplateEvent = TemplateEvent()



    /**
     * Every event you declared in EventHandler must have onDisable method and should be called in this onDisable method
     */
    public fun onDisable(){
        templateEvent.onDisable()
    }
}