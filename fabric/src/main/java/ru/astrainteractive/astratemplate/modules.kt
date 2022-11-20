package ru.astrainteractive.astratemplate

import com.astrainteractive.astratemplate.domain.Repository
import com.astrainteractive.astratemplate.domain.local.entities.RatingRelationTable
import com.astrainteractive.astratemplate.domain.local.entities.UserTable
import com.astrainteractive.astratemplate.domain.remote.RestApi
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import ru.astrainteractive.astralibs.database_v2.Database
import ru.astrainteractive.astralibs.di.module
import ru.astrainteractive.astralibs.rest.RestRequester
import java.io.File

val sqlModule = module {
    runBlocking {
        val database = Database()
        database.openConnection("jdbc:sqlite:data.db", "org.sqlite.JDBC")
        UserTable.create(database)
        RatingRelationTable.create(database)
        database
    }
}
val restRequestorModule = module {
    RestRequester {
        this.baseUrl = "https://rickandmortyapi.com/"
        this.converterFactory = { json, clazz ->
            json?.let { Gson().fromJson(json, clazz) }
        }
        this.decoderFactory = Gson()::toJson
    }
}
val restModule = module {
    restRequestorModule.value.create(RestApi::class.java)
}
val repositoryModule = module {
    Repository(sqlModule.value, restModule.value)
}