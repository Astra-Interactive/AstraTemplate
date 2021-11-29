package com.astrainteractive.astratemplate.commands

import com.astrainteractive.astralibs.AstraLibs
import com.astrainteractive.astralibs.registerCommand
import com.astrainteractive.astratemplate.AstraTemplate
import com.astrainteractive.astratemplate.utils.EmpirePermissions

/**
 * Reload command handler
 */
class Reload {

    /**
     * This function called only when etempreload beign called
     *
     * Here you should also check for permission
     */
    private val onCommand = AstraLibs.registerCommand("reload"){sender,args->
        if (!sender.hasPermission(EmpirePermissions.RELOAD))
            return@registerCommand
        sender.sendMessage(AstraTemplate.translations.RELOAD)
        AstraTemplate.instance.reloadPlugin()
        sender.sendMessage(AstraTemplate.translations.RELOAD_COMPLETE)
    }

}