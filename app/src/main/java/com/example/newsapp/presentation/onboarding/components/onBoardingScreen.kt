package com.example.newsapp.presentation.onboarding.components

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.newsapp.presentation.onboarding.Dimens.MediumPadding2
import com.example.newsapp.presentation.onboarding.Dimens.PageIndicatorWidth
import com.example.newsapp.presentation.onboarding.OnBoardingEvent
import com.example.newsapp.presentation.onboarding.common.NewsButton
import com.example.newsapp.presentation.onboarding.common.NewsTextButton
import com.example.newsapp.presentation.onboarding.pages
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(
    event: (OnBoardingEvent) -> Unit
) {
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

        Spacer(modifier = Modifier.weight(0.1f))

        // Row for PageIndicator and Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MediumPadding2)
                .weight(0.5f),
            horizontalArrangement = Arrangement.SpaceBetween, // Adjusted horizontal arrangement
            verticalAlignment = Alignment.CenterVertically
        ) {
            PageIndicator(
                modifier = Modifier.width(PageIndicatorWidth),
                pageSize = pages.size,
                selectedPage = pagerState.currentPage
            )

            Spacer(modifier = Modifier.width(16.dp)) // Adjust the value as needed for spacing

            // Row for Buttons
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                val scope = rememberCoroutineScope()

                if (buttonState.value[0].isNotEmpty()) {
                    Log.d("onBoardingScreen", "Button 0 text: ${buttonState.value[0]}")
                    Box(
                        modifier = Modifier.weight(1f).padding(end = 8.dp) // Adjust the value as needed for button spacing
                    )
                    NewsTextButton(
                        text = buttonState.value[0],
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(page = pagerState.currentPage - 1)
                            }
                        }
                    )
                    Log.d("onBoardingScreen", "Button 1 text: ${buttonState.value[1]}")
                    NewsButton(
                        text = buttonState.value[1],
                        onClick = {
                            scope.launch {
                                if (pagerState.currentPage == 2) {
                                    event(OnBoardingEvent.SaveAppEntry)
                                } else {
                                    pagerState.animateScrollToPage(
                                        page = pagerState.currentPage + 1
                                    )
                                }
                            }
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.weight(0.5f))
    }
}