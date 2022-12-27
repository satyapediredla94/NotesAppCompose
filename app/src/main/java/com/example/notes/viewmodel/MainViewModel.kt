package com.example.notes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.db.NotesRepository
import com.example.notes.model.Note
import com.example.notes.ui.notesList.NoteListEvent
import com.example.notes.utils.Routes
import com.example.notes.utils.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val notesRepository: NotesRepository
) : ViewModel() {

    val notes = notesRepository.getAllNotes()

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var deletedNote: Note? = null

    fun onEvent(event: NoteListEvent) {
        when (event) {
            is NoteListEvent.OnDeleteNoteClicked -> {
                viewModelScope.launch {
                    event.note.id?.let { notesRepository.deleteNote(it) }
                    sendUiEvent(UIEvent.ShowSnackBar("Note Deleted", "Undo"))
                }
            }
            NoteListEvent.OnAddNoteClick -> {
                sendUiEvent(UIEvent.Navigate(Routes.ADD_EDIT_NOTE))
            }
            is NoteListEvent.OnDoneChanged -> {
                viewModelScope.launch {
                    deletedNote = event.note
                    notesRepository.insertNote(
                        event.note.copy(
                            isComplete = event.isComplete
                        )
                    )
                }
            }
            is NoteListEvent.OnNoteItemClick -> {
                //Adding Note ID to get that note based on ID
                sendUiEvent(
                    UIEvent.Navigate(Routes.ADD_EDIT_NOTE + "?noteId=${event.note.id}")
                )
            }
            NoteListEvent.OnUndoDeleteClick -> {
                deletedNote?.let {
                    viewModelScope.launch {
                        notesRepository.insertNote(it)
                    }
                }
            }
        }
    }

    private fun sendUiEvent(uiEvent: UIEvent) {
        viewModelScope.launch {
            _uiEvent.send(uiEvent)
        }
    }

}