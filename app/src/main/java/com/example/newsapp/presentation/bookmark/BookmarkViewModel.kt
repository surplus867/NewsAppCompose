package com.example.newsapp.presentation.bookmark

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.manager.usecases.news.NewsUseCases
import com.example.newsapp.presentation.BookmarkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases
) : ViewModel() {

    // Mutable state to hold the current state of the bookmark feature
    private val _state = mutableStateOf(BookmarkState())

    // Exposed read-only state for the UI to observe
    val state: State<BookmarkState> = _state

    // Initialization block to fetch bookmarked articles when ViewModel is created
    init {
        getArticles()
    }

    // Function ot fetch bookmarked articles using the NewsUseCases
    private fun getArticles() {
        // Use a coroutine flow to receive updates when articles are selected
        newsUseCases.selectArticles().onEach { articles ->
            // Update the state by copying the existing state and reversing the order of the articles
            _state.value = _state.value.copy(articles = articles.asReversed())
        }.launchIn(viewModelScope)// Launch the flow in the viewModelScope to handle coroutine lifecycles
    }
}