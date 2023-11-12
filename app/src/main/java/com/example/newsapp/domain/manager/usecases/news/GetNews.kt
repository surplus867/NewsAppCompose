package com.example.newsapp.domain.manager.usecases.news

import androidx.paging.PagingData
import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

// Use case or interactor for fetching news articles
class GetNews(
    private val newsRepository: NewsRepository
) {

    // Operator function that returns a Flow of PagingData<Article>
    // The class defines an invoke function, which allows instances of 'GetNews' to be
    // Called as they were functions.
    operator fun invoke(sources: List<String>): Flow<PagingData<Article>> {
        // Delegate the actual work to the NewsRepository
        return newsRepository.getNews(sources = sources)
    }
}