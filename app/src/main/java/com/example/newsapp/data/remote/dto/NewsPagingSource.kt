package com.example.newsapp.data.remote.dto

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsapp.domain.model.Article
import kotlin.Exception

// Implementation of PagingSource for news articles
class NewsPagingSource(
    private val newsApi: NewsApi, // Retrofit service for fetching news
    private val sources: String // Source parameter for the news
) : PagingSource<Int, Article>() {

    private var totalNewsCount = 0 // Total count of news articles loaded so far

    // Provide a key for refreshing the list
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    // Load a page of news article
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        // Start from page 1 of params.key is null (initial load)
        val page = params.key ?: 1
        return try {
            // Fetch news articles from the network
            val newsResponse = newsApi.getNews(sources = sources, page = page)

            // Update the total count of new articles
            totalNewsCount += newsResponse.articles.size

            // Remove duplicates based on article title
            val articles = newsResponse.articles.distinctBy { it.title } // Remove Duplicates

            // Construct a LoadResult.Page with the loaded data, next key, and previous key set to null
            // If the total number of news article('totalNewsCount') is equal to the total number of
            // Results available ('newsResponse.totalResults'), set 'nextKey' to 'null'.
            // This means that there are no more pages to load
            // Otherwise set 'nextKey' to the next page number ('page + 1')
            LoadResult.Page(
                data = articles, // The list of loaded articles for the current page
                nextKey = if (totalNewsCount == newsResponse.totalResults) null else page + 1,
                prevKey = null // Since this is not a bidirectional paging source, preKey is set to null
            )
        } catch (e: Exception) {
            e.printStackTrace()

            // Construct a LoadResult.Error with the associated exception
            LoadResult.Error(
                throwable = e
            )
        }
    }
}

//In summary,
// this LoadResult.Page is used to wrap the loaded data (articles) along with information about the next page to be loaded. The decision about whether there's a next page depends on the comparison between the total loaded articles and the total available results.
//
//This result will be consumed by the PagingDataAdapter and used to update the displayed list in your UI.