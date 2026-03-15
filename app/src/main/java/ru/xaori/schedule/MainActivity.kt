package ru.xaori.schedule

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import ru.xaori.schedule.presentation.common.snackbar.SnackbarHostContainer
import ru.xaori.schedule.presentation.navigation.Navigation
import ru.xaori.schedule.presentation.ui.AppTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3ExpressiveApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AppTheme {

                val isDark = !MaterialTheme.colorScheme.background.luminance().let { it > 0.5f }

                SideEffect {
                    enableEdgeToEdge(
                        statusBarStyle = if (isDark)
                            SystemBarStyle.dark(android.graphics.Color.TRANSPARENT)
                        else
                            SystemBarStyle.light(
                                android.graphics.Color.TRANSPARENT,
                                android.graphics.Color.TRANSPARENT
                            ),
                        navigationBarStyle = if (isDark)
                            SystemBarStyle.dark(android.graphics.Color.TRANSPARENT)
                        else
                            SystemBarStyle.light(
                                android.graphics.Color.TRANSPARENT,
                                android.graphics.Color.TRANSPARENT
                            )
                    )
                }

                SnackbarHostContainer {
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
                            Navigation()
                        }
                    }
                }
            }
        }
    }
}
