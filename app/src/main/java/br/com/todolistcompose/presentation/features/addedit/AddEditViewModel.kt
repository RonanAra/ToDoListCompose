package br.com.todolistcompose.presentation.features.addedit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.todolistcompose.domain.repository.TodoRepository
import br.com.todolistcompose.presentation.UiEvent
import br.com.todolistcompose.presentation.navigation.Navigator
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddEditViewModel(
    private val id: Long?,
    private val navigator: Navigator,
    private val repository: TodoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddEditUiState())
    val uiState: StateFlow<AddEditUiState> = _uiState
        .onStart { getById() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = AddEditUiState()
        )

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: AddEditEvent) {
        when (event) {
            is AddEditEvent.DescriptionChanged -> descriptionChanged(event.description)
            is AddEditEvent.TitleChanged -> titleChanged(event.title)
            AddEditEvent.Save -> saveTodo()
        }
    }

    private fun getById() {
        if (id == null) return
        id.let {
            viewModelScope.launch {
                val todo = repository.getById(it)
                _uiState.update { state ->
                    state.copy(
                        title = todo?.title.orEmpty(),
                        description = todo?.description.orEmpty()
                    )
                }
            }
        }
    }

    private fun descriptionChanged(text: String) {
        _uiState.update { state ->
            state.copy(description = text)
        }
    }

    private fun titleChanged(text: String) {
        _uiState.update { state ->
            state.copy(title = text)
        }
    }

    private fun saveTodo() {
        viewModelScope.launch {
            with(_uiState.value) {
                if (title.isBlank()) {
                    _uiEvent.send(UiEvent.ShowSnackBar("Title cannot be empty"))
                    return@launch
                }
                repository.insert(
                    title = title,
                    description = description,
                    id = id
                )
                navigator.navigateUp()
            }
        }
    }
}