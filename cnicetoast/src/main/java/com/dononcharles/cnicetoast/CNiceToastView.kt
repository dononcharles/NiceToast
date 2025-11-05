package com.dononcharles.cnicetoast

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * The core visual composable for displaying a toast. This function is responsible for
 * rendering the toast's UI based on the provided data and configuration. It is stateless
 * and purely presentational.
 *
 * This composable uses a [Card] to create the main body with elevation and rounded corners.
 * The layout is a [Row] containing an icon and a [Column] for the title and message.
 * The styling (colors, icon) is dynamically determined by the [StyleSpec] resolved from
 * the configuration.
 *
 * @param data The [CNiceToastData] object containing all the content to display (message,
 *   title, type, etc.).
 * @param config The [CNiceToastConfiguration] used to resolve the visual style of the toast.
 *   This is typically provided via [LocalCNiceToastConfig].
 */
@Composable
internal fun CNiceToastView(data: CNiceToastData, config: CNiceToastConfiguration) {
    // Resolve the style specification (colors, icon) from the configuration.
    // `remember` is used here to avoid re-calculating the spec on every recomposition
    // unless one of the key inputs (type, theme, background style) changes.
    val spec = remember(data.type, data.isDarkMode, data.isFullBackground) {
        config.getStyleSpec(data.type, data.isDarkMode, data.isFullBackground)
    }

    // Resolve the color resources from the style spec.
    val primaryColor = colorResource(id = spec.primaryColor)
    val backgroundColor = colorResource(id = spec.backgroundColor)

    // For solid or dark backgrounds, white text provides the best contrast.
    val textColor = when {
        data.isFullBackground -> Color.White
        data.isDarkMode -> Color.White
        else -> Color.Black
    }

    // The title color is usually the primary color, but on a dark, solid background, white is more readable.
    val titleColor = if (data.isFullBackground || data.isDarkMode) {
        Color.White
    } else {
        primaryColor
    }

    val infiniteTransition = rememberInfiniteTransition(label = "icon_pulse_transition")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f, // Start at normal size
        targetValue = 1.2f, // Scale up to 120%
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "icon_pulse_scale"
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(dimensionResource(R.dimen.dimen_8)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Row(
            modifier = Modifier.height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Display the colored side-bar only for the non-solid background style.
            if (!data.isFullBackground) {
                Box(
                    modifier = Modifier
                        .width(dimensionResource(R.dimen.dimen_6))
                        .fillMaxHeight()
                        .background(primaryColor)
                )
            }

            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                    }
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = spec.iconRes),
                    contentDescription = data.title ?: spec.defaultTitle,
                    tint = primaryColor,
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.dimen_8))
                        .size(dimensionResource(R.dimen.dimen_24))
                )
            }

            // A Column to arrange the title and message vertically.
            Column(
                modifier = Modifier
                    .weight(1f) // Takes up the remaining horizontal space
                    .padding(vertical = dimensionResource(R.dimen.dimen_12))
                    .padding(end = dimensionResource(R.dimen.dimen_16))
            ) {
                Text(
                    text = data.title ?: spec.defaultTitle,
                    color = titleColor,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = data.message,
                    color = textColor,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            if (!data.isFullBackground) {
                Box(
                    modifier = Modifier
                        .width(dimensionResource(R.dimen.dimen_6))
                        .fillMaxHeight()
                        .background(primaryColor)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NiceToastViewPreview_No_FullBackground() {
    CNiceToastView(
        data = CNiceToastData(
            message = "This is a sample success message to demonstrate the NiceToastView composable.",
            title = "Success",
            type = CNiceToastType.SUCCESS,
            duration = 4000L,
            isDarkMode = false,
            isFullBackground = false,
            position = CNiceToastPosition.BOTTOM
        ),
        config = CNiceToastConfiguration()
    )
}

@Preview(showBackground = true)
@Composable
private fun NiceToastViewPreview_FullBackground_DarkMode() {
    CNiceToastView(
        data = CNiceToastData(
            message = "This is a sample error message to demonstrate the NiceToastView composable with full background in dark mode.",
            title = "Error",
            type = CNiceToastType.ERROR,
            duration = 4000L,
            isDarkMode = true,
            isFullBackground = true,
            position = CNiceToastPosition.BOTTOM
        ),
        config = CNiceToastConfiguration()
    )
}

@Preview(showBackground = true)
@Composable
private fun NiceToastViewPreview_FullBackground_LightMode() {
    CNiceToastView(
        data = CNiceToastData(
            message = "This is a sample warning message to demonstrate the NiceToastView composable with full background in light mode.",
            title = "Warning",
            type = CNiceToastType.WARNING,
            duration = 4000L,
            isDarkMode = false,
            isFullBackground = true,
            position = CNiceToastPosition.BOTTOM
        ),
        config = CNiceToastConfiguration()
    )
}