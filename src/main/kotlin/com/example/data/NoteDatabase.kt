package com.example.data

import com.example.data.collections.Note

interface NoteDatabase {
    suspend fun getNotes(): List<Note>
    suspend fun upsertNote(note: Note): Boolean
    suspend fun deleteNote(noteId: String): Boolean
    suspend fun getNoteById(noteId: String): Note?
}
