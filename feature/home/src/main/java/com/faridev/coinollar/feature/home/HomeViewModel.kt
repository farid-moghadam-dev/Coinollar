package com.faridev.coinollar.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faridev.coinollar.core.common.result.Result
import com.faridev.coinollar.domain.model.CurrenciesData
import com.faridev.coinollar.domain.usecase.FetchCurrenciesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HomeUiState(
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val currenciesData: CurrenciesData? = null,
    val errorMessage: String? = null
)

class HomeViewModel(
    private val fetchCurrenciesUseCase: FetchCurrenciesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        fetchCurrencies()
    }

    fun fetchCurrencies() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            when (val result = fetchCurrenciesUseCase()) {
                is Result.Success -> _uiState.update {
                    it.copy(isLoading = false, currenciesData = result.data, errorMessage = null)
                }
                is Result.Error -> _uiState.update {
                    it.copy(isLoading = false, errorMessage = result.message)
                }
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true) }
            when (val result = fetchCurrenciesUseCase()) {
                is Result.Success -> _uiState.update {
                    it.copy(isRefreshing = false, currenciesData = result.data, errorMessage = null)
                }
                is Result.Error -> _uiState.update {
                    it.copy(isRefreshing = false, errorMessage = result.message)
                }
            }
        }
    }
}
