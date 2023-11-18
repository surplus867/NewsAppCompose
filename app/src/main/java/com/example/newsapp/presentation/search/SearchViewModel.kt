package com.example.newsapp.presentation.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.newsapp.domain.manager.usecases.news.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

// HiltViewModel annotation for Dagger Hilt dependency injection
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases // Injected dependency for news use cases
) : ViewModel() {

    // Mutable state for representing the state of the search screen
    private val _state = mutableStateOf(SearchState())
    val state: State<SearchState> = _state

    // Function to handle event triggered by the UI
    fun onEvent(event: SearchEvent) {
        when (event) {
            // Event to update the search query
            is SearchEvent.UpdateSearchQuery -> {
                _state.value = state.value.copy(searchQuery = event.searchQuery)

            }
            // Event to initiate a new search
            is SearchEvent.SearchNews -> {
                searchNews()
            }
        }
    }

    // Function to search for news based on the current search query
    private fun searchNews() {
        // Use the news use case to fetch paginated news data
        val articles = newsUseCases.searchNews(
            searchQuery = state.value.searchQuery,
            sources = listOf("bbc-news", "abc-news", "al-jazeera-english")
        ).cachedIn(viewModelScope)

        // Update the state with the new articles using the cachedIn operator
        _state.value = state.value.copy(articles = articles)
    }
}