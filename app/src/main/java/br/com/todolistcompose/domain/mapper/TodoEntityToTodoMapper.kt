package br.com.todolistcompose.domain.mapper

import br.com.todolistcompose.common.mapper.Mapper
import br.com.todolistcompose.data.database.entity.TodoEntity
import br.com.todolistcompose.domain.entity.Todo

object TodoEntityToTodoMapper : Mapper<TodoEntity, Todo> {
    override fun mapFrom(from: TodoEntity): Todo {
        with(from) {
            return Todo(
                id = id,
                title = title,
                description = description,
                isCompleted = isCompleted
            )
        }
    }
}

object EntityToTodoListMapper : Mapper<List<TodoEntity>, List<Todo>> {
    override fun mapFrom(from: List<TodoEntity>): List<Todo> {
        return from.map { entity ->
            with(entity) {
                Todo(
                    id = id,
                    title = title,
                    description = description,
                    isCompleted = isCompleted
                )
            }
        }
    }
}