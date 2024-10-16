package br.com.todolistcompose.presentation.navigation

import androidx.compose.runtime.Composable
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
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun TodoNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ListRoute
    ) {
        composable<ListRoute> {
            val viewModel: ListViewModel = koinViewModel()
            ListScreen(
                viewModel = viewModel,
                navigateToAddEditScreen = { id ->
                    navController.navigate(AddEditRoute(id))
                }
            )
        }

        composable<AddEditRoute> { backStackEntry ->
            val addEditRoute = backStackEntry.toRoute<AddEditRoute>()
            val viewModel: AddEditViewModel = koinViewModel {
                parametersOf(addEditRoute.id)
            }
            AddEditScreen(
                viewModel = viewModel,
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}