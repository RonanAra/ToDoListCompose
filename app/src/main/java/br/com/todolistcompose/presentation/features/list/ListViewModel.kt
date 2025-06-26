package br.com.todolistcompose.presentation.features.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.todolistcompose.domain.repository.TodoRepository
import br.com.todolistcompose.presentation.navigation.Navigator
import br.com.todolistcompose.presentation.navigation.TodoDestinations.AddEditRoute
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ListViewModel(
    private val navigator: Navigator,
    private val repository: TodoRepository
) : ViewModel() {

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
            navigator.navigate(AddEditRoute(id))
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