package br.com.todolistcompose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import br.com.todolistcompose.presentation.features.addedit.AddEditScreen
import br.com.todolistcompose.presentation.features.addedit.AddEditViewModel
import br.com.todolistcompose.presentation.features.list.ListScreen
import br.com.todolistcompose.presentation.features.list.ListViewModel
import br.com.todolistcompose.presentation.navigation.TodoDestinations.AddEditRoute
import br.com.todolistcompose.presentation.navigation.TodoDestinations.ListRoute
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

@Composable
fun TodoNavHost() {
    val navController = rememberNavController()
    val navigator = koinInject<Navigator>()

    ObserveAsEvents(navigator.navigationChannel) { action ->
        when (action) {
            is NavigationAction.Navigate -> navController.navigate(action.destination) {
                action.navOptions(this)
            }
            NavigationAction.NavigateUp -> navController.navigateUp()
        }
    }

    NavHost(
        navController = navController,
        startDestination = ListRoute
    ) {
        composable<ListRoute> {
            val viewModel: ListViewModel = koinViewModel {
                parametersOf(navigator)
            }
            ListScreen(viewModel = viewModel)
        }

        composable<AddEditRoute> { backStackEntry ->
            val addEditRoute = backStackEntry.toRoute<AddEditRoute>()
            val viewModel: AddEditViewModel = koinViewModel {
                parametersOf(
                    addEditRoute.id,
                    navigator
                )
            }
            AddEditScreen(viewModel = viewModel)
        }
    }
}

@Composable
fun <T> ObserveAsEvents(
    flow: Flow<T>,
    key2: Any? = null,
    key3: Any? = null,
    onEvent: (T) -> Unit,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(
        key1 = lifecycleOwner.lifecycle,
        key2 = key2,
        key3 = key3
    ) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                flow.collect(onEvent)
            }
        }
    }
}