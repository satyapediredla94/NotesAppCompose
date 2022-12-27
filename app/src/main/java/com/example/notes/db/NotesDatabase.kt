package com.example.notes.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notes.model.Note

@Database(version = 1, entities = [Note::class])
abstract class NotesDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao
}