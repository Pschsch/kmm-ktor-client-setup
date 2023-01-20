package com.pschsch.kmmktor

import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

object HttpClientPool {

    @OptIn(ExperimentalSerializationApi::class)
    private val json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
    }

    private val clientsMap = HashMap<HttpClientConfig, HttpClient>()

    operator fun get(config: HttpClientConfig): HttpClient {
        clientsMap[config]?.let { return it }
        return createClient(config)
    }

    private fun createClient(config: HttpClientConfig): HttpClient {
        return HttpClient(PlatformEngine()) {
            install(ContentNegotiation) {
                json(json = json)
            }
            install(HttpTimeout) {
                socketTimeoutMillis = config.timeoutMillis * 1000L
                connectTimeoutMillis = config.timeoutMillis * 1000L
                requestTimeoutMillis = config.timeoutMillis * 1000L
            }
            if (config.loggers.isNotEmpty()) {
                install(Logging) {
                    logger = object : Logger {
                        override fun log(message: String) {
                            config.loggers.forEach {
                                it(message)
                            }
                        }
                    }
                    level = LogLevel.BODY
                }
            }
            defaultRequest {
                headers {
                    config.defaultRequestHeaders.forEach { entry ->
                        append(entry.key, entry.value)
                    }
                }
            }
        }.also {
            clientsMap[config] = it
        }
    }

}