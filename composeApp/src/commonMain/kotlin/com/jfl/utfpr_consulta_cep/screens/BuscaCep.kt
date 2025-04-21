package com.jfl.utfpr_consulta_cep.screens

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

data class BuscaCep( val id:String): Screen {

    @Composable
    override fun Content(){
        BuscaCepScreen()
    }
}