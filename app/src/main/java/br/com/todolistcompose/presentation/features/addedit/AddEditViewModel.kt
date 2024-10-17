package br.com.todolistcompose.presentation.features.addedit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.todolistcompose.domain.repository.TodoRepository
import br.com.todolistcompose.presentation.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddEditViewModel(
    private val id: Long?,
    private val repository: TodoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddEditUiState())
    val uiState: StateFlow<AddEditUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        id?.let {
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

    fun onEvent(event: AddEditEvent) {
        when (event) {
            is AddEditEvent.DescriptionChanged -> descriptionChanged(event.description)
            is AddEditEvent.TitleChanged -> titleChanged(event.title)
            AddEditEvent.Save -> saveTodo()
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
                _uiEvent.send(UiEvent.NavigateBack)
            }
        }
    }
}