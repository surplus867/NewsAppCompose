package com.example.newsapp.domain.manager

import kotlinx.coroutines.flow.Flow

// Interface defining the contract for managing local user data related to the app
// entry status. Implementations of this interface handle asynchronous and reactive
// data operations using Kotlin coroutines and flows.
// This interface is designed to be implemented by classes responsible for
// storing and retrieving user-related preferences locally.
interface LocalUserManager {

    // Suspend function to asynchronously save the app entry status.
    suspend fun saveAppEntry()

    // Function to asynchronously read the app entry status as Flow of Boolean
    // The Flow allows for reactive updates when the app entry status changes.
    fun readAppEntry() : Flow<Boolean>
}