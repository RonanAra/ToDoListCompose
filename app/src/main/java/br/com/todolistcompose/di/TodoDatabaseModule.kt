package br.com.todolistcompose.di

import br.com.todolistcompose.common.di.KoinLazyModule
import br.com.todolistcompose.data.database.TodoDatabaseProvider
import org.koin.android.ext.koin.androidContext
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.lazyModule

@OptIn(KoinExperimentalAPI::class)
object TodoDatabaseModule: KoinLazyModule {
    override val module = lazyModule {
        single { TodoDatabaseProvider.provide(androidContext()).todoDao }
    }
}