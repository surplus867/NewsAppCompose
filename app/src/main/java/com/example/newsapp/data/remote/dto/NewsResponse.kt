package com.example.newsapp.data.remote.dto

import com.example.newsapp.domain.model.Article

// Data class representing the response from a remote news API
data class NewsResponse(
    val articles: List<Article>, // List of articles received in the response
    val status: String, // Status of the API response
    val totalResults:  Int // Total number of results available from the API
)