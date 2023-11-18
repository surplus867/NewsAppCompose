package com.example.newsapp.presentation.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.newsapp.presentation.navGraph.Route
import com.example.newsapp.presentation.onboarding.Dimens.MediumPadding1
import com.example.newsapp.presentation.onboarding.common.ArticlesList
import com.example.newsapp.presentation.onboarding.common.SearchBar

// Composable function for the search screen
@Composable
fun SearchScreen(
    state: SearchState, // The state of the search screen
    event: (SearchEvent) -> Unit, // Callback to handle search events
    navigate: (String) -> Unit // Callback to navigate to other screens
) {
    Column(
        modifier = Modifier
            .padding(
                top = MediumPadding1,
                start = MediumPadding1,
                end = MediumPadding1
            )
            .statusBarsPadding()
            .fillMaxSize()
    ) {
        // Search bar for inputting search queries
        SearchBar(
            text = state.searchQuery,
            readOnly = false,
            onValueChange = { event(SearchEvent.UpdateSearchQuery(it)) },
            onSearch = { event(SearchEvent.SearchNews) })

        // Spacer to add some space between the search bar and the articles list
        Spacer(modifier = Modifier.height(MediumPadding1))

        // Display the list of articles
        state.articles?.let {
            val articles = it.collectAsLazyPagingItems()
            ArticlesList(articles = articles, onClick = { navigate(Route.DetailsScreen.route) })
        }
    }
}