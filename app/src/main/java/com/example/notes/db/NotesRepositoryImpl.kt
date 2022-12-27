package com.example.notes.db

import com.example.notes.model.Note

class NotesRepositoryImpl(
    private val dao: NotesDao
) : NotesRepository {
    override suspend fun insertNote(note: Note) = dao.insertNote(note)

    override fun getAllNotes() = dao.getAllNotes()

    override suspend fun deleteNote(id: Int) = dao.deleteNote(id)

    override fun getAllUnfinishedNotes() = dao.getAllUnfinishedNotes()

    override suspend fun getNoteById(noteId: Int) = dao.getNoteById(noteId)
}