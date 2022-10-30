package com.astrainteractive.astratemplate.domain.remote

import ru.astrainteractive.astralibs.rest.*
import kotlin.random.Random

interface RestApi {
    @Request("api/character/{id}")
    fun getRandomCharacter(@Path("id") id: Int = Random.nextInt(1, 30)): ProxyTask<Response<ResponseClass>>
}