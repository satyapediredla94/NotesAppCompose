package com.example.notes.ui.addEditNote

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.db.NotesRepository
import com.example.notes.model.Note
import com.example.notes.utils.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val notesRepository: NotesRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var note by mutableStateOf<Note?>(null)
        private set

    var title by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val noteId = savedStateHandle.get<Int>("noteId")
        noteId?.let {
            if (noteId != -1) {
                viewModelScope.launch {
                    notesRepository.getNoteById(it)?.let { note ->
                        title = note.title
                        description = note.description ?: ""
                        this@AddEditNoteViewModel.note = note
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.OnDescriptionChange -> {
                description = event.description
            }
            AddEditNoteEvent.OnSaveClicked -> {
                if (title.isBlank()) {
                    sendUiEvent(UIEvent.ShowSnackBar("Title cannot be blank"))
                    return
                }
                if (description.isBlank()) {
                    sendUiEvent(UIEvent.ShowSnackBar("Description cannot be blank"))
                    return
                }
                viewModelScope.launch {
                    notesRepository.insertNote(
                        Note(
                            title = title,
                            description = description,
                            isComplete = note?.isComplete ?: false,
                            id = note?.id
                        )
                    )
                }
                sendUiEvent(UIEvent.PopBackstack)
            }
            is AddEditNoteEvent.OnTitleChange -> {
                title = event.title
            }
        }
    }


    private fun sendUiEvent(uiEvent: UIEvent) {
        viewModelScope.launch {
            _uiEvent.send(uiEvent)
        }
    }
}