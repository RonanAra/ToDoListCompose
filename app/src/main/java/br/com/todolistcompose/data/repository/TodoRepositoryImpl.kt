package br.com.todolistcompose.data.repository

import br.com.todolistcompose.data.database.entity.TodoEntity
import br.com.todolistcompose.data.datasource.TodoDataSource
import br.com.todolistcompose.domain.entity.Todo
import br.com.todolistcompose.domain.mapper.TodoEntityToTodoMapper
import br.com.todolistcompose.domain.mapper.EntityToTodoListMapper
import br.com.todolistcompose.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TodoRepositoryImpl(
    private val datasource: TodoDataSource
) : TodoRepository {
    override suspend fun insert(
        title: String,
        description: String?,
        id: Long?
    ) {
        val entity = id?.let {
            datasource.getById(it)?.copy(
                title = title,
                description = description
            )
        } ?: TodoEntity(
            title = title,
            description = description,
            isCompleted = false
        )
        datasource.insert(entity)
    }

    override suspend fun updateCompleted(
        id: Long,
        isCompleted: Boolean
    ) {
        val existingEntity = datasource.getById(id) ?: return
        val updateEntity = existingEntity.copy(isCompleted = isCompleted)
        datasource.insert(updateEntity)
    }

    override suspend fun delete(id: Long) {
        val existingEntity = datasource.getById(id) ?: return
        datasource.delete(existingEntity)
    }

    override fun getAll(): Flow<List<Todo>> {
        return datasource.getAll().map {
            EntityToTodoListMapper.mapFrom(it)
        }
    }

    override suspend fun getById(id: Long): Todo? {
        return datasource.getById(id)?.let { entity ->
            TodoEntityToTodoMapper.mapFrom(entity)
        }
    }
}