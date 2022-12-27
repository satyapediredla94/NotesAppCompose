package com.example.notes.db

import com.example.notes.model.Note
import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    suspend fun insertNote(note: Note)
    fun getAllNotes(): Flow<List<Note>>
    suspend fun deleteNote(id: Int)
    fun getAllUnfinishedNotes(): Flow<List<Note>>
    suspend fun getNoteById(noteId: Int): Note?
}