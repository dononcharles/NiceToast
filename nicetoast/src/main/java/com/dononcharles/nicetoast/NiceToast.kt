package com.dononcharles.nicetoast

import android.app.Activity
import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.Typeface
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.AnimRes
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.dononcharles.nicetoast.databinding.NiceToastBgBinding
import com.dononcharles.nicetoast.databinding.NiceToastBinding

class NiceToast {

    private val config = NiceToastConfiguration()
    private var currentToastView: View? = null

    companion object {
        private const val POST_DELAYED_MIN = 500L
        private const val POST_DELAYED_DURATION = 200L
    }

    /*********************
     * CONFIGURATION
     *******************/

    /**
     * Resets all custom configurations to their default values.
     * This includes colors for different toast types and any custom icon animations.
     *
     * Call this method to revert the `NiceToast` appearance to its initial state
     * after applying custom configurations via the `configure` block.
     */
    fun resetAll() {
        config.resetAll()
    }

    /**
     * Applies a custom configuration to the `NiceToast` instance. This allows for
     * customizing properties like colors and animations for different toast types.
     *
     * This configuration is applied globally to all subsequent toasts created
     * with this `NiceToast` instance. To reset to defaults, use [resetAll].
     *
     * Example usage:
     * ```
     * val niceToast = NiceToast()
     * niceToast.configure {
     *     successToastColor = R.color.my_custom_success_color
     *     errorToastColor = R.color.my_custom_error_color
     * }
     * ```
     *
     * @param block A lambda with [NiceToastConfiguration] as its receiver, used to set custom properties.
     * @see NiceToastConfiguration
     * @see resetAll
     */
    fun configure(block: NiceToastConfiguration.() -> Unit) {
        config.apply(block)
    }

    /**
     * Creates and displays a toast by selecting the appropriate style based on various configurations.
     * This is the primary method for showing a toast, which internally delegates to a more specific
     * toast creation function based on the theme and background style.
     *
     * @param activity The host [Activity] where the toast will be displayed.
     * @param title The optional title text for the toast.
     * @param message The main message content of the toast.
     * @param toastType The type of toast, which determines its icon and color scheme (e.g., [NiceToastType.SUCCESS], [NiceToastType.ERROR]).
     * @param position The gravity defining where the toast appears on the screen (e.g., [Gravity.TOP], [Gravity.BOTTOM]).
     * @param duration The duration in milliseconds for how long the toast should be visible.
     * @param font An optional custom [Typeface] to apply to the toast's message.
     * @param isDarkMode A boolean flag indicating if the toast should use a dark theme (`true`) or a light theme (`false`).
     * @param isFullBackground A boolean flag indicating if the toast should have a solid background color (`true`) or a side-bar style (`false`).
     */
    fun magicCreate(
        activity: Activity,
        title: String? = null,
        message: String,
        toastType: NiceToastType,
        position: Int,
        duration: Long,
        font: Typeface?,
        isDarkMode: Boolean,
        isFullBackground: Boolean
    ) {
        if (isDarkMode) {
            if (isFullBackground) {
                createDarkNiceToastWithBackground(activity, title, message, toastType, position, duration, font)
            } else {
                createDarkNiceToast(activity, title, message, toastType, position, duration, font)
            }
        } else {
            if (isFullBackground) {
                createNiceToastWithBackground(activity, title, message, toastType, position, duration, font)
            } else {
                createNiceToast(activity, title, message, toastType, position, duration, font)
            }
        }
    }

    /**
     * Creates and displays a toast notification with a side color bar (light theme).
     *
     * This style features a light background, a colored side bar corresponding to the `toastType`,
     * a colored icon, and a colored title. The message text is black.
     *
     * @param activity The host activity where the toast will be displayed.
     * @param title An optional title for the toast. If null or blank, a default title based on `toastType` is used.
     * @param message The main message text of the toast.
     * @param toastType The type of toast (e.g., `SUCCESS`, `ERROR`), which determines the color and icon.
     * @param position The gravity where the toast should appear on the screen (e.g., `Gravity.TOP`, `Gravity.BOTTOM`).
     * @param duration The duration in milliseconds for how long the toast should be visible.
     * @param font An optional custom `Typeface` for the toast message and title.
     */
    fun createNiceToast(
        activity: Activity,
        title: String? = null,
        message: String,
        toastType: NiceToastType,
        position: Int,
        duration: Long,
        font: Typeface?
    ) {
        val inflater = LayoutInflater.from(activity)
        val root = activity.window.decorView as ViewGroup
        val binding = NiceToastBinding.inflate(inflater, root, false)
        val layout = binding.root

        val spec = getStyleSpec(toastType, darkTheme = false, solidBackground = false)

        binding.toastImg.setImageDrawable(ContextCompat.getDrawable(activity, spec.iconRes))
        DrawableCompat.setTint(DrawableCompat.wrap(binding.toastImg.drawable), ContextCompat.getColor(activity, spec.primaryColor))
        activity.startAnimation(binding.toastImg, config.customAnimRes)

        binding.verticalLineV.backgroundTintList = ContextCompat.getColorStateList(activity, spec.primaryColor)
        binding.verticalLineVRight.backgroundTintList = ContextCompat.getColorStateList(activity, spec.primaryColor)

        setBackgroundAndFilter(R.drawable.round_bg, spec.backgroundColor, layout, activity)

        binding.toastTitleTv.setTextColor(ContextCompat.getColor(activity, spec.primaryColor))
        binding.toastTitleTv.text = title?.takeIf { it.isNotBlank() } ?: spec.defaultTitle

        setDescriptionDetails(font, R.color.black, message, binding.toastDescTv)

        activity.showTransientOverlay(layout, duration, position)
    }

    /**
     * Creates and displays a toast with a solid background color.
     *
     * This style features a solid background color derived from the `toastType`,
     * with white text and a white icon. It's suitable for light-themed applications
     * where a more prominent, fully-colored toast is desired.
     *
     * @param activity The host activity where the toast will be displayed.
     * @param title An optional title for the toast. If null or blank, a default title based on `toastType` is used.
     * @param message The main message content of the toast.
     * @param toastType The type of toast (e.g., SUCCESS, ERROR), which determines the background color and icon.
     * @param position The on-screen position for the toast (e.g., [TOAST_GRAVITY_BOTTOM]).
     * @param duration The time in milliseconds for the toast to be visible.
     * @param font An optional custom [Typeface] for the toast's message and title.
     */
    fun createNiceToastWithBackground(
        activity: Activity,
        title: String? = null,
        message: String,
        toastType: NiceToastType,
        position: Int,
        duration: Long,
        font: Typeface?
    ) {
        val inflater = LayoutInflater.from(activity)
        val root = activity.window.decorView as ViewGroup
        val binding = NiceToastBgBinding.inflate(inflater, root, false)
        val layout = binding.root

        val spec = getStyleSpec(toastType, darkTheme = false, solidBackground = true)

        binding.toastImg.setImageDrawable(ContextCompat.getDrawable(activity, spec.iconRes))
        DrawableCompat.setTint(DrawableCompat.wrap(binding.toastImg.drawable), ContextCompat.getColor(activity, spec.primaryColor))
        activity.startAnimation(binding.toastImg, config.customAnimRes)

        setBackgroundAndFilter(R.drawable.round_bg, spec.backgroundColor, layout, activity)

        binding.toastTitleTv.setTextColor(ContextCompat.getColor(activity, R.color.white))
        binding.toastTitleTv.text = title?.takeIf { it.isNotBlank() } ?: spec.defaultTitle

        setDescriptionDetails(font, R.color.white, message, binding.toastDescTv)
        activity.showTransientOverlay(layout, duration, position)
    }

    /**
     * Creates and displays a dark-themed toast notification with a side color bar.
     * This style features a dark background, a colored side bar, and a colored title.
     *
     * @param activity The host activity where the toast will be displayed.
     * @param title An optional title for the toast. If null or blank, a default title based on `toastType` is used.
     * @param message The main message body of the toast.
     * @param toastType The type of toast (e.g., SUCCESS, ERROR), which determines the icon and colors.
     * @param position The gravity on screen where the toast should appear (e.g., TOP, BOTTOM).
     * @param duration The duration in milliseconds for how long the toast should be visible.
     * @param font An optional custom typeface to be applied to the toast message.
     */
    fun createDarkNiceToast(
        activity: Activity,
        title: String? = null,
        message: String,
        toastType: NiceToastType,
        position: Int,
        duration: Long,
        font: Typeface?
    ) {
        val inflater = LayoutInflater.from(activity)
        val root = activity.window.decorView as ViewGroup
        val binding = NiceToastBinding.inflate(inflater, root, false)
        val layout = binding.root

        val spec = getStyleSpec(toastType, darkTheme = true, solidBackground = false)

        binding.toastImg.setImageDrawable(ContextCompat.getDrawable(activity, spec.iconRes))
        DrawableCompat.setTint(DrawableCompat.wrap(binding.toastImg.drawable), ContextCompat.getColor(activity, spec.primaryColor))
        activity.startAnimation(binding.toastImg, config.customAnimRes)

        binding.verticalLineV.backgroundTintList = ContextCompat.getColorStateList(activity, spec.primaryColor)
        binding.verticalLineVRight.backgroundTintList = ContextCompat.getColorStateList(activity, spec.primaryColor)

        setBackgroundAndFilter(R.drawable.round_bg, spec.backgroundColor, layout, activity)

        binding.toastTitleTv.setTextColor(ContextCompat.getColor(activity, spec.primaryColor))
        binding.toastTitleTv.text = title?.takeIf { it.isNotBlank() } ?: spec.defaultTitle

        setDescriptionDetails(font, R.color.white, message, binding.toastDescTv)

        activity.showTransientOverlay(layout, duration, position)
    }

    /**
     * Creates and displays a dark-themed toast notification with a solid background.
     * This toast style has a dark, solid background color determined by the toast type,
     * with colored icons and title text, and white message text.
     *
     * @param activity The host activity.
     * @param title An optional title for the toast. If null or blank, a default title based on `toastType` is used.
     * @param message The main message content of the toast.
     * @param toastType The type of toast (e.g., SUCCESS, ERROR), which determines the colors and icon.
     * @param position The on-screen gravity for the toast (e.g., TOP, BOTTOM, CENTER).
     * @param duration The time in milliseconds for the toast to be displayed.
     * @param font An optional custom typeface for the toast message.
     */
    fun createDarkNiceToastWithBackground(
        activity: Activity,
        title: String? = null,
        message: String,
        toastType: NiceToastType,
        position: Int,
        duration: Long,
        font: Typeface?
    ) {
        val inflater = LayoutInflater.from(activity)
        val root = activity.window.decorView as ViewGroup
        val binding = NiceToastBgBinding.inflate(inflater, root, false)
        val layout = binding.root

        val spec = getStyleSpec(toastType, darkTheme = true, solidBackground = false)

        binding.toastImg.setImageDrawable(ContextCompat.getDrawable(activity, spec.iconRes))
        DrawableCompat.setTint(DrawableCompat.wrap(binding.toastImg.drawable), ContextCompat.getColor(activity, spec.primaryColor))
        activity.startAnimation(binding.toastImg, config.customAnimRes)

        setBackgroundAndFilter(R.drawable.round_bg, spec.backgroundColor, layout, activity)

        binding.toastTitleTv.setTextColor(ContextCompat.getColor(activity, spec.primaryColor))
        binding.toastTitleTv.text = title?.takeIf { it.isNotBlank() } ?: spec.defaultTitle

        setDescriptionDetails(font, R.color.white, message, binding.toastDescTv)

        activity.showTransientOverlay(layout, duration, position)
    }

    private fun setDescriptionDetails(font: Typeface?, @ColorRes textColor: Int, message: String, layout: TextView) {
        layout.setTextColor(ContextCompat.getColor(layout.context, textColor))
        layout.text = message
        font?.let {
            layout.typeface = font
        }
    }

    private fun setBackgroundAndFilter(@DrawableRes background: Int, @ColorRes colorFilter: Int, layout: View, context: Context) {
        val drawable = ContextCompat.getDrawable(context, background)
        drawable?.colorFilter = PorterDuffColorFilter(ContextCompat.getColor(context, colorFilter), PorterDuff.Mode.MULTIPLY)
        layout.background = drawable
    }

    /**
     * Attaches a custom view to the activity's decor view to display it as a transient overlay,
     * such as a toast. This function handles the positioning, animation, and automatic dismissal
     * of the view. It also ensures that only one toast is visible at a time by removing the
     * previous one if it exists.
     *
     * @receiver The [Activity] in which the overlay will be shown.
     * @param content The [View] to be displayed as the overlay.
     * @param duration The duration in milliseconds for which the overlay should be visible before starting to fade out.
     * @param position The gravity constant (e.g., [Gravity.TOP], [Gravity.BOTTOM]) determining where on the screen to show the overlay.
     */
    private fun Activity.showTransientOverlay(content: View, duration: Long, position: Int) {
        val root = window.decorView as ViewGroup

        currentToastView?.let { root.removeView(it) }
        currentToastView = content

        // Disable clipping on the parent to allow rounded corners to draw correctly.
        root.clipToPadding = false
        root.clipChildren = false

        val contentParams = content.layoutParams as? ViewGroup.MarginLayoutParams
        if (contentParams == null) {
            return
        }

        val newParams = FrameLayout.LayoutParams(
            contentParams.width,
            contentParams.height
        ).apply {
            leftMargin = contentParams.leftMargin
            topMargin = contentParams.topMargin
            rightMargin = contentParams.rightMargin
            bottomMargin = contentParams.bottomMargin

            gravity = when (position) {
                TOAST_GRAVITY_TOP -> Gravity.TOP
                TOAST_GRAVITY_CENTER -> Gravity.CENTER
                TOAST_GRAVITY_BOTTOM -> Gravity.BOTTOM
                else -> Gravity.BOTTOM
            }

            if (gravity == Gravity.BOTTOM) {
                bottomMargin += dpToPx(96)
            }
        }

        content.layoutParams = newParams

        root.addView(content)

        val pulseAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_in_and_fade)
        content.startAnimation(pulseAnimation)

        // Auto dismiss
        content.postDelayed({
            content.animate().alpha(0f).setDuration(POST_DELAYED_DURATION).withEndAction {
                root.removeView(content)

                 if (content == currentToastView) {
                    currentToastView = null
                }
            }.start()
        }, duration.coerceAtLeast(POST_DELAYED_MIN))
    }

    /**
     * Starts an animation on the toast's icon.
     *
     * This is an extension function for `Activity`. It loads and starts an animation
     * on the provided `ImageView`. A default pulse animation is used if no custom
     * animation resource is specified.
     *
     * @param customToastImage The `ImageView` (the toast icon) to animate.
     * @param animRes The resource ID of the animation to apply. Defaults to `R.anim.pulse`.
     */
    private fun Activity.startAnimation(customToastImage: ImageView, @AnimRes animRes: Int = R.anim.pulse) {
        val loadAnimation = AnimationUtils.loadAnimation(this, animRes)
        customToastImage.startAnimation(loadAnimation)
    }


    /**
     * Retrieves the style specification for a toast based on its type and theme settings.
     * This includes the icon, primary color, background color, and default title.
     * The returned colors are resource IDs.
     *
     * @param toastType The type of toast (e.g., SUCCESS, ERROR).
     * @param darkTheme `true` if the dark theme is active, `false` otherwise.
     * @param solidBackground `true` for a solid background color, `false` for a side-bar style.
     * @return A [StyleSpec] data class containing the appropriate resources for the toast.
     */
    private fun getStyleSpec(toastType: NiceToastType, darkTheme: Boolean, solidBackground: Boolean): StyleSpec {
        val primaryColor = when (toastType) {
            NiceToastType.SUCCESS -> config.successToastColor
            NiceToastType.ERROR -> config.errorToastColor
            NiceToastType.WARNING, NiceToastType.NO_INTERNET -> config.warningToastColor
            NiceToastType.INFO -> config.infoToastColor
            NiceToastType.DELETE -> config.deleteToastColor
        }

        val backgroundColor = when {
            solidBackground -> primaryColor
            darkTheme -> R.color.dark_color
            else -> when (toastType) {
                NiceToastType.SUCCESS -> config.successBackgroundToastColor
                NiceToastType.ERROR -> config.errorBackgroundToastColor
                NiceToastType.WARNING, NiceToastType.NO_INTERNET -> config.warningBackgroundToastColor
                NiceToastType.INFO -> config.infoBackgroundToastColor
                NiceToastType.DELETE -> config.deleteBackgroundToastColor
            }
        }

        val iconRes = when (toastType) {
            NiceToastType.SUCCESS -> R.drawable.baseline_check_circle_24
            NiceToastType.ERROR -> R.drawable.outline_error_24
            NiceToastType.WARNING -> R.drawable.outline_warning_24
            NiceToastType.INFO -> R.drawable.outline_info_24
            NiceToastType.DELETE -> R.drawable.outline_delete_24
            NiceToastType.NO_INTERNET -> R.drawable.outline_signal_cellular_connected_no_internet_4_bar_24
        }

        return StyleSpec(iconRes, primaryColor, backgroundColor, toastType.getName())
    }

    private data class StyleSpec(val iconRes: Int, val primaryColor: Int, val backgroundColor: Int, val defaultTitle: String)

    private fun Context.dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }
}
