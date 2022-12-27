package com.example.notes.ui.addEditNote

sealed class AddEditNoteEvent {
    data class OnTitleChange(val title: String): AddEditNoteEvent()
    data class OnDescriptionChange(val description: String): AddEditNoteEvent()
    object OnSaveClicked: AddEditNoteEvent()
}
