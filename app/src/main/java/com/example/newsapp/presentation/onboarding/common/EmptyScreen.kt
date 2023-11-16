package com.example.newsapp.presentation.onboarding.common

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import com.example.newsapp.R
import java.net.ConnectException
import java.net.SocketTimeoutException

/**
 * Usage of 'remember' and 'mutableStateOf': Good use of 'remember' and 'mutableStateOf'
 * to retain and observe state across recompositions
 *
 */

// EmptyScreen function: This function handles the logic of the determining the massage and icon
// Based on the "LoadState.Error'. If there's no error, it defaults to a special message and icon.
@Composable
fun EmptyScreen(error: LoadState.Error? = null) {

    // Initialize message and icon based on the provided error
    var message by remember {
        mutableStateOf(parseErrorMessage(error = error))
    }

    var icon by remember {
        mutableIntStateOf(R.drawable.ic_network_error)
    }

    // If there is no error, use default message and icon
    if (error == null){
        message = "You have not saved news so far !"
        icon = R.drawable.ic_search_document
    }

    // Animation related state variables
    var startAnimation by remember {
        mutableStateOf(false)
    }

    // Alpha animation for a smooth transition
    val alphaAnimation by animateFloatAsState(
        targetValue = if (startAnimation) 0.3f else 0f,
        animationSpec = tween(durationMillis = 1000), label = ""
    )

    // Start the animation when the composable is first launched
    LaunchedEffect(key1 = true) {
        startAnimation = true
    }

    // Delegate rendering to the EmptyContent composable
    EmptyContent(alphaAnim = alphaAnimation, message = message, iconId = icon)

}

/**
 * Composable function to render the content of an empty screen.
 *
 * @param alphaAnim The alpha value for the content to control transparency.
 * @param message The message to be displayed on the screen.
 * @param iconId the resource ID of the icon to be displayed.
 */
@Composable
fun EmptyContent(alphaAnim: Float, message: String, iconId: Int) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Display the icon with alpha animation
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = null,
            tint = if (isSystemInDarkTheme()) LightGray else DarkGray,
            modifier = Modifier
                .size(120.dp)
                .alpha(alphaAnim)
        )
        // Display the message with alpha animation
        Text(
            modifier = Modifier
                .padding(10.dp)
                .alpha(alphaAnim),
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = if (isSystemInDarkTheme()) LightGray else DarkGray,
        )
    }
}

/**
 * Function to parse a [LoadState.Error] and return a human-readable error message.
 *
 * @param error The [LoadState.Error] to be parsed.
 * @return A human-readable error message based on the type of error.
 */
fun parseErrorMessage(error: LoadState.Error?): String {
    return when (error?.error) {
        is SocketTimeoutException -> {
            "Server Unavailable."
        }

        is ConnectException -> {
            "Internet Unavailable."
        }

        else -> {
            "Unknown Error."
        }
    }
}

/**
 * Preview function for the EmptyScreen composable in light mode.
 */
@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun EmptyScreenPreview() {
    EmptyContent(alphaAnim = 0.3f, message = "Internet Unavailable.",R.drawable.ic_network_error)
}

/**
 * Preview function for the EmptryScreen composable in dark mode.
 */
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun EmptyScreenDarkPreview() {
    EmptyContent(alphaAnim = 0.3f, message = "Internet Unavailable" , R.drawable.ic_network_error )
}