package com.example.newsapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import com.example.newsapp.domain.manager.usecases.AppEntryUseCases
import com.example.newsapp.presentation.onboarding.OnBoardingViewModel
import com.example.newsapp.presentation.onboarding.components.OnBoardingScreen
import com.example.newsapp.ui.theme.NewsAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

// Annotation indicating that Hilt should generate the necessary Dagger components
// for dependency injection in this Android component.
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // Injecting the AppEntryUseCases dependency using Hilt
    @Inject
    lateinit var useCases: AppEntryUseCases
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set system window decorations
        WindowCompat.setDecorFitsSystemWindows(window,false)

        // Install the splash screen
        installSplashScreen()

        // Coroutine scope for asynchronous tasks
        lifecycleScope.launch {
            // Reading the app entry using AppEntryUseCases and collecting the result
            useCases.readAppEntry().collect {
                Log.d("test", it.toString())
            }
        }
        // set the content of the activity using Jetpack Compose
        setContent {
            NewsAppTheme {
                // Create a Compose Box with a background color
                Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.background)) {
                    // Display the onBoardingScreen within the Box, obtaining the ViewModel using Hilt
                    val viewModel: OnBoardingViewModel  = hiltViewModel()
                    OnBoardingScreen(
                        event = viewModel:: onEvent
                    )
                }
            }
        }
    }
}