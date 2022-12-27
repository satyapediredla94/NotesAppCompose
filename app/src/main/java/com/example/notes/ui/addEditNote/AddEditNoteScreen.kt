package com.example.notes.ui.addEditNote

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.notes.utils.UIEvent

@Composable
fun AddEditNoteScreen(
    onPopBackStack: () -> Unit,
    viewModel: AddEditNoteViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true, block = {
        viewModel.uiEvent.collect { event ->
            when (event) {
                UIEvent.PopBackstack -> onPopBackStack()
                is UIEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        event.message,
                        event.action
                    )
                }
                else -> Unit
            }
        }
    })

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Notes")
            },
                navigationIcon = {
                    IconButton(onClick = {
                        onPopBackStack()
                    }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                })
        },
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(AddEditNoteEvent.OnSaveClicked)
            }) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "Done")
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            TextField(
                value = viewModel.title,
                onValueChange = { title ->
                    viewModel.onEvent(AddEditNoteEvent.OnTitleChange(title))
                },
                placeholder = {
                    Text(text = "Title")
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = viewModel.description,
                onValueChange = { description ->
                    viewModel.onEvent(AddEditNoteEvent.OnDescriptionChange(description))
                },
                placeholder = {
                    Text(text = "Description")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = false,
                maxLines = 5
            )
        }
    }
}