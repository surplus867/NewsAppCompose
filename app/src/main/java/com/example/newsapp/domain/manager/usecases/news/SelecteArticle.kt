package com.example.newsapp.domain.manager.usecases.news

import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.repository.NewsRepository

class SelectArticle(
    private val newsRepository: NewsRepository
) {
    /**
     * This class represents a use case for selecting a special article by its URL.
     *
     * @param newsRepository The repository responsible for handling news-related data operations
     */

    /**
     * Suspending function that is called to select an article based on its URL.
     *
     * @param url the URL of the article to be selected.
     * @return The selected Article object, or null if the article is not founded.
     */
    suspend operator fun invoke(url: String): Article? {
        // Delegate the actual article selection operation to the news repository
        return newsRepository.selectArticle(url)
    }
}