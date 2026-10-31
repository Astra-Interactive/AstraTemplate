package ru.astrainteractive.astratemplate.gui.api.di

import ru.astrainteractive.astratemplate.gui.api.router.api.Router
import ru.astrainteractive.astratemplate.gui.api.router.stub.StubRouter

class StubGuiModule : GuiModule {
    override val router: Router = StubRouter()
}
