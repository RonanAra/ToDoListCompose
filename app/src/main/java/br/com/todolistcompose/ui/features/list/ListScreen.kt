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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.todolistcompose.R
import br.com.todolistcompose.domain.entity.Todo
import br.com.todolistcompose.domain.entity.todo1
import br.com.todolistcompose.domain.entity.todo2
import br.com.todolistcompose.ui.components.TodoItem
import br.com.todolistcompose.ui.theme.ToDoListComposeTheme

@Composable
fun ListScreen(
    navigateToAddEditScreen: (id: Long?) -> Unit
) {
    ListContent(
        todos = listOf(),
        onAddClick = { navigateToAddEditScreen(it) },
        onItemClick = {},
        onCompletedChange = {},
        onDeleteClick = {}
    )
}

@Composable
fun ListContent(
    todos: List<Todo>,
    onAddClick: (id: Long?) -> Unit,
    onCompletedChange: (Boolean) -> Unit,
    onDeleteClick: () -> Unit,
    onItemClick: () -> Unit,
) {
    Scaffold(
        modifier = Modifier,
        floatingActionButton = {
            AddFabButton(onAddClick = { onAddClick(null) })
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
                    onCompletedChange = onCompletedChange,
                    onDeleteClick = onDeleteClick,
                    onItemClick = onItemClick
                )

                if (index < todos.lastIndex) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun AddFabButton(onAddClick: () -> Unit) {
    FloatingActionButton(onClick = onAddClick) {
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
            onAddClick = {},
            onItemClick = {},
            onDeleteClick = {},
            onCompletedChange = {}
        )
    }
}