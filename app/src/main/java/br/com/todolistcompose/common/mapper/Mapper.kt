package br.com.todolistcompose.common.mapper

interface Mapper<F, T> {
    fun mapFrom(from: F): T
}