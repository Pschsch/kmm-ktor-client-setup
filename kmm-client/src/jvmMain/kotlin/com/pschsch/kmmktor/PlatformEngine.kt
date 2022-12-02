package com.pschsch.kmmktor

import io.ktor.client.engine.*
import io.ktor.client.engine.cio.*

internal actual fun PlatformEngine() : HttpClientEngineFactory<HttpClientEngineConfig> = CIO