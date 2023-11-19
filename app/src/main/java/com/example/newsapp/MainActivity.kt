package com.example.newsapp

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.example.newsapp.data.local.NewsDao
import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.model.Source
import com.example.newsapp.presentation.navGraph.NavGraph
import com.example.newsapp.ui.theme.NewsAppTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

// Annotation indicating that Hilt should generate the necessary Dagger components
// for dependency injection in this Android component.
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set system window decorations
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Log message to indicate Room database initialization
        Log.d(TAG, "Initializing Room database")

        // Install the splash screen
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.splashCondition
            }
        }

        // set the content of the activity using Jetpack Compose
        setContent {
            NewsAppTheme {

                // Check if the system is in dark mode
                val isSystemInDarkMode = isSystemInDarkTheme()

                // Create a controller to manage the system UI appearance
                val systemController = rememberSystemUiController()

                // Apply a SideEffect to set the system bars color when the composition is applied
                // The 'SideEffect' block is used correctly to set the system bars color
                SideEffect {
                    systemController.setSystemBarsColor(
                        color = Color.Transparent,
                        darkIcons = !isSystemInDarkMode
                    )
                }

                // Create a Compose Box with a background color
                Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.background)) {

                    // Get the start destination for the navigation graph from the ViewModel
                    val startDestination = viewModel.startDestination

                    // Display the navigation graph (defined in the NavGraph composable)
                    NavGraph(startDestination = startDestination)
                }
            }
        }
    }
}