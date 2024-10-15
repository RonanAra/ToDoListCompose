package br.com.todolistcompose.domain.repository

import br.com.todolistcompose.domain.entity.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    suspend fun insert(
        title: String,
        description: String?,
        id: Long?
    )
    suspend fun updateCompleted(
        id: Long,
        isCompleted: Boolean
    )
    suspend fun delete(id: Long)
    fun getAll(): Flow<List<Todo>>
    suspend fun getById(id: Long): Todo?
}