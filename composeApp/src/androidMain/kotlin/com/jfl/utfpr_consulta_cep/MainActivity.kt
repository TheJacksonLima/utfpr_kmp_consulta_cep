package com.jfl.utfpr_consulta_cep

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.jfl.utfpr_consulta_cep.navigation.SetupNavigation
import com.jfl.utfpr_consulta_cep.screens.BuscaCep

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SetupNavigation()

        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    SetupNavigation()
}