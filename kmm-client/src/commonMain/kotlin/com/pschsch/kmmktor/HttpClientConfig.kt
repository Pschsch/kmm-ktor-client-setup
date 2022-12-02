package com.pschsch.kmmktor

data class HttpClientConfig(
    val timeoutMillis : Long = 15,
    val defaultRequestHeaders : Map<String, String> = emptyMap(),
    val enableLogging : Boolean = false
)