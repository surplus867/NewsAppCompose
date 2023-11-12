package com.example.newsapp.domain.usercases.app_entry

// Data class representing a container for app entry-related use cases.
data class AppEntryUseCases(
    // USe case for reading the app entry status.
    val readAppEntry: ReadAppEntry,

    // Use case for saving the app entry status.
    val saveAppEntry: SaveAppEntry
)
