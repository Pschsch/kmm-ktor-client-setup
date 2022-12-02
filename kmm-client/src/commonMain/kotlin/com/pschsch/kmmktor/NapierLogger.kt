package com.pschsch.kmmktor

import io.github.aakira.napier.Napier
import io.ktor.client.plugins.logging.*

internal object NapierLogger : Logger {
    override fun log(message: String) {
        Napier.d(message)
    }
}