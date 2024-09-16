package br.com.todolistcompose.domain.entity

data class Todo(
    val id: Long,
    val title: String,
    val description: String?,
    val isCompleted: Boolean
)

// Fake Objects
val todo1 = Todo(
    id = 1,
    title = "Minha primeira tarefa",
    description = "Fazer compras",
    isCompleted = false
)

val todo2 = Todo(
    id = 1,
    title = "Minha primeira tarefa",
    description = "Fazer compras",
    isCompleted = true
)