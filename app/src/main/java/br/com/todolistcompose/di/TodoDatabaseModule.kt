package br.com.todolistcompose.di

import br.com.todolistcompose.common.di.KoinModule
import br.com.todolistcompose.data.database.TodoDatabaseProvider
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object TodoDatabaseModule: KoinModule {
    override val module = module {
        single { TodoDatabaseProvider.provide(androidContext()).todoDao }
    }
}