package com.example.notes.ui.notesList

import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notes.model.Note

@Composable
fun NoteItem(
    note: Note,
    onEvent: (NoteListEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = note.title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = {
                    onEvent(NoteListEvent.OnDeleteNoteClicked(note))
                }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                }
            }
            note.description?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = it, fontSize = 12.sp)
            }
        }
        Checkbox(checked = note.isComplete, onCheckedChange = {
            onEvent(NoteListEvent.OnDoneChanged(note, it))
        })
    }
}