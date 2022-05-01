package com.example.data

import com.example.data.collections.Note
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.coroutine.toList
import org.litote.kmongo.reactivestreams.KMongo

object NoteDatabaseImpl : NoteDatabase {
    private val client = KMongo.createClient().coroutine
    private val database = client.getDatabase("ComposeNoteDb")
    private val notes = database.getCollection<Note>()

    override suspend fun getNotes(): List<Note> {
        return notes.collection.find().toList()
    }

    override suspend fun upsertNote(note: Note): Boolean {
        return if (noteExists(note.id)) {
            notes.updateOneById(note.id, note).wasAcknowledged()
        } else {
            notes.insertOne(note).wasAcknowledged()
        }
    }

    suspend fun noteExists(noteId: String): Boolean {
        return notes.findOneById(noteId) != null
    }

    override suspend fun deleteNote(noteId: String): Boolean {
        return notes.deleteOneById(noteId).wasAcknowledged()
    }

    override suspend fun getNoteById(noteId: String): Note? {
        return notes.findOneById(noteId)
    }
}
