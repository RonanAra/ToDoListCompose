package br.com.todolistcompose.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.todolistcompose.domain.entity.Todo
import br.com.todolistcompose.domain.entity.todo2
import br.com.todolistcompose.presentation.theme.ToDoListComposeTheme

@Composable
fun TodoItem(
    todo: Todo,
    onCompletedChange: (Boolean) -> Unit,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.padding(vertical = 8.dp),
        onClick = onItemClick,
        shape = MaterialTheme.shapes.medium,
        shadowElevation = 2.dp,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = todo.isCompleted,
                onCheckedChange = onCompletedChange
            )

            Spacer(Modifier.width(8.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = todo.title,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(Modifier.height(8.dp))

                todo.description?.let { desc ->
                    Text(
                        text = desc,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    ToDoListComposeTheme {
        TodoItem(
            todo = todo2,
            onCompletedChange = {},
            onItemClick = {}
        )
    }
}