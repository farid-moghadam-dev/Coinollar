package com.faridev.coinollar.feature.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faridev.coinollar.core.common.result.Result
import com.faridev.coinollar.domain.model.CurrenciesData
import com.faridev.coinollar.domain.usecase.GetCurrencyDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class DetailUiState(
    val isLoading: Boolean = true,
    val currency: CurrenciesData.Currency? = null,
    val errorMessage: String? = null
)

class DetailViewModel(
    private val symbol: String,
    private val getCurrencyDetailUseCase: GetCurrencyDetailUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    init {
        loadCurrencyDetail()
    }

    private fun loadCurrencyDetail() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            when (val result = getCurrencyDetailUseCase(symbol)) {
                is Result.Success -> _uiState.update {
                    it.copy(isLoading = false, currency = result.data)
                }
                is Result.Error -> _uiState.update {
                    it.copy(isLoading = false, errorMessage = result.message)
                }
            }
        }
    }
}
