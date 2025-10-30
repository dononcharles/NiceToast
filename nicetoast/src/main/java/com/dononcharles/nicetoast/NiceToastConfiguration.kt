package com.dononcharles.nicetoast

import androidx.annotation.AnimRes
import androidx.annotation.ColorRes

/**
 * A configuration class for customizing the appearance and behavior of NiceToast.
 * Use this class to set custom colors and animations.
 */
class NiceToastConfiguration {
    var successToastColor: Int = R.color.success_color
        private set
    var errorToastColor: Int = R.color.error_color
        private set
    var warningToastColor: Int = R.color.warning_color
        private set
    var infoToastColor: Int = R.color.info_color
        private set
    var deleteToastColor: Int = R.color.delete_color
        private set

    var successBackgroundToastColor: Int = R.color.success_bg_color
        private set
    var errorBackgroundToastColor: Int = R.color.error_bg_color
        private set
    var warningBackgroundToastColor: Int = R.color.warning_bg_color
        private set
    var infoBackgroundToastColor: Int = R.color.info_bg_color
        private set
    var deleteBackgroundToastColor: Int = R.color.delete_bg_color
        private set

    var customAnimRes: Int = R.anim.pulse
        private set

    fun setSuccessColor(@ColorRes color: Int) {
        successToastColor = color
    }

    fun setSuccessBackgroundColor(@ColorRes color: Int) {
        successBackgroundToastColor = color
    }

    fun setErrorColor(@ColorRes color: Int) {
        errorToastColor = color
    }

    fun setErrorBackgroundColor(@ColorRes color: Int) {
        errorBackgroundToastColor = color
    }

    fun setWarningColor(@ColorRes color: Int) {
        warningToastColor = color
    }

    fun setWarningBackgroundColor(@ColorRes color: Int) {
        warningBackgroundToastColor = color
    }

    fun setInfoColor(@ColorRes color: Int) {
        infoToastColor = color
    }

    fun setInfoBackgroundColor(@ColorRes color: Int) {
        infoBackgroundToastColor = color
    }

    fun setDeleteColor(@ColorRes color: Int) {
        deleteToastColor = color
    }

    fun setDeleteBackgroundColor(@ColorRes color: Int) {
        deleteBackgroundToastColor = color
    }

    fun setCustomAnimation(@AnimRes animRes: Int) {
        customAnimRes = animRes
    }

    /**
     * Resets all colors and animations to their default values.
     */
    fun resetAll() {
        this.successToastColor = R.color.success_color
        this.errorToastColor = R.color.error_color
        this.warningToastColor = R.color.warning_color
        this.infoToastColor = R.color.info_color
        this.deleteToastColor = R.color.delete_color

        this.successBackgroundToastColor = R.color.success_bg_color
        this.errorBackgroundToastColor = R.color.error_bg_color
        this.warningBackgroundToastColor = R.color.warning_bg_color
        this.infoBackgroundToastColor = R.color.info_bg_color
        this.deleteBackgroundToastColor = R.color.delete_bg_color

        this.customAnimRes = R.anim.pulse
    }
}