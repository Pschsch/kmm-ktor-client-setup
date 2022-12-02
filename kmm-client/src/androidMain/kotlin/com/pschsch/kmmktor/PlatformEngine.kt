package com.pschsch.kmmktor

import io.ktor.client.engine.*
import io.ktor.client.engine.android.*

internal actual fun PlatformEngine() : HttpClientEngineFactory<HttpClientEngineConfig> = Android