package com.pschsch.kmmktor

import io.ktor.client.engine.*

internal expect fun PlatformEngine() : HttpClientEngineFactory<HttpClientEngineConfig>