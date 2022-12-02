package com.pschsch.kmmktor

import io.ktor.client.engine.*
import io.ktor.client.engine.darwin.*

internal actual fun PlatformEngine() : HttpClientEngineFactory<HttpClientEngineConfig> = Darwin