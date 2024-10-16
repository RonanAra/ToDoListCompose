package br.com.todolistcompose.di

import br.com.todolistcompose.common.di.KoinModule
import br.com.todolistcompose.data.repository.TodoRepositoryImpl
import br.com.todolistcompose.domain.repository.TodoRepository
import br.com.todolistcompose.data.datasource.TodoDataSource
import br.com.todolistcompose.data.datasource.TodoDataSourceImpl
import br.com.todolistcompose.presentation.features.addedit.AddEditViewModel
import br.com.todolistcompose.presentation.features.list.ListViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

object TodoModule : KoinModule {
    override val module: Module = module {
        singleOf(::TodoRepositoryImpl) { bind<TodoRepository>() }
        singleOf(::TodoDataSourceImpl) { bind<TodoDataSource>() }

        viewModelOf(::ListViewModel)
        viewModelOf(::AddEditViewModel)
    }
}