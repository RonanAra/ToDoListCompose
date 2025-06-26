package br.com.todolistcompose.di

import br.com.todolistcompose.common.di.KoinModule
import br.com.todolistcompose.data.datasource.TodoDataSource
import br.com.todolistcompose.data.datasource.TodoDataSourceImpl
import br.com.todolistcompose.data.repository.TodoRepositoryImpl
import br.com.todolistcompose.domain.repository.TodoRepository
import br.com.todolistcompose.presentation.features.addedit.AddEditViewModel
import br.com.todolistcompose.presentation.features.list.ListViewModel
import br.com.todolistcompose.presentation.navigation.DefaultNavigator
import br.com.todolistcompose.presentation.navigation.Navigator
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

object TodoModule : KoinModule {

    override val module = module {
        singleOf(::TodoRepositoryImpl) bind TodoRepository::class
        singleOf(::TodoDataSourceImpl) bind TodoDataSource::class
        singleOf(::DefaultNavigator) bind Navigator::class

        viewModelOf(::ListViewModel)
        viewModel { (id: Long?) -> AddEditViewModel(id, get(), get()) }
    }
}