package com.example.notes.di

import android.content.Context
import androidx.room.Room
import com.example.notes.db.NotesDao
import com.example.notes.db.NotesDatabase
import com.example.notes.db.NotesRepository
import com.example.notes.db.NotesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDb(
        @ApplicationContext context: Context
    ): NotesDatabase = Room.databaseBuilder(
        context,
        NotesDatabase::class.java,
        "NotesDB"
    ).build()

    @Provides
    @Singleton
    fun provideDao(
        db: NotesDatabase
    ): NotesDao = db.notesDao()

    @Provides
    @Singleton
    fun provideNotesRepository(
        notesDao: NotesDao
    ): NotesRepository = NotesRepositoryImpl(dao = notesDao)

}