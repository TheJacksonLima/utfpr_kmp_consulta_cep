package com.jfl.utfpr_consulta_cep.api

import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.test.*

class ViaCepApiTest {

    // Subclasse com HttpClient mockado
    class MockViaCepApi : ViaCepApi() {
        override val client: HttpClient = HttpClient(MockEngine) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
            engine {
                addHandler { request ->
                    when {
                        request.url.fullPath.endsWith("/85503390/json/") -> {
                            respond(
                                content = """
                                    {
                                        "cep": "85503-390",
                                        "logradouro": "Via do Conhecimento",
                                        "complemento": "",
                                        "bairro": "Fraron",
                                        "localidade": "Pato Branco",
                                        "uf": "PR",
                                        "ibge": "4118501",
                                        "gia": "",
                                        "ddd": "46",
                                        "siafi": "7553"
                                    }
                                """.trimIndent(),
                                status = HttpStatusCode.OK,
                                headers = headersOf(HttpHeaders.ContentType, "application/json")
                            )
                        }
                        request.url.fullPath.contains("/00000000/") || request.url.fullPath.contains("/abc123/") -> {
                            respondError(HttpStatusCode.NotFound)
                        }
                        else -> respondError(HttpStatusCode.BadRequest)
                    }
                }
            }
        }
    }

    @Test
    fun testValidCep_utfprPatoBranco_Mocked() = runTest {
        val api = MockViaCepApi()
        val result = api.getCepInfo("85503390").first()

        assertNotNull(result)
        assertEquals("85503-390", result?.cep)
        assertEquals("Via do Conhecimento", result?.logradouro)
        assertEquals("Pato Branco", result?.localidade)
        assertEquals("PR", result?.uf)
    }

    @Test
    fun testInvalidCep_inexistent_Mocked() = runTest {
        val api = MockViaCepApi()
        val result = api.getCepInfo("00000000").first()

        assertNull(result)
    }

    @Test
    fun testInvalidCep_malformed_Mocked() = runTest {
        val api = MockViaCepApi()
        val result = api.getCepInfo("abc123").first()

        assertNull(result)
    }
}
