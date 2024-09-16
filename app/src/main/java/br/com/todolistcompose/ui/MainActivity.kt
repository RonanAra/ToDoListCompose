package br.com.todolistcompose.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import br.com.todolistcompose.ui.navigation.TodoNavHost
import br.com.todolistcompose.ui.theme.ToDoListComposeTheme

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