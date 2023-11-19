package com.example.newsapp.presentation.navGraph

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.newsapp.presentation.bookmark.BookmarkScreen
import com.example.newsapp.presentation.bookmark.BookmarkViewModel
import com.example.newsapp.presentation.home.HomeScreen
import com.example.newsapp.presentation.home.HomeViewModel
import com.example.newsapp.presentation.onboarding.OnBoardingViewModel
import com.example.newsapp.presentation.onboarding.components.OnBoardingScreen
import com.example.newsapp.presentation.search.SearchScreen
import com.example.newsapp.presentation.search.SearchViewModel

@Composable
fun NavGraph(
    startDestination: String
) {
    // Create a NavController to navigate through the app
    val navController = rememberNavController()

    // Define the navigation graph using NavHost
    // 'NavHost' is used to define the navigation graph. It takes 'NavController' and the 'startDestination' as parameters.
    NavHost(navController = navController, startDestination = startDestination) {
        // Branch for app start navigation
        // The branch is defined with the route 'Route.AppStartNavigation.route'
        // The start destination is set to the onboarding screen
        navigation(
            route = Route.AppStartNavigation.route,
            startDestination = Route.OnBoardingScreen.route
        ) {
            composable(
                route = Route.OnBoardingScreen.route
            ) {
                // Display the onBoardingScreen within the Box, obtaining the ViewModel using Hilt
                val viewModel: OnBoardingViewModel = hiltViewModel()
                OnBoardingScreen(
                    event = viewModel::onEvent
                )
            }
        }

        // Branch for news
        // The branch is defined with the route 'Route.NewsNavigation.route'.
        // The start destination is set to the news navigation screen
        navigation(
            route = Route.NewsNavigation.route,
            startDestination = Route.NewsNavigationScreen.route
        ) {
            // Inside this branch, a 'composable' is used to display a simple text message.
            composable(route = Route.NewsNavigationScreen.route) {
                val viewModel: BookmarkViewModel = hiltViewModel()
                BookmarkScreen(state = viewModel.state.value, navigate = {})
            }
        }
    }
}