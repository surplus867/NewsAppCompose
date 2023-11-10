package com.example.newsapp.presentation.onboarding.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.example.newsapp.presentation.onboarding.Dimens.IndicatorSize
import com.example.newsapp.ui.theme.BlueGray

/**
 * Composable function for a page indicator used in onboarding screens.
 *
 * @param modifier Custom modifier for the entire page indicator.
 * @param pageSize Total number of pages.
 * @param selectedPage Currently selected page index.
 * @param selectedColor Color for the indicator of the selected page.
 * @param unselectedColor Color for the indicators of unselected pages.
 */
@Composable
fun PageIndicator(
    modifier: Modifier = Modifier,
    pageSize: Int,
    selectedPage: Int,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    unselectedColor: Color = BlueGray

) {
    // Horizontal row to contain individual page indicators
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Create indicators for each page
        repeat(pageSize) { page ->
            Box(
                modifier = Modifier
                    .size(IndicatorSize) // Set the size of each indicator
                    .clip(CircleShape) // Clip the indicator to a circle shape
                    .background(color = if (page == selectedPage) selectedColor else unselectedColor)
                // Set background color based on whether it's the selected page or not
            )
        }
    }
}