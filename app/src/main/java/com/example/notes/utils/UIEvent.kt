package com.example.notes.utils

sealed class UIEvent {
    object PopBackstack: UIEvent()
    data class Navigate(val route: String) : UIEvent()
    data class ShowSnackBar(
        val message: String,
        val action: String? = null
    ): UIEvent()
}
