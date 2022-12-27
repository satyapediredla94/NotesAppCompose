package com.example.notes.ui.notesList

import com.example.notes.model.Note

sealed class NoteListEvent {
    data class OnDeleteNoteClicked(val note: Note) : NoteListEvent()
    data class OnDoneChanged(val note: Note, val isComplete: Boolean): NoteListEvent()
    object OnUndoDeleteClick : NoteListEvent()
    data class OnNoteItemClick(val note: Note) : NoteListEvent()
    object OnAddNoteClick: NoteListEvent()
}
