package com.astrainteractive.astratemplate.api.remote

import com.astrainteractive.astralibs.rest.*
import org.jetbrains.kotlin.com.google.gson.Gson
import kotlin.random.Random

interface RestApi {
    @Request("api/character/{id}")
    fun getRandomCharacter(@Path("id") id: Int = Random.nextInt(1, 30)): ProxyTask<Response<ResponseClass>>
}
object RestBuilder{
        fun build() = RestRequester {
            this.baseUrl = "https://rickandmortyapi.com/"
            this.converterFactory = { json, clazz ->
                json?.let { Gson().fromJson(json, clazz) }
            }
            this.decoderFactory = Gson()::toJson
        }.create(RestApi::class.java)
}

data class ResponseClass(
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
)