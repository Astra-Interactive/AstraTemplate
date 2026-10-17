package ru.astrainteractive.astratemplate.gui.api.di

import ru.astrainteractive.astratemplate.gui.api.router.api.Router

interface GuiModule {
    val router: Router
}
