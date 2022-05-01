package com.example.plugins

import com.example.routes.noteRoute
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    install(Routing) {
        noteRoute()
    }
}
