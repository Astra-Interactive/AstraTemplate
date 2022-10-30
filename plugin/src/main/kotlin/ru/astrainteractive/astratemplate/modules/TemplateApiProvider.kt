package ru.astrainteractive.astratemplate.modules

import ru.astrainteractive.astratemplate.api.ItemStackSpigotAPI
import ru.astrainteractive.astralibs.di.IModule

object TemplateApiProvider : IModule<ItemStackSpigotAPI>() {
    override fun initializer(): ItemStackSpigotAPI = ItemStackSpigotAPI.also { it.onEnable() }
}