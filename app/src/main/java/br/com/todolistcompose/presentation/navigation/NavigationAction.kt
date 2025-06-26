package br.com.todolistcompose.presentation.navigation

import androidx.navigation.NavOptionsBuilder

sealed interface NavigationAction {

    data class Navigate(
        val destination: TodoDestinations,
        val navOptions: NavOptionsBuilder.() -> Unit = {},
    ) : NavigationAction

    data object NavigateUp : NavigationAction
}