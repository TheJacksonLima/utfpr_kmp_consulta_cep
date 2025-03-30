package com.jfl.utfpr_consulta_cep

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform