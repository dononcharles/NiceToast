package com.dononcharles.nicetoast

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

class NiceToastConfigurationTest {

    private lateinit var config: NiceToastConfiguration

    @Before
    fun setup() {
        config = NiceToastConfiguration()
    }

    @Test
    fun `resetAll() should restore all properties to their default values`() {
        // Arrange: Change multiple properties from their defaults
        config.setSuccessColor(R.color.black)
        config.setErrorColor(R.color.black)
        config.setInfoColor(R.color.black)
        config.setWarningBackgroundColor(R.color.black)
        config.setCustomAnimation(R.anim.fade_in)

        // Act: Call the method under test
        config.resetAll()

        // Assert: Verify that ALL relevant properties are back to their defaults
        assertEquals(R.color.success_color, config.successToastColor)
        assertEquals(R.color.error_color, config.errorToastColor)
        assertEquals(R.color.info_color, config.infoToastColor)
        assertEquals(R.color.warning_bg_color, config.warningBackgroundToastColor)
        assertEquals(R.anim.pulse, config.customAnimRes)
    }

    @Test
    fun `setCustomAnimation() should update the animation resource`() {
        // Arrange
        val newAnimRes = R.anim.fade_in

        // Act
        config.setCustomAnimation(newAnimRes)

        // Assert
        assertEquals(newAnimRes, config.customAnimRes)
    }

    @Test
    fun `setSuccessColor() should update the success color`() {
        // Arrange
        val newColor = R.color.black

        // Act
        config.setSuccessColor(newColor)

        // Assert
        assertEquals(newColor, config.successToastColor)
    }

    @Test
    fun `setErrorBackgroundColor() should update the error background color`() {
        // Arrange
        val newColor = R.color.black

        // Act
        config.setErrorBackgroundColor(newColor)

        // Assert
        assertEquals(newColor, config.errorBackgroundToastColor)
    }

    @Test
    fun `setWarningBackgroundColor() should accept any integer value`() {
        // Arrange
        val anyColorValue = -1 // Using -1 is a good way to test this

        // Act
        config.setWarningBackgroundColor(anyColorValue)

        // Assert
        assertEquals(anyColorValue, config.warningBackgroundToastColor)
    }

}