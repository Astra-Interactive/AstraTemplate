package ru.astrainteractive.astratemplate.modules

import com.astrainteractive.astratemplate.domain.remote.RestApi
import ru.astrainteractive.astralibs.di.IModule

object RestApiModule : IModule<RestApi>() {
    override fun initializer(): RestApi = RestRequesterModule.value.create(RestApi::class.java)
}