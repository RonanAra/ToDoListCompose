package br.com.todolistcompose.presentation.features.list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.todolistcompose.R
import br.com.todolistcompose.domain.entity.Todo
import br.com.todolistcompose.domain.entity.todo1
import br.com.todolistcompose.domain.entity.todo2
import br.com.todolistcompose.presentation.components.ActionIcon
import br.com.todolistcompose.presentation.components.SwipeItem
import br.com.todolistcompose.presentation.components.TodoItem
import br.com.todolistcompose.presentation.theme.ToDoListComposeTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun ListScreen(
    viewModel: ListViewModel = koinViewModel()
) {
    val todos by viewModel.todos.collectAsState()

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
                SwipeItem(
                    action = {
                        ActionIcon(
                            icon = Icons.Filled.Delete,
                            onClick = { onEvent(ListEvent.Delete(todo.id)) },
                            backgroundColor = Color.White,
                            tint = Color.Red,
                            contentDescription = stringResource(R.string.ic_delete_description),
                        )
                    }
                ) {
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