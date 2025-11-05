package com.dononcharles.cnicetoast

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf

/**
 * Configuration for Compose NiceToast components.
 *
 * This class is marked as @Immutable to help the Compose compiler optimize recompositions.
 * It follows a builder-like pattern where you create a modified copy of the configuration
 * instead of changing its properties directly.
 */
@Immutable
data class CNiceToastConfiguration(
    @param:ColorRes val successToastColor: Int = R.color.success_color,
    @param:ColorRes val errorToastColor: Int = R.color.error_color,
    @param:ColorRes val warningToastColor: Int = R.color.warning_color,
    @param:ColorRes val infoToastColor: Int = R.color.info_color,

    @param:ColorRes val successBackgroundToastColor: Int = R.color.success_bg_color,
    @param:ColorRes val errorBackgroundToastColor: Int = R.color.error_bg_color,
    @param:ColorRes val warningBackgroundToastColor: Int = R.color.warning_bg_color,
    @param:ColorRes val infoBackgroundToastColor: Int = R.color.info_bg_color,
) {
    /**
     * Internal function to resolve the style based on the toast type and theme.
     * This logic is now part of the immutable configuration object.
     */
    internal fun getStyleSpec(toastType: CNiceToastType, darkTheme: Boolean, solidBackground: Boolean): StyleSpec {
        val primaryColor = when (toastType) {
            CNiceToastType.SUCCESS -> successToastColor
            CNiceToastType.ERROR -> errorToastColor
            CNiceToastType.WARNING -> warningToastColor
            CNiceToastType.INFO -> infoToastColor
        }

        val backgroundColor = when {
            solidBackground -> primaryColor
            darkTheme -> R.color.dark_color
            else -> when (toastType) {
                CNiceToastType.SUCCESS -> successBackgroundToastColor
                CNiceToastType.ERROR -> errorBackgroundToastColor
                CNiceToastType.WARNING -> warningBackgroundToastColor
                CNiceToastType.INFO -> infoBackgroundToastColor
            }
        }

        val iconRes = when (toastType) {
            CNiceToastType.SUCCESS -> R.drawable.baseline_check_circle_24
            CNiceToastType.ERROR -> R.drawable.outline_error_24
            CNiceToastType.WARNING -> R.drawable.outline_warning_24
            CNiceToastType.INFO -> R.drawable.outline_info_24
        }

        return StyleSpec(
            iconRes = iconRes,
            primaryColor = primaryColor,
            backgroundColor = backgroundColor,
            defaultTitle = toastType.name
        )
    }
}

/**
 * Data class holding the resolved style properties for a specific toast.
 */
@Immutable
internal data class StyleSpec(
    @param:DrawableRes val iconRes: Int,
    @param:ColorRes val primaryColor: Int,
    @param:ColorRes val backgroundColor: Int,
    val defaultTitle: String
)
enum class CNiceToastPosition { TOP, BOTTOM, CENTER }
enum class CNiceToastType { SUCCESS, ERROR, WARNING, INFO }

/**
 * CompositionLocal to provide the NiceToastConfiguration down the UI tree.
 * Use this to override the default configuration for a specific part of your app.
 */
val LocalCNiceToastConfig = staticCompositionLocalOf { CNiceToastConfiguration() }