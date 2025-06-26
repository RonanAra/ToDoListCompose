package br.com.todolistcompose.presentation.navigation

import androidx.navigation.NavOptionsBuilder
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

interface Navigator {
    val navigationChannel: Flow<NavigationAction>
    suspend fun navigateUp()
    suspend fun navigate(
        destination: TodoDestinations,
        navOptions: NavOptionsBuilder.() -> Unit = {},
    )
}

class DefaultNavigator : Navigator {

    private val _navigationActions = Channel<NavigationAction>()
    override val navigationChannel: Flow<NavigationAction> = _navigationActions.receiveAsFlow()

    override suspend fun navigate(
        destination: TodoDestinations,
        navOptions: NavOptionsBuilder.() -> Unit,
    ) {
        _navigationActions.trySend(
            NavigationAction.Navigate(
                destination = destination,
                navOptions = navOptions
            )
        )
    }

    override suspend fun navigateUp() {
        _navigationActions.trySend(NavigationAction.NavigateUp)
    }
}