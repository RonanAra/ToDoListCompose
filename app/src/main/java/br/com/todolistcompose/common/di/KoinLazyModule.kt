package br.com.todolistcompose.common.di

import org.koin.core.module.Module

interface KoinLazyModule {
    val module: Lazy<Module>
}

interface KoinModule {
    val module: Module
}