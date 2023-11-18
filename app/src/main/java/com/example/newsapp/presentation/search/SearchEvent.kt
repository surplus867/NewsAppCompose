package com.example.newsapp.presentation.search

// Sealed class representing events related to search functionally
sealed class SearchEvent {

    // Data class to represent the event of updating the search query
    data class UpdateSearchQuery(val searchQuery: String): SearchEvent()

    // Object to represent the event of initiating a search for news
    object SearchNews: SearchEvent()
}