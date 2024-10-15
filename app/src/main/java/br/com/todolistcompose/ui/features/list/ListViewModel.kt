package br.com.todolistcompose.ui.features.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.todolistcompose.domain.repository.TodoRepository
import br.com.todolistcompose.ui.UiEvent
import br.com.todolistcompose.ui.navigation.TodoDestinations.AddEditRoute
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ListViewModel(
    private val repository: TodoRepository
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    val todos = repository.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun onEvent(event: ListEvent) {
        when (event) {
            is ListEvent.AddEdit -> navigateToEdit(event.id)
            is ListEvent.CompleteChanged -> completeChanged(
                id = event.id,
                isCompleted = event.isCompleted
            )
            is ListEvent.Delete -> delete(event.id)
        }
    }

    private fun navigateToEdit(id: Long?) {
        viewModelScope.launch {
            _uiEvent.send(UiEvent.Navigate(AddEditRoute(id)))
        }
    }

    private fun delete(id: Long) {
        viewModelScope.launch {
            repository.delete(id)
        }
    }

    private fun completeChanged(
        id: Long,
        isCompleted: Boolean
    ) {
        viewModelScope.launch {
            repository.updateCompleted(
                id = id,
                isCompleted = isCompleted
            )
        }
    }
}