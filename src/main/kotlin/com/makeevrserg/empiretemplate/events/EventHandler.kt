package com.makeevrserg.empiretemplate.events

class EventHandler {

    private val templateEvent:TemplateEvent = TemplateEvent()

    public fun onDisable(){
        templateEvent.onDisable()
    }
}