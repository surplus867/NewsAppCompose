package com.example.newsapp.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.usercases.app_entry.AppEntryUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

// Annotating with @HiltViewModel indicates that Hilt should provide dependency injection for this ViewModel
@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val appEntryUseCases: AppEntryUseCases
) : ViewModel() {

    // Function to handle different onboarding events
    fun onEvent(event: OnBoardingEvent) {
        when (event) {
            // If the event is to save the app entry, call the saveAppEntry function
            is OnBoardingEvent.SaveAppEntry -> {
                saveAppEntry()
            }
        }
    }

    // Coroutine function to save the app entry asynchronously
    private fun saveAppEntry() {
        // Launching a coroutine within the viewModelScope for structured concurrency
        viewModelScope.launch {
            // Calling the saveAppEntry function from the injected AppEntryUseCases
            appEntryUseCases.saveAppEntry()
        }
    }
}