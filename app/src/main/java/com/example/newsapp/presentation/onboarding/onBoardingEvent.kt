package com.example.newsapp.presentation.onboarding

// Sealed clas to represent different events in the onboarding process
sealed class OnBoardingEvent {

    // Object representing the event of saving the app entry
    object SaveAppEntry: OnBoardingEvent()
}