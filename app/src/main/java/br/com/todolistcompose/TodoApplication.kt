package br.com.todolistcompose

import android.app.Application
import br.com.todolistcompose.di.TodoDatabaseModule
import br.com.todolistcompose.di.TodoModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TodoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TodoApplication)
            modules(
                TodoModule.module,
                TodoDatabaseModule.module
            )
        }
    }
}