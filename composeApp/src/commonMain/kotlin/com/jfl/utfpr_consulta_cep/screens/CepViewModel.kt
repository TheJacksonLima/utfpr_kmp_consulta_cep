package com.jfl.utfpr_consulta_cep.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfl.utfpr_consulta_cep.api.ViaCepApi
import com.jfl.utfpr_consulta_cep.model.CepInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.StateFlow


class CepViewModel : ViewModel() {
    private val api = ViaCepApi()

    private val _cepInfo = MutableStateFlow<CepInfo?>(null)
    val cepInfo = _cepInfo.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    fun buscarCep(cep: String) {
        if (cep == "11111111") {
            _errorMessage.value = "\"Erro ao buscar o CEP. Tente novamente mais tarde\""
            return
        }
        viewModelScope.launch {
            viewModelScope.launch {
                _isLoading.value = true
                _errorMessage.value = null
                try {
                    ViaCepApi().getCepInfo(cep).collect { info ->
                        _cepInfo.value = info
                    }
                }  catch (e: Exception) {
                    _errorMessage.value = "Erro ao buscar o CEP. Tente novamente mais tarde"
                }
                finally {
                    _isLoading.value = false
                }
            }
        }
    }
    }
