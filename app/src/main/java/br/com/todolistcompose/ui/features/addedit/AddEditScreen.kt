package br.com.todolistcompose.ui.features.addedit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.todolistcompose.R
import br.com.todolistcompose.ui.theme.ToDoListComposeTheme

@Composable
fun AddEditScreen() {
    AddEditContent(onCompleted = {})
}

@Composable
fun AddEditContent(onCompleted: () -> Unit) {
    Scaffold(
        modifier = Modifier,
        floatingActionButton = {
            CompletedFab(onCompleted = onCompleted)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .consumeWindowInsets(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = "",
                onValueChange = {},
                placeholder = {
                    Text(text = stringResource(R.string.title_place_holder))
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = "",
                onValueChange = {},
                placeholder = {
                    Text(text = stringResource(R.string.description_place_holder))
                }
            )
        }
    }
}

@Composable
private fun CompletedFab(
    onCompleted: () -> Unit
) {
    FloatingActionButton(onClick = onCompleted) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = stringResource(R.string.fab_completed_description)
        )
    }
}

@Preview
@Composable
fun Preview() {
    ToDoListComposeTheme {
        AddEditContent(
            onCompleted = {}
        )
    }
}