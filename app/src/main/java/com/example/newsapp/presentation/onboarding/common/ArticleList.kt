package com.example.newsapp.presentation.onboarding.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.newsapp.domain.model.Article
import com.example.newsapp.presentation.onboarding.Dimens.ExtraSmallPadding2
import com.example.newsapp.presentation.onboarding.Dimens.MediumPadding1


@Composable
fun ArticlesList(
    modifier: Modifier = Modifier,
    articles: List<Article>,
    onClick: (Article) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(MediumPadding1),
        contentPadding = PaddingValues(all = ExtraSmallPadding2)
    ) {
        // Iterate through the articles and display each article using ArticleCard.
        items(count = articles.size) {
            val article = articles[it]
            ArticleCard(article = article, onClick = { onClick(article) })

        }
    }
}

/**
 * Composable for displaying a list of articles with paging support.
 *
 * @param modifier for the Composable.
 * @param articles LazyPagingItems representing the paginated list of articles.
 * @param onClick Callback when an article is clicked.
 */
@Composable
fun ArticlesList(
    modifier: Modifier = Modifier,
    articles: LazyPagingItems<Article>,
    onClick: (Article) -> Unit
) {
    // Check the paging result and decide whether to show loading, error, or the actual list.
    val handlePagingResult = handlePagingResult(articles = articles)
    if (handlePagingResult) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(MediumPadding1),
            contentPadding = PaddingValues(all = ExtraSmallPadding2)
        ) {
            // Iterate through the articles and display each article using ArticleCard.
            items(count = articles.itemCount) {
                articles[it]?.let { article ->
                    ArticleCard(article = article, onClick = { onClick(article) })

                }
            }
        }
    }
}

/**
 * Function to handle different paging states and determine whether to show loading, error, or the list.
 *
 * @param articles LazyPagingItems representing the paginated list of articles.
 * @return Boolean indicating whether to proceed with rendering the list or not.
 */
@Composable
fun handlePagingResult(
    articles: LazyPagingItems<Article>,
): Boolean {
    // Get the current load state and check for errors
    val loadState = articles.loadState
    val error = when {
        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
        else -> null
    }

    return when {
        // Show a loading indicator if data is in the process of being loaded.
        loadState.refresh is LoadState.Loading -> {
            ShimmerEffect()
            false
        }

        // Display an error screen if there is an error.
        error != null -> {
            EmptyScreen()
            false
        }

        // Proceed with rendering the list if there are no loading or error states.
        else -> {
            true
        }
    }
}

/**
 * Composable for displaying a shimmer effect during loading.
 */
@Composable
private fun ShimmerEffect() {
    Column(verticalArrangement = Arrangement.spacedBy(MediumPadding1)) {
        // Repeat the shimmer effect for a specified number of times.
        repeat(10) {
            ArticleCardShimmerEffect(
                modifier = Modifier.padding(horizontal = MediumPadding1)
            )
        }
    }
}