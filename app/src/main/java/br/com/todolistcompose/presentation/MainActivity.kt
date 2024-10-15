package br.com.todolistcompose.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import br.com.todolistcompose.presentation.navigation.TodoNavHost
import br.com.todolistcompose.presentation.theme.ToDoListComposeTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoListComposeTheme {
                TodoNavHost()
            }
        }
    }
}