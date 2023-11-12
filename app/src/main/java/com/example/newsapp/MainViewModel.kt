package com.example.newsapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.usercases.app_entry.AppEntryUseCases
import com.example.newsapp.presentation.navGraph.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appEntryUseCases: AppEntryUseCases
) : ViewModel() {

    // MutableState variable represent the condition for showing the splash screen
    // A boolean variable that represents the condition for showing a splash screen. It is initially set to 'true'
     var splashCondition by mutableStateOf(true)
         private set

    // MutableState variable for the starting destination of navigation
    // A variable representing the start destination for navigation within the app. It is initially set to the route for the app start navigation.
     var startDestination by mutableStateOf(Route.AppStartNavigation.route)
         private set

    // Int the 'init' block, there's a all to 'readEntry()' from 'AppEntryUseCases' using a Kotlin Flow,

    init {
        // Using a Kotlin Flow to observe changes in app entry conditions
        // 'onEach' is to used to observe changes emitted by the flow
        // It updates 'startDestination' based on the value emitted by the flow
        // Determine whether the app should start from the home screen or the app start screen,
        appEntryUseCases.readAppEntry().onEach { shouldStartFromHomeScreen ->
            // Determine the starting destination based on the value emitted by the flow
            startDestination = if (shouldStartFromHomeScreen) {
                Route.NewsNavigation.route
            } else {
                Route.AppStartNavigation.route
            }
            // Introduce a delay of 300 milliseconds
            delay(300)
            // Update the splashConditions to indicate that the splash screen should not be displayed
            // 'splashCondition' is set to 'false', indicating the splash screen should no longer be displayed.
            splashCondition = false
        }.launchIn(viewModelScope)
    }
}