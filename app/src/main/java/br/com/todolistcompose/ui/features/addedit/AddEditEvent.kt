package br.com.todolistcompose.ui.features.addedit

sealed interface AddEditEvent {
    data class TitleChanged(val title: String) : AddEditEvent
    data class DescriptionChanged(val description: String) : AddEditEvent
    data object Save : AddEditEvent
}