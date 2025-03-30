package com.jfl.utfpr_consulta_cep.api

import com.jfl.utfpr_consulta_cep.model.CepInfo
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ViaCepApi {
    val client = HttpClient {
        install(ContentNegotiation) {
            json()
        }
        install(HttpCache)
    }
    suspend fun getCepInfo(cep: String): Flow<CepInfo?> = flow {
        try {
            val cepInfo: CepInfo = client.get("https://viacep.com.br/ws/$cep/json/").body()
            emit(cepInfo)
        } catch (e: Exception) {
            emit(null)
        }
    }
}
