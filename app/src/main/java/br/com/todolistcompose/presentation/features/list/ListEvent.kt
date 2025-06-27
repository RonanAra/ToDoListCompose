package br.com.todolistcompose.presentation.features.list

sealed interface ListEvent {
    data object OnBackPressed: ListEvent
    data class ShowCloseAppDialog(val enable: Boolean): ListEvent
    data class Delete(val id: Long) : ListEvent
    data class CompleteChanged(
        val id: Long,
        val isCompleted: Boolean
    ) : ListEvent

    data class AddEdit(val id: Long?) : ListEvent
}