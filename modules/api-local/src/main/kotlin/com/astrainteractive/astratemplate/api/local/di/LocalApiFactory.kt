package com.astrainteractive.astratemplate.api.local.di

import com.astrainteractive.astratemplate.api.local.LocalApi
import com.astrainteractive.astratemplate.api.local.LocalApiImpl
import ru.astrainteractive.astralibs.Factory

class LocalApiFactory(
    private val module: LocalApiModule
) : Factory<LocalApi> {
    override fun build(): LocalApi {
        return LocalApiImpl(module)
    }
}
