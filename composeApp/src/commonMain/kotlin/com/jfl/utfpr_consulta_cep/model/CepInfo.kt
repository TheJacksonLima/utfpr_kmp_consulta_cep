package com.jfl.utfpr_consulta_cep.model

import kotlinx.serialization.Serializable

@Serializable
data class CepInfo(
    val bairro: String = "",
    val cep: String = "",
    val complemento: String = "",
    val ddd: String = "",
    val estado: String = "",
    val gia: String = "",
    val ibge: String = "",
    val localidade: String = "",
    val logradouro: String = "",
    val regiao: String = "",
    val siafi: String = "",
    val uf: String = "",
    val unidade: String = ""
)