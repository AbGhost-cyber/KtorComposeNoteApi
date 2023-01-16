package com.example

import com.example.plugins.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8085, host = "0.0.0.0") {
        configureHeaders()
        configureMonitoring()
        configureRouting()
        configureSerialization()
    }.start(wait = true)
}
