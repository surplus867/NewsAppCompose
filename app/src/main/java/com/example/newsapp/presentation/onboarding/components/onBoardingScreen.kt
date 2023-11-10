package com.example.newsapp.presentation.onboarding.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.newsapp.presentation.onboarding.pages

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen() {
    // Column layout to contain the onboarding screen
    Column(modifier = Modifier.fillMaxSize()) {

        // State to manage the current page of the HorizontalPager
        val pagerState = rememberPagerState(initialPage = 0) {
            pages.size
        }

        // State to determine button text based on the current page
        val buttonState = remember {
            derivedStateOf {
                when (pagerState.currentPage) {
                    0 -> listOf("", "Next")// First page, only "Next" button
                    1 -> listOf("Back", "Next")// Second page, both "Back" and "Next" buttons
                    2 -> listOf("Back", "Get Started")// Last page, "Back" and "Get Started" buttons
                    else -> listOf("", "") // Default case
                }
            }
        }

        // HorizontalPager to navigate through onboarding pages
        HorizontalPager(state = pagerState) { index ->
            // Display the onboarding page based on the current index
            OnBoardingPage(page = pages[index])
        }
    }
}