package br.com.todolistcompose.ui.features.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.todolistcompose.domain.repository.TodoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class ListViewModel(
    private val repository: TodoRepository
) : ViewModel() {

    val todos = repository.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}