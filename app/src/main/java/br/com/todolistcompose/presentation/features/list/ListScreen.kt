package br.com.todolistcompose.presentation.features.list

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.todolistcompose.R
import br.com.todolistcompose.domain.entity.todo1
import br.com.todolistcompose.domain.entity.todo2
import br.com.todolistcompose.presentation.components.ActionIcon
import br.com.todolistcompose.presentation.components.SwipeItem
import br.com.todolistcompose.presentation.components.TodoItem
import br.com.todolistcompose.presentation.theme.ToDoListComposeTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun ListScreen(
    viewModel: ListViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsState()

    BackHandler {
        viewModel.onEvent(ListEvent.ShowCloseAppDialog(true))
    }

    when (val currentState = state) {
        ListState.Loading -> LoadingScreen()
        is ListState.MainState -> ListContent(
            state = currentState,
            onEvent = viewModel::onEvent
        )
    }
}

@Composable
private fun ListContent(
    state: ListState.MainState,
    onEvent: (ListEvent) -> Unit,
) {
    if (state.showCloseAppDialog) {
        CloseAppDialog(
            onConfirm = { onEvent(ListEvent.FinishApp) },
            onDismiss = { onEvent(ListEvent.ShowCloseAppDialog(false)) }
        )
    }

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
            itemsIndexed(state.todos) { index, todo ->
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

                    if (index < state.todos.lastIndex) {
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun AddFabButton(onClick: () -> Unit) {
    FloatingActionButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(R.string.fab_add_description)
        )
    }
}

@Composable
private fun LoadingScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun CloseAppDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = stringResource(R.string.dialog_title_close_app))
        },
        text = {
            Text(text = stringResource(R.string.dialog_message_close_app))
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = stringResource(R.string.dialog_button_yes))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.dialog_button_no))
            }
        }
    )
}


@Preview
@Composable
private fun Preview() {
    ToDoListComposeTheme {
        ListContent(
            state = ListState.MainState(
                todos = listOf(
                    todo1,
                    todo2,
                    todo1
                )
            ),
            onEvent = {}
        )
    }
}