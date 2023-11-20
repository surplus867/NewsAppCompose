package com.example.newsapp.presentation.details.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.manager.usecases.news.NewsUseCases
import com.example.newsapp.domain.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

// Dagger Hilt ViewModel for the Details screen
@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases
) : ViewModel() {

    // Mutable state to represent a side effect message (e.g., success or error message)
    var sideEffect by mutableStateOf<String?>(null)
        private set

    // Function to handle events triggered from the Details screen
    fun onEvent(event: DetailsEvent) {
        when (event) {
            is DetailsEvent.UpsertDeleteArticle -> {
                viewModelScope.launch {
                    // Retrieve the article from the database based on its URL
                    val article = newsUseCases.selectArticle(event.article.url)
                    if (article == null) {
                        // If the article does not exist, upsert it
                        upsertArticle(event.article)
                    } else {
                        // If the article exists, delete it
                        deleteArticle(event.article)
                    }
                }
            }
            // Handling the event of removing a side effect message
            is DetailsEvent.RemoveSideEffect -> {
                sideEffect = null
            }
        }
    }

    //Coroutine function to delete an article and update the side effect message
    private suspend fun deleteArticle(article: Article) {
        newsUseCases.deleteArticle(article = article)
        sideEffect = "Article Deleted"
    }

    // Coroutine function to upsert an article and update the side effect message
    private suspend fun upsertArticle(article: Article) {
        newsUseCases.upsertArticle(article = article)
        sideEffect = "Article Saved"
    }
}