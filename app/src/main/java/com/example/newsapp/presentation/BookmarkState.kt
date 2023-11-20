package com.example.newsapp.presentation

import com.example.newsapp.domain.model.Article

data class BookmarkState(
    val articles: List<Article> = emptyList()
) {
}