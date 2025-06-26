package br.com.todolistcompose.presentation

sealed interface UiEvent {
    data class ShowSnackBar(val message: String) : UiEvent
}