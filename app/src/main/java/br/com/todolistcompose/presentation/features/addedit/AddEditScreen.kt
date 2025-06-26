package br.com.todolistcompose.presentation.features.addedit

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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.todolistcompose.R
import br.com.todolistcompose.presentation.UiEvent
import br.com.todolistcompose.presentation.theme.ToDoListComposeTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddEditScreen(
    viewModel: AddEditViewModel = koinViewModel()
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UiEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(message = uiEvent.message)
                }
            }
        }
    }

    AddEditContent(
        title = uiState.title,
        description = uiState.description,
        onEvent = viewModel::onEvent,
        snackBarHostState = snackBarHostState
    )
}

@Composable
fun AddEditContent(
    title: String,
    description: String?,
    onEvent: (AddEditEvent) -> Unit,
    snackBarHostState: SnackbarHostState
) {
    Scaffold(
        modifier = Modifier,
        floatingActionButton = {
            CompletedFab(
                onCompleted = {
                    onEvent(AddEditEvent.Save)
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .consumeWindowInsets(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = title,
                onValueChange = { title ->
                    onEvent(AddEditEvent.TitleChanged(title))
                },
                placeholder = {
                    Text(text = stringResource(R.string.title_place_holder))
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = description.orEmpty(),
                onValueChange = { desc ->
                    onEvent(AddEditEvent.DescriptionChanged(desc))
                },
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
            title = "",
            description = null,
            onEvent = {},
            snackBarHostState = SnackbarHostState()
        )
    }
}