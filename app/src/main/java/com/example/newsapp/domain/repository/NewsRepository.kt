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
}