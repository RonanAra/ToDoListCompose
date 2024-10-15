package br.com.todolistcompose.ui.features.addedit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.todolistcompose.domain.repository.TodoRepository
import br.com.todolistcompose.ui.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class AddEditViewModel(
    private val id: Long?,
    private val repository: TodoRepository
) : ViewModel() {

    var title by mutableStateOf("")
        private set

    var description by mutableStateOf<String?>(null)
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        id?.let {
            viewModelScope.launch {
                val todo = repository.getById(it)
                title = todo?.title.orEmpty()
                description = todo?.description
            }
        }
    }

    fun onEvent(event: AddEditEvent) {
        when (event) {
            is AddEditEvent.DescriptionChanged -> description = event.description
            is AddEditEvent.TitleChanged -> title = event.title
            AddEditEvent.Save -> saveTodo()
        }
    }

    private fun saveTodo() {
        viewModelScope.launch {
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