package com.example.newsapp.presentation.navGraph


// Sealed classes are useful for representing a fixed set of classes or types in a hierarchy.
// The uses of sealed classes ensures that all possible subclasses are known at compile time.
// This approach makes it easy to manage and navigate between different screens in your app.
// It provides a clean and type-safe representation of your navigation routes.

// Usage: You can use these subclasses as constants to represent different routes within your app.
// For example, if you want to navigate to the home screen, you can use 'Route.HomeScreen.route'
sealed class Route(
    val route: String
) {

    // Subclasses representing different screens or navigation destinations
    object OnBoardingScreen : Route(route = "onBoardingScreen")
    object HomeScreen : Route(route = "homeScreen")
    object SearchScreen : Route(route = "searchScreen")
    object BookmarkScreen : Route(route = "bookmarkScreen")
    object DetailsScreen : Route(route = "detailsScreen")
    object AppStartNavigation : Route(route = "appStartNavigation")
    object NewsNavigation : Route(route = "newsNavigation")
    object NewsNavigationScreen : Route(route = "newsNavigator")
}
