package br.com.todolistcompose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import br.com.todolistcompose.ui.features.addedit.AddEditScreen
import br.com.todolistcompose.ui.features.list.ListScreen
import br.com.todolistcompose.ui.navigation.TodoDestinations.*

@Composable
fun TodoNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ListRoute
    ) {
        composable<ListRoute> {
            ListScreen(
                navigateToAddEditScreen = { id ->
                    navController.navigate(AddEditRoute(id))
                }
            )
        }

        composable<AddEditRoute> { backStackEntry ->
            val addEditRoute = backStackEntry.toRoute<AddEditRoute>()
            AddEditScreen()
        }
    }
}