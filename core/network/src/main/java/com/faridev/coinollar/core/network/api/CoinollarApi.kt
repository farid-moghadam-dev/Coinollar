package com.faridev.coinollar.core.network.api

import com.faridev.coinollar.core.network.model.CurrenciesResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class CoinollarApi(private val httpClient: HttpClient) {
    suspend fun getCurrenciesData(): CurrenciesResponseDto = httpClient.get("").body()
}
