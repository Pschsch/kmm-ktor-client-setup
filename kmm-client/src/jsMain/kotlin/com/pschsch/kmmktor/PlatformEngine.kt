package com.pschsch.kmmktor

import io.ktor.client.engine.*
import io.ktor.client.engine.js.*

internal actual fun PlatformEngine() : HttpClientEngineFactory<HttpClientEngineConfig> = Js