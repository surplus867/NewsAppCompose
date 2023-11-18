package com.example.newsapp.presentation.details.components

// Sealed class to represent different events related to details screen
sealed class DetailsEvent {
    // Object representing in the event of saving an article
    object SaveArticle: DetailsEvent()
}