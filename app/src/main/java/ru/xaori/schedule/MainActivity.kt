package ru.xaori.schedule

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.koin.compose.viewmodel.koinViewModel
import ru.xaori.schedule.presentation.state.AppUiState
import ru.xaori.schedule.presentation.navigation.Navigation
import ru.xaori.schedule.presentation.navigation.Screen
import ru.xaori.schedule.presentation.ui.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                val viewModel: AppViewModel = koinViewModel()
                val uiState by viewModel.uiState.collectAsState()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Surface(
                        modifier = Modifier.padding(
                            WindowInsets.safeDrawing.only(WindowInsetsSides.Top)
                                .asPaddingValues()
                        ),
                        color = Color.Transparent
                    ) {
                        when (uiState) {
                            is AppUiState.Loading -> {}
                            is AppUiState.Success -> {
                                Navigation(Screen.Schedule.route)
                            }

                            is AppUiState.NewUser -> {
                                Navigation(Screen.Start.route)
                            }
                        }
                    }
                }
            }
        }
    }
}
