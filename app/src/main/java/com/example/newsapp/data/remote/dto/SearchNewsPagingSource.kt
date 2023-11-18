package com.example.newsapp.data.remote.dto

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsapp.domain.model.Article

// A PagingSource for searching news based on a query and sources
class SearchNewsPagingSource(
    private val newsApi: NewsApi, // The News API service for making network calls
    private val searchQuery: String, // The search query for news
    private val sources: String // The source for news filtering
) : PagingSource<Int, Article>() {

    // Function to provide a key for refreshing the data
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPage ->
            val page = state.closestPageToPosition(anchorPage)
            page?.nextKey?.minus(1) ?: page?.prevKey?.plus(1)
        }
    }

    // Variable to keep track of the total count of news articles loaded
    private var totalNewsCount = 0

    // Function to load data for a given page
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: 1  // Default to page 1 if not special page is provided
        return try {
            // Make a network call to search for news
            val newsResponse =
                newsApi.searchNews(searchQuery = searchQuery, sources = sources, page = page)

            // Update the total count of news articles
            totalNewsCount += newsResponse.articles.size

            // Remove duplicate articles based on their titles
            val articles = newsResponse.articles.distinctBy { it.title } // Remove duplicates

            // Return a LoadResult.Page with the loaded data, next page key, and null prevKey for forward paging
            LoadResult.Page(
                data = articles,
                nextKey = if (totalNewsCount == newsResponse.totalResults) null else page + 1,
                prevKey = null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            // Return an error result if an exception occurs during the API call
            LoadResult.Error(throwable = e)
        }
    }


}