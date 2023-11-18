package com.example.newsapp.presentation.search

import androidx.paging.PagingData
import com.example.newsapp.domain.model.Article
import kotlinx.coroutines.flow.Flow

// Data class representing the state of the search screen
data class SearchState(
    val searchQuery: String = "", // The current search query entered by the user
    val articles: Flow<PagingData<Article>>? = null // Flow representing the paginated list of articles
) {
}