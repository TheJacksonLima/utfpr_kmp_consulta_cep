package com.jfl.utfpr_consulta_cep.api

import com.jfl.utfpr_consulta_cep.model.CepInfo
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

open class ViaCepApi {
    open val client = HttpClient {
        install(ContentNegotiation) {
            json()
        }
        install(HttpCache)
    }
    suspend fun getCepInfo(cep: String): Flow<CepInfo?> = flow {
        val response = client.get("https://viacep.com.br/ws/$cep/json/")
        if (response.status.isSuccess()) {
            emit(response.body<CepInfo>())
        } else {
            emit(null) // Ou lance uma exceção específica se preferir
        }
    }.catch { e ->
        // Log do erro aqui, se necessário
        emit(null) // Emita null ou outro valor padrão em caso de erro
    }
}