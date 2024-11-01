package br.com.todolistcompose.common.di

import org.koin.core.module.Module

interface KoinModule {
    val module: Lazy<Module>
}