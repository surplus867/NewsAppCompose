package com.example.newsapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.example.newsapp.domain.manager.usecases.AppEntryUseCases
import com.example.newsapp.presentation.onboarding.components.OnBoardingScreen
import com.example.newsapp.ui.theme.NewsAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

// Annotation indicating that Hilt should generate the necessary Dagger components
// for dependency injection in this Android component.
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // Field to be injected with the AppEntryUseCases instance.
    // The @Inject annotation informs Hilt to provide this dependency.
    @Inject
    lateinit var appEntryUseCases: AppEntryUseCases
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set system window decorations
        WindowCompat.setDecorFitsSystemWindows(window,false)

        // Install the splash screen
        installSplashScreen()

        // Asynchronously read the app entry status using AppEntryUseCases
        lifecycleScope.launch {
            // Collect the Flow emitted by readAppEntry
            appEntryUseCases.readAppEntry().collect {
                // Log the app entry status for testing purposes
                Log.d("Test", it.toString())
            }
        }
        // set the content of the activity using Jetpack Compose
        setContent {
            NewsAppTheme {
                // Create a Compose Box with a background color
                Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.background)) {
                    // Display the onBoardingScreen within the Box
                    OnBoardingScreen()
                }
            }
        }
    }
}