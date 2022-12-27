package com.example.notes

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.notes.ui.addEditNote.AddEditNoteScreen
import com.example.notes.ui.notesList.NoteListScreen
import com.example.notes.ui.theme.NotesTheme
import com.example.notes.utils.Routes
import com.example.notes.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Routes.NOTE_LIST) {
                    composable(Routes.NOTE_LIST) {
                        NoteListScreen(onNavigate = {
                            navController.navigate(it.route)
                        })
                    }
                    composable(
                        Routes.ADD_EDIT_NOTE + "?noteId={noteId}",
                        arguments = listOf(
                            navArgument(name = "noteId") {
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )
                    ) {
                        AddEditNoteScreen(onPopBackStack = {
                            navController.navigateUp()
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DefaultPreview() {
    NotesTheme {
        Greeting("Android")
    }
}