package com.example.newsapp.domain.manager.usecases.news

import androidx.paging.PagingData
import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

// Use case class responsible for searching news
class SearchNews(
    private val newsRepository: NewsRepository // Repository to fetch news data
) {

    // Operator function to invoke the use case
    // 'invoke' Function Body:
    // Calls the 'searchNews' method on the injected 'newsRepository'
    // Passes the provided 'searchQuery' and 'sources' parameters to the repository's 'searchNews' method
    // Return a 'Flow' of 'PagingData<Article>', indicating that the result is expected to be paginated.
    operator fun invoke(searchQuery: String, sources: List<String>): Flow<PagingData<Article>> {
        // Delegates the call to the news repository to het the paginated news data
        return newsRepository.searchNews(searchQuery = searchQuery, sources = sources)
    }
}