package ru.astrainteractive.astratemplate.modules

import org.jetbrains.kotlin.com.google.gson.Gson
import ru.astrainteractive.astralibs.di.IModule
import ru.astrainteractive.astralibs.rest.RestRequester

object RestRequesterModule : IModule<RestRequester>() {
    override fun initializer(): RestRequester = RestRequester {
        this.baseUrl = "https://rickandmortyapi.com/"
        this.converterFactory = { json, clazz ->
            json?.let { Gson().fromJson(json, clazz) }
        }
        this.decoderFactory = Gson()::toJson
    }
}