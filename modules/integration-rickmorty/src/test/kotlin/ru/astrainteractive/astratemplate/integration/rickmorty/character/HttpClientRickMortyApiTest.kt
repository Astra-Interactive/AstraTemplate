@file:Suppress("FunctionNaming")

package ru.astrainteractive.astratemplate.integration.rickmorty.character

import com.sun.net.httpserver.HttpServer
import kotlinx.coroutines.test.runTest
import ru.astrainteractive.astratemplate.integration.rickmorty.character.model.RMResponse
import java.net.InetSocketAddress
import java.net.URI
import java.net.http.HttpClient
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class HttpClientRickMortyApiTest {
    private lateinit var server: HttpServer
    private lateinit var api: HttpClientRickMortyApi
    private val requestedPaths = mutableListOf<String>()
    private var responseBody = ""

    @BeforeTest
    fun setUp() {
        server = HttpServer.create(InetSocketAddress("127.0.0.1", 0), 0)
        server.createContext("/api/") { exchange ->
            requestedPaths += exchange.requestURI.path
            val bytes = responseBody.toByteArray()
            exchange.sendResponseHeaders(HTTP_OK, bytes.size.toLong())
            exchange.responseBody.use { body -> body.write(bytes) }
        }
        server.start()
        api = HttpClientRickMortyApi(
            httpClient = HttpClient.newHttpClient(),
            baseUri = URI("http://127.0.0.1:${server.address.port}/api/")
        )
    }

    @AfterTest
    fun tearDown() {
        server.stop(0)
    }

    @Test
    fun GIVEN_character_response_WHEN_character_is_requested_THEN_returns_it_and_ignores_unknown_fields() = runTest {
        responseBody = """
            {"id":2,"name":"Morty Smith","status":"Alive","species":"Human","gender":"Male","origin":{}}
        """.trimIndent()

        val result = api.getRandomCharacter(2)

        assertEquals(
            RMResponse(name = "Morty Smith", status = "Alive", species = "Human", gender = "Male"),
            result.getOrThrow()
        )
        assertEquals(listOf("/api/character/2"), requestedPaths)
    }

    @Test
    fun GIVEN_malformed_response_WHEN_character_is_requested_THEN_returns_failure() = runTest {
        responseBody = "not json"

        assertTrue(api.getRandomCharacter(1).isFailure)
    }

    private companion object {
        const val HTTP_OK = 200
    }
}
