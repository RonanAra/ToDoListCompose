package br.com.todolistcompose.ui.features.list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.todolistcompose.R
import br.com.todolistcompose.data.database.TodoDatabaseProvider
import br.com.todolistcompose.data.repository.TodoRepositoryImpl
import br.com.todolistcompose.domain.entity.Todo
import br.com.todolistcompose.domain.entity.todo1
import br.com.todolistcompose.domain.entity.todo2
import br.com.todolistcompose.ui.UiEvent
import br.com.todolistcompose.ui.components.TodoItem
import br.com.todolistcompose.ui.navigation.TodoDestinations
import br.com.todolistcompose.ui.theme.ToDoListComposeTheme

@Composable
fun ListScreen(
    navigateToAddEditScreen: (id: Long?) -> Unit
) {
    val context = LocalContext.current.applicationContext
    val database = TodoDatabaseProvider.provide(context)
    val repository = TodoRepositoryImpl(database.todoDao)
    val viewModel = viewModel<ListViewModel> {
        ListViewModel(repository = repository)
    }
    val todos by viewModel.todos.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UiEvent.Navigate<*> -> {
                    when (uiEvent.route) {
                        is TodoDestinations.AddEditRoute -> {
                            navigateToAddEditScreen(uiEvent.route.id)
                        }
                    }
                }
                else -> {}
            }
        }
    }

    ListContent(
        todos = todos,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun ListContent(
    todos: List<Todo>,
    onEvent: (ListEvent) -> Unit
) {
    Scaffold(
        modifier = Modifier,
        floatingActionButton = {
            AddFabButton(
                onClick = {
                    onEvent(ListEvent.AddEdit(null))
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .consumeWindowInsets(paddingValues),
            contentPadding = PaddingValues(16.dp)
        ) {
            itemsIndexed(todos) { index, todo ->
                TodoItem(
                    todo = todo,
                    onCompletedChange = { completed ->
                        onEvent(
                            ListEvent.CompleteChanged(
                                id = todo.id,
                                isCompleted = completed
                            )
                        )
                    },
                    onDeleteClick = {
                        onEvent(ListEvent.Delete(todo.id))
                    },
                    onItemClick = {
                        onEvent(ListEvent.AddEdit(todo.id))
                    }
                )

                if (index < todos.lastIndex) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun AddFabButton(onClick: () -> Unit) {
    FloatingActionButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(R.string.fab_add_description)
        )
    }
}

@Preview
@Composable
private fun Preview() {
    ToDoListComposeTheme {
        ListContent(
            todos = listOf(
                todo1,
                todo2,
                todo1
            ),
            onEvent = {}
        )
    }
}