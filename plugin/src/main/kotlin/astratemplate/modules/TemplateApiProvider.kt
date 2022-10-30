package com.astrainteractive.astratemplate.modules

import com.astrainteractive.astratemplate.api.ItemStackSpigotAPI
import ru.astrainteractive.astralibs.di.IModule

class TemplateApiProvider:IModule<ItemStackSpigotAPI>() {
    override fun initializer(): ItemStackSpigotAPI = ItemStackSpigotAPI.also { it.onEnable() }
}