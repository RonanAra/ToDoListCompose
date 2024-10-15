package br.com.todolistcompose.data.repository

import br.com.todolistcompose.data.database.dao.TodoDao
import br.com.todolistcompose.data.database.entity.TodoEntity
import br.com.todolistcompose.domain.entity.Todo
import br.com.todolistcompose.domain.mapper.TodoEntityToTodoMapper
import br.com.todolistcompose.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TodoRepositoryImpl(
    private val dao: TodoDao
) : TodoRepository {
    override suspend fun insert(
        title: String,
        description: String?,
        id: Long?
    ) {
        val entity = id?.let {
            dao.getById(it)?.copy(
                title = title,
                description = description
            )
        } ?: TodoEntity(
            title = title,
            description = description,
            isCompleted = false
        )
        dao.insert(entity)
    }

    override suspend fun updateCompleted(
        id: Long,
        isCompleted: Boolean
    ) {
        val existingEntity = dao.getById(id) ?: return
        val updateEntity = existingEntity.copy(isCompleted = isCompleted)
        dao.insert(updateEntity)
    }

    override suspend fun delete(id: Long) {
        val existingEntity = dao.getById(id) ?: return
        dao.delete(existingEntity)
    }

    override fun getAll(): Flow<List<Todo>> {
        return dao.getAll().map { entities ->
            entities.map { entity ->
                TodoEntityToTodoMapper.mapFrom(entity)
            }
        }
    }

    override suspend fun getById(id: Long): Todo? {
        return dao.getById(id)?.let { entity ->
            TodoEntityToTodoMapper.mapFrom(entity)
        }
    }
}