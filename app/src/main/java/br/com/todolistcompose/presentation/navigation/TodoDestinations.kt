package br.com.todolistcompose.presentation.navigation

import kotlinx.serialization.Serializable

sealed class TodoDestinations {
    @Serializable
    data object ListRoute: TodoDestinations()

    @Serializable
    data class AddEditRoute(val id: Long? = null): TodoDestinations()
}