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

    /*********************
     * CONFIGURATION
     *******************/

    /**
     * Resets all custom colors and animations to their default values.
     */
    fun resetAll() {
        config.resetAll()
    }

    /**
     * Applies custom configuration to the toast.
     * @param block A lambda function to configure the toast's appearance.
     */
    fun configure(block: NiceToastConfiguration.() -> Unit) {
        config.apply(block)
    }

    /**
     * Creates and displays a toast by selecting the appropriate style based on configuration.
     * This is the primary method for showing a toast.
     *
     * @param activity The host activity.
     * @param title The toast title.
     * @param message The toast message.
     * @param toastType The type of toast (e.g., SUCCESS, ERROR).
     * @param position The gravity on screen (e.g., TOP, BOTTOM).
     * @param duration How long the toast should be visible.
     * @param font Custom typeface for the message.
     * @param isDarkMode `true` for dark theme, `false` for light.
     * @param isFullBackground `true` for a solid background, `false` for a side-bar style.
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
     * Creates and displays a toast notification with a side color bar.
     * This style features a colored side bar, a colored title, and a rounded background.
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

        setBackgroundAndFilter(R.drawable.round_bg, spec.backgroundColor, layout, activity)

        binding.toastTitleTv.setTextColor(ContextCompat.getColor(activity, spec.primaryColor))
        binding.toastTitleTv.text = title?.takeIf { it.isNotBlank() } ?: spec.defaultTitle

        setDescriptionDetails(font, R.color.black, message, binding.toastDescTv)

        activity.showTransientOverlay(layout, duration, position)
    }

    /**
     * Creates and displays a solid-colored toast notification.
     * This toast style has a solid background color with white text.
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
        val binding = NiceToastBgBinding.inflate(inflater)
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
        val binding = NiceToastBinding.inflate(inflater)
        val layout = binding.root

        val spec = getStyleSpec(toastType, darkTheme = true, solidBackground = false)

        binding.toastImg.setImageDrawable(ContextCompat.getDrawable(activity, spec.iconRes))
        DrawableCompat.setTint(DrawableCompat.wrap(binding.toastImg.drawable), ContextCompat.getColor(activity, spec.primaryColor))
        activity.startAnimation(binding.toastImg, config.customAnimRes)

        binding.verticalLineV.backgroundTintList = ContextCompat.getColorStateList(activity, spec.primaryColor)

        setBackgroundAndFilter(R.drawable.round_bg, spec.backgroundColor, layout, activity)

        binding.toastTitleTv.setTextColor(ContextCompat.getColor(activity, spec.primaryColor))
        binding.toastTitleTv.text = title?.takeIf { it.isNotBlank() } ?: spec.defaultTitle

        setDescriptionDetails(font, R.color.white, message, binding.toastDescTv)

        activity.showTransientOverlay(layout, duration, position)
    }

    /**
     * Creates and displays a dark-themed toast notification.
     * This toast style has a dark background with colored icons and title text.
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
        val binding = NiceToastBgBinding.inflate(inflater)
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

    private fun Activity.showTransientOverlay(content: View, duration: Long, position: Int) {
        val root = window.decorView as ViewGroup
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
            content.animate().alpha(0f).setDuration(200).withEndAction {
                root.removeView(content)
            }.start()
        }, duration.coerceAtLeast(500L))
    }

    private fun Activity.startAnimation(customToastImage: ImageView, @AnimRes animRes: Int = R.anim.pulse) {
        val loadAnimation = AnimationUtils.loadAnimation(this, animRes)
        customToastImage.startAnimation(loadAnimation)
    }

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