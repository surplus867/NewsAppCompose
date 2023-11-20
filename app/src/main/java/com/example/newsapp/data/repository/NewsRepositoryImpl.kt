package com.example.newsapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.newsapp.data.local.NewsDao
import com.example.newsapp.data.remote.dto.NewsApi
import com.example.newsapp.data.remote.dto.NewsPagingSource
import com.example.newsapp.data.remote.dto.SearchNewsPagingSource
import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

// Implementation of NewsRepository interface
class NewsRepositoryImpl(
    private val newsApi: NewsApi, // Retrofit service for fetching news from the network
    private val newsDao: NewsDao
) : NewsRepository {

    // Function to get news articles in a paginated flow
    override fun getNews(sources: List<String>): Flow<PagingData<Article>> {
        // Use Pager from Paging library to create a flow of paginated data
        return Pager(
            config = PagingConfig(pageSize = 10), // Configure the size of each page,
            // In this case, each page will contain 10 items
            pagingSourceFactory = {
                // Provide a new instance of NewsPagingSource for each page
                NewsPagingSource(
                    newsApi = newsApi,
                    sources = sources.joinToString(separator = ",")
                )
            }
        ).flow
        // The 'flow' property of the Pager is accessed to obtain a 'Flow' of 'pagingData<Article>'.
    }

    override fun searchNews(searchQuery: String, sources: List<String>): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 10), // Configure the size of each page,
            // In this case, each page will contain 10 items
            pagingSourceFactory = {
                // Provide a new instance of SearchNewsPagingSource for each page
                SearchNewsPagingSource(
                    searchQuery = searchQuery,
                    newsApi = newsApi,
                    sources = sources.joinToString(separator = ",")
                )
            }
        ).flow
        // The 'flow' property of the Pager is accessed to obtain a 'Flow' of 'pagingData<Articles>'.
    }

    // Function to insert or update an article in the local database
    override suspend fun upsertArticle(article: Article) {
        newsDao.upsert(article)
    }

    // Function to delete an article from the local database
    override suspend fun deleteArticle(article: Article) {
        newsDao.delete(article)
    }

    // Function to get a flow of lists of articles from the local database
    override fun selectArticles(): Flow<List<Article>> {
        return newsDao.getArticles()
    }

    // Function to select a single article from the local database based on its URL
    override suspend fun selectArticle(url: String): Article? {
        return newsDao.getArticle(url)
    }
}