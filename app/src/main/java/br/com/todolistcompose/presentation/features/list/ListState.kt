package br.com.todolistcompose.presentation.features.list

import br.com.todolistcompose.domain.entity.Todo

sealed interface ListState {
    data object Loading: ListState
    data class MainState(
        val todos: List<Todo>,
        val showCloseAppDialog: Boolean = false
    ): ListState
}