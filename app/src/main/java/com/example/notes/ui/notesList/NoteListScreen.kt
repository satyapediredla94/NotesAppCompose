package com.example.notes.ui.notesList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.notes.utils.UIEvent
import com.example.notes.viewmodel.MainViewModel

@Composable
fun NoteListScreen(
    onNavigate: (UIEvent.Navigate) -> Unit,
    viewModel: MainViewModel = hiltViewModel()
) {
    val noOp: () -> Unit = {}
    val scaffoldState = rememberScaffoldState()
    val notes = viewModel.notes.collectAsState(initial = emptyList())
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UIEvent.Navigate -> onNavigate(event)
                is UIEvent.ShowSnackBar -> {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        event.message,
                        actionLabel = event.action
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(NoteListEvent.OnUndoDeleteClick)
                    }
                }
                else -> noOp()
            }
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Notes")
            } )
        },
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(NoteListEvent.OnAddNoteClick)
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Note")
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            items(notes.value) { note ->
                NoteItem(note = note, onEvent = viewModel::onEvent,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.onEvent(NoteListEvent.OnNoteItemClick(note))
                        }
                        .padding(16.dp))
            }
        }
    }
}