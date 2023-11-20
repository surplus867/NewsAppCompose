package com.example.newsapp.domain.repository

import androidx.paging.PagingData
import com.example.newsapp.domain.model.Article
import kotlinx.coroutines.flow.Flow

// Repository interface for accessing news data
interface NewsRepository {

    // Paging is a technique that enables to fetch data by small chunks from the server
    // Function to get news articles in a paginated flow
    fun getNews(sources: List<String>): Flow<PagingData<Article>>

    // Function to search for news articles based on a query and sources
    fun searchNews(searchQuery: String, sources: List<String>): Flow<PagingData<Article>>

    // Coroutine function to insert or update an article in the database
    suspend fun upsertArticle(article: Article)

    // Coroutine function to delete an article from the database
    suspend fun deleteArticle(article: Article)

    // Function to observe a flow of lists of articles
    fun selectArticles(): Flow<List<Article>>

    // Coroutine function to select a single article based on its URL
    // Returns null if the article is not found
    suspend fun selectArticle(url: String): Article?

}