package com.makeevrserg.empiretemplate.empirelibs


import com.makeevrserg.empiretemplate.EmpireTemplate
import com.makeevrserg.empiretemplate.events.TemplateEvent
import org.bukkit.event.Listener

/**
 * This interface provides you comfortability whe use events
 * @see TemplateEvent
 */
interface IEmpireListener:Listener {


    fun onEnable(manager: IEventManager): IEmpireListener {

        EmpireTemplate.instance.server.pluginManager.registerEvents(this, EmpireTemplate.instance)
        manager.addHandler(this)
        return this
    }
    abstract fun onDisable()
}