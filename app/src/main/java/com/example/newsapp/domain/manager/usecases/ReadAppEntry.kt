package com.example.newsapp.domain.manager.usecases

import com.example.newsapp.domain.manager.LocalUserManager
import kotlinx.coroutines.flow.Flow

class ReadAppEntry(
    private val localUserManager: LocalUserManager
) {

    // The invoke function is defined, allowing instances of this class to be called like a function
    // The 'invoke' function is defined with the 'operator' keyword, allowing instances of this class to be called like a function
    // Inside the 'invoke' function, it returns a 'Flow<Boolean>'. The 'Flow' is Kotlin coroutine construct used for handing asynchronous streams of values
    // In this case, it represents a flow of boolean values.
     operator fun invoke(): Flow<Boolean> {
        // The invoke function returns a Flow<Boolean> obtained from the localUserManager
        // This use case is likely used to determine the starting destination for your app's navigation flow based on the result obtained from 'localUserManager.readAppEntry()'
       return localUserManager.readAppEntry()
    }
}