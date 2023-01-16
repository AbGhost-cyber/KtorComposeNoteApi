package com.example.data.response

import com.example.data.collections.Note
import kotlinx.serialization.Serializable

@Serializable
data class SimpleResponse(
    val success: Boolean = false,
    val message: String = ""
)

@Serializable
data class NoteListResponse(
    val success: Boolean = false,
    val message: String = "",
    val data: List<Note> = listOf()
)

@Serializable
data class NoteResponse(
    val success: Boolean = false,
    val message: String = "",
    val data: Note? = null
)
