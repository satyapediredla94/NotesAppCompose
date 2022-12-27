package com.example.notes.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.notes.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Query("SELECT * FROM Note")
    fun getAllNotes(): Flow<List<Note>>

    @Query("DELETE FROM Note WHERE id=:id")
    suspend fun deleteNote(id: Int)

    @Query("SELECT * FROM Note WHERE isComplete=0")
    fun getAllUnfinishedNotes(): Flow<List<Note>>

    @Query("SELECT * FROM Note WHERE id=:noteId")
    suspend fun getNoteById(noteId: Int): Note

}