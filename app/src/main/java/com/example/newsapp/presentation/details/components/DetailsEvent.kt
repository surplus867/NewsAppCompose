package com.example.newsapp.presentation.details.components

import com.example.newsapp.domain.model.Article

// Sealed class to represent different events related to details screen
sealed class DetailsEvent {
    // Object representing in the event of saving an article
    data class UpsertDeleteArticle(val article: Article) : DetailsEvent()

    // Object representing the event of removing a side effect message
    data object RemoveSideEffect : DetailsEvent()

}