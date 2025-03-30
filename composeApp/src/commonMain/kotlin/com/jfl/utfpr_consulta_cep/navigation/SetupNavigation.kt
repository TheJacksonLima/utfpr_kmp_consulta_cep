package com.jfl.utfpr_consulta_cep.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.jfl.utfpr_consulta_cep.screens.BuscaCep
import com.jfl.utfpr_consulta_cep.screens.BuscaCepScreen

@Composable
fun SetupNavigation() {
    Navigator(
        screen = BuscaCep("Home")
    ) {
            navigator -> SlideTransition(navigator)
    }
}
