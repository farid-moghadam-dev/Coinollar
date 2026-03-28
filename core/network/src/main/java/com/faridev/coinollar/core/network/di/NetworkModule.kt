package com.faridev.coinollar.core.network.di

import com.faridev.coinollar.core.network.BuildConfig
import com.faridev.coinollar.core.network.api.CoinollarApi
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import timber.log.Timber

val networkModule = module {

    single {
        HttpClient(Android) {
            install(Logging) {
                level = LogLevel.INFO
                logger = object : io.ktor.client.plugins.logging.Logger {
                    override fun log(message: String) {
                        Timber.tag("Ktor").d(message)
                    }
                }
            }

            install(ContentNegotiation) {
                json(
                    json = Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }

            HttpResponseValidator {
                validateResponse { response ->
                    if (!response.status.isSuccess()) {
                        val errorBody = response.bodyAsText()
                        throw ResponseException(response, errorBody)
                    }
                }
            }

            defaultRequest {
                url {
                    takeFrom(BuildConfig.BASE_URL)
                    parameters.append("key", BuildConfig.API_KEY)
                }
            }
        }
    }

    single { CoinollarApi(get()) }
}
