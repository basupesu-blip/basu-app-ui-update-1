package com.yourbrand.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.yourbrand.todolist.navigation.AppNavGraph
import com.yourbrand.todolist.ui.theme.ToDoListAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val app = application as TodoApplication

        setContent {
            ToDoListAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppNavGraph(app = app)
                }
            }
        }
    }
}
