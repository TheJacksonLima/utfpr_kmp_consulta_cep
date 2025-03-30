package com.jfl.utfpr_consulta_cep.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun BuscaCepScreen(viewModel: CepViewModel = remember { CepViewModel() }) {
    var cep by remember { mutableStateOf(TextFieldValue()) }
    val isCepValid = cep.text.length == 8 && cep.text.all { it.isDigit() }
    val cepInfo by viewModel.cepInfo.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            scaffoldState.snackbarHostState.showSnackbar(it)
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.padding(16.dp),
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = cep,
                enabled = !isLoading,
                onValueChange = { cep = it },
                label = { Text("Digite o CEP") },
                singleLine = true
            )

            Button(
                onClick = { viewModel.buscarCep(cep.text) },
                enabled = isCepValid && !isLoading,
                modifier = Modifier.fillMaxWidth().height(50.dp).padding(20.dp, 5.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor = Color.White,
                    contentColor = Color.Blue
                ),
                border = BorderStroke(2.dp, Color.Blue)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.height(10.dp))
                } else {
                    Text("Buscar CEP")
                }
            }

            cepInfo?.let {
                Text(text = "Logradouro: ${it.logradouro}")
                Text(text = "Bairro: ${it.bairro}")
                Text(text = "Cidade: ${it.localidade}")
                Text(text = "Estado: ${it.uf}")
                Text(text = "CEP: ${it.cep}")
            }
        }
    }
}
