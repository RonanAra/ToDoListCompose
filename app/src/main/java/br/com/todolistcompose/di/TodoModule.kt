package br.com.todolistcompose.di

import br.com.todolistcompose.common.di.KoinModule
import br.com.todolistcompose.presentation.features.addedit.AddEditViewModel
import br.com.todolistcompose.presentation.features.list.ListViewModel
import br.com.todolistcompose.data.repository.TodoRepositoryImpl
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

object TodoModule : KoinModule {
    override val module: Module = module {
        singleOf(::TodoRepositoryImpl)

        viewModelOf(::ListViewModel)
        viewModelOf(::AddEditViewModel)
    }
}