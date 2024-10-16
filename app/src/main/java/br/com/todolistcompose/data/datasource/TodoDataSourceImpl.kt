package br.com.todolistcompose.data.datasource

import br.com.todolistcompose.data.database.dao.TodoDao
import br.com.todolistcompose.data.database.entity.TodoEntity
import kotlinx.coroutines.flow.Flow

interface TodoDataSource {
    suspend fun insert(entity: TodoEntity)
    suspend fun delete(entity: TodoEntity)
    fun getAll(): Flow<List<TodoEntity>>
    suspend fun getById(id: Long): TodoEntity?
}

class TodoDataSourceImpl(
    private val dao: TodoDao
): TodoDataSource {

    override suspend fun insert(entity: TodoEntity) = dao.insert(entity)

    override suspend fun delete(entity: TodoEntity) = dao.delete(entity)

    override fun getAll(): Flow<List<TodoEntity>> = dao.getAll()

    override suspend fun getById(id: Long): TodoEntity? = dao.getById(id)
}