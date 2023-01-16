package com.example.routes

import com.example.data.NoteDatabaseImpl
import com.example.data.collections.Note
import com.example.data.response.NoteListResponse
import com.example.data.response.NoteResponse
import com.example.data.response.SimpleResponse
import io.ktor.http.*
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.Conflict
import io.ktor.http.HttpStatusCode.Companion.NotFound
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.noteRoute() {
    route("/notes") {
        get {
            val notes = NoteDatabaseImpl.getNotes()
            call.respond(OK, NoteListResponse(success = true, data = notes))
        }
        get("{id?}") {
            val id = call.parameters["id"]
                ?: return@get call.respond(
                    BadRequest,
                    NoteResponse(message = "missing id")
                )
            val note = NoteDatabaseImpl.getNoteById(noteId = id)
                ?: return@get call.respond(
                    NotFound,
                    NoteResponse(message = "No note with id $id")
                )
            call.respond(NoteResponse(success = true, data = note))
        }
        post {
            val note = try {
                call.receive<Note>()
            } catch (e: ContentTransformationException) {
                call.respond(
                    BadRequest,
                    SimpleResponse(message = e.message ?: "bad content")
                )
                return@post
            }
            if (NoteDatabaseImpl.upsertNote(note)) {
                call.respond(
                    OK,
                    SimpleResponse(true, "note saved successfully")
                )
            } else {
                call.respond(
                    Conflict,
                    SimpleResponse(message = "an unknown error occurred")
                )
            }
        }
        delete("{id?}") {
            val id = call.parameters["id"]
                ?: return@delete call.respond(
                    BadRequest,
                    SimpleResponse(message = "missing id")
                )
            val noteExists = NoteDatabaseImpl.noteExists(id)
            if (noteExists) {
                NoteDatabaseImpl.deleteNote(id)
                call.respond(
                    OK,
                    SimpleResponse(true, "note deleted")
                )
            } else {
                call.respond(
                    NotFound,
                    SimpleResponse(message = "note not found")
                )
            }
        }
    }
}
