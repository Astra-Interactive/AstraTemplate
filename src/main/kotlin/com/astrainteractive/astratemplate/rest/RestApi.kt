package com.astrainteractive.astratemplate.rest

import com.astrainteractive.astralibs.rest.Path
import com.astrainteractive.astralibs.rest.ProxyTask
import com.astrainteractive.astralibs.rest.Request
import com.astrainteractive.astralibs.rest.Response
import kotlin.random.Random

interface RestApi {
    @Request("api/character/{id}")
    fun getRandomCharacter(@Path("id") id: Int = Random.nextInt(1, 30)): ProxyTask<Response<ResponseClass>>

}

data class ResponseClass(
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
)