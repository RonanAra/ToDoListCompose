package br.com.todolistcompose.presentation.features.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.todolistcompose.domain.repository.TodoRepository
import br.com.todolistcompose.presentation.navigation.Navigator
import br.com.todolistcompose.presentation.navigation.TodoDestinations.AddEditRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ListViewModel(
    private val navigator: Navigator,
    private val repository: TodoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ListState>(ListState.Loading)
    val uiState: StateFlow<ListState> = _uiState
        .onStart { loadList() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ListState.Loading
        )

    fun onEvent(event: ListEvent) {
        when (event) {
            is ListEvent.AddEdit -> navigateToEdit(event.id)
            is ListEvent.CompleteChanged -> completeChanged(
                id = event.id,
                isCompleted = event.isCompleted
            )
            is ListEvent.Delete -> delete(event.id)
            ListEvent.FinishApp -> finishApp()
            is ListEvent.ShowCloseAppDialog -> showCloseAppDialog(event.enable)
        }
    }

    private fun showCloseAppDialog(enable: Boolean) {
        _uiState.update { state ->
            if (state is ListState.MainState) {
                state.copy(showCloseAppDialog = enable)
            } else {
                state
            }
        }
    }

    private fun loadList() {
        viewModelScope.launch {
            try {
                val list = repository.getAll().first()
                _uiState.update { ListState.MainState(list) }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun finishApp() {
        viewModelScope.launch {
            navigator.finish()
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