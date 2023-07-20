package ru.astrainteractive.astratemplate.api.local.di

import ru.astrainteractive.astratemplate.api.local.LocalApi
import ru.astrainteractive.astratemplate.api.local.LocalApiImpl
import ru.astrainteractive.klibs.kdi.Factory

class LocalApiFactory(
    private val module: LocalApiModule
) : Factory<LocalApi> {
    override fun create(): LocalApi {
        return LocalApiImpl(module)
    }
}
