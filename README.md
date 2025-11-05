# Nice Toast for Android

[![API](https://img.shields.io/badge/API-24%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=24)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Kotlin](https://img.shields.io/badge/Kotlin-100%25-blue.svg?style=flat)](https://kotlinlang.org/)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.dononcharles/nicetoast)](https://central.sonatype.com/artifact/io.github.dononcharles/nicetoast)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.dononcharles/cnicetoast)](https://central.sonatype.com/artifact/io.github.dononcharles/cnicetoast)


Nice Toast is a stunning and highly customizable toast library for Android, available for both traditional **Views** and **Jetpack Compose**. Elevate your app's user experience by replacing standard toasts with eye-catching, animated, and informative notifications that look great in both light and dark themes.

*   **NiceToast**: The original library for Android's Legacy View system.
*   **CNiceToast**: A modern, Compose-native version for Jetpack Compose UIs.

Whether you need to show a success message, an error, a warning, or just some info, NiceToast provides a variety of styles to fit your app's design language.

## 🤩 Features

*   **Beautiful Pre-defined Styles**: Toasts for Success, Error, Warning, Info, Delete, and No Internet connection are ready to use out-of-the-box.
*   **Light & Dark Mode Support**: Automatically adapts with beautifully crafted light and dark theme variations.
*   **Two Core Styles**:
    *   **Side-Bar Style**: A modern design featuring a colored side-bar and icon.
    *   **Solid Background Style**: A bold, filled design for more emphasis.
*   **Highly Customizable**:
    *   Easily override default colors for each toast type.
    *   Apply custom fonts to match your app's branding.
    *   Set custom animations for the toast icon.
*   **Flexible & Easy to Use**: Simple, fluent APIs for both Views and Compose.
*   **Position Control**: Show toasts at the top, center, or bottom of the screen.

## ✨ Preview

### GIF showcasing different toast types
<div style="text-align: center;">
  <img src="https://raw.githubusercontent.com/dononcharles/NiceToastAssets/main/assets/blob/phone_preview.gif" width="300" alt="Nice Toast library for Android preview GIF"/>
</div>

### Light Mode
<div style="text-align: center;">
<table>
  <tr>
    <td><img src="https://raw.githubusercontent.com/dononcharles/NiceToastAssets/main/assets/img/shoot1.png" width="200" alt="Success Toast (Side-bar)"></td>
    <td><img src="https://raw.githubusercontent.com/dononcharles/NiceToastAssets/main/assets/img/shoot2.png" width="200" alt="Success Toast (Solid)"></td>
    <td><img src="https://raw.githubusercontent.com/dononcharles/NiceToastAssets/main/assets/img/shoot3.png" width="200" alt="Error Toast (Side-bar)"></td>
    <td><img src="https://raw.githubusercontent.com/dononcharles/NiceToastAssets/main/assets/img/shoot4.png" width="200" alt="Error Toast (Solid)"></td>
  </tr>
  <tr>
    <td><img src="https://raw.githubusercontent.com/dononcharles/NiceToastAssets/main/assets/img/shoot5.png" width="200" alt="Warning Toast (Side-bar)"></td>
    <td><img src="https://raw.githubusercontent.com/dononcharles/NiceToastAssets/main/assets/img/shoot6.png" width="200" alt="Warning Toast (Solid)"></td>
    <td><img src="https://raw.githubusercontent.com/dononcharles/NiceToastAssets/main/assets/img/shoot7.png" width="200" alt="Info Toast (Side-bar)"></td>
    <td><img src="https://raw.githubusercontent.com/dononcharles/NiceToastAssets/main/assets/img/shoot8.png" width="200" alt="Info Toast (Solid)"></td>
  </tr>
    <tr>
    <td><img src="https://raw.githubusercontent.com/dononcharles/NiceToastAssets/main/assets/img/shoot9.png" width="200" alt="Delete Toast (Side-bar)"></td>
    <td><img src="https://raw.githubusercontent.com/dononcharles/NiceToastAssets/main/assets/img/shoot10.png" width="200" alt="No Internet Toast (Side-bar)"></td>
  </tr>
</table>
</div>

### Dark Mode
<div style="text-align: center;">
<table>
  <tr>
    <td><img src="https://raw.githubusercontent.com/dononcharles/NiceToastAssets/main/assets/img/shoot11.png" width="200" alt="Success Toast Dark (Side-bar)"></td>
    <td><img src="https://raw.githubusercontent.com/dononcharles/NiceToastAssets/main/assets/img/shoot12.png" width="200" alt="Success Toast Dark (Solid)"></td>
    <td><img src="https://raw.githubusercontent.com/dononcharles/NiceToastAssets/main/assets/img/shoot13.png" width="200" alt="Error Toast Dark (Side-bar)"></td>
    <td><img src="https://raw.githubusercontent.com/dononcharles/NiceToastAssets/main/assets/img/shoot14.png" width="200" alt="Warning Toast Dark (Side-bar)"></td>
  </tr>
  <tr>
    <td><img src="https://raw.githubusercontent.com/dononcharles/NiceToastAssets/main/assets/img/shoot15.png" width="200" alt="Info Toast Dark (Side-bar)"></td>
    <td><img src="https://raw.githubusercontent.com/dononcharles/NiceToastAssets/main/assets/img/shoot16.png" width="200" alt="Delete Toast Dark (Side-bar)"></td>
    <td><img src="https://raw.githubusercontent.com/dononcharles/NiceToastAssets/main/assets/img/shoot17.png" width="200" alt="No Internet Toast Dark (Side-bar)"></td>
  </tr>
</table>
</div>

## 🚀 Installation

Since both libraries are available on **Maven Central**, you can easily add them to your project.

**1. Add the repository**

First, ensure that `mavenCentral()` is included in your `settings.gradle.kts` (or `settings.gradle`) file. It is usually added by default in new Android Studio projects.

```kotlin
// In settings.gradle.kts
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}
```

**2. Add the dependency**

Next, add the appropriate library to your app's `build.gradle.kts` (or `build.gradle`) file.

**Note:** Remember to replace `LATEST_VERSION` with the most recent version number, which you can find in the release badges at the top of this README.

### For Legacy Views (`nicetoast`)

**Kotlin (`build.gradle.kts`)**
```kotlin
dependencies {
    implementation("io.github.dononcharles:nicetoast:LATEST_VERSION")
}
```

**Groovy (`build.gradle`)**
```groovy
dependencies {
    implementation 'io.github.dononcharles:nicetoast:LATEST_VERSION'
}
```

### For Jetpack Compose (`cnicetoast`)

**Kotlin (`build.gradle.kts`)**
```kotlin
dependencies {
    implementation("io.github.dononcharles:cnicetoast:LATEST_COMPOSE_VERSION")
}
```

**Groovy (`build.gradle`)**
```groovy
dependencies {
    implementation 'io.github.dononcharles:cnicetoast:LATEST_COMPOSE_VERSION'
}
```

## 📖 How to Use

### For Legacy Views (`nicetoast`)

Displaying a toast is as simple as calling one of the built-in methods from your Activity.

```kotlin

 private val niceToast by lazy { NiceToast() }
 
 // All in one place by using magic method
 niceToast.magicCreate(
     activity = this,
     title = getString(R.string.edit_profile),
     message = getString(R.string.your_profile_was_updated_successfully),
     toastType = NiceToastType.SUCCESS,
     position = TOAST_GRAVITY_BOTTOM,
     duration = LONG_DURATION,
     font = ResourcesCompat.getFont(this, R.font.poppins_bold),
     isDarkMode = isDarkMode,
     isFullBackground = fullBackground
 )

// Success Toast (Side-bar style)
 niceToast.createNiceToast(
    activity = this,
    message = "Your profile was updated successfully!",
    toastType = NiceToastType.SUCCESS,
    position = Gravity.TOP,
    duration = 3000L,
    font = null // Optional: pass a Typeface object
)

// Error Toast (Solid background style)
 niceToast.createNiceToastWithBackground(
    activity = this,
    title = "Oh No!",
    message = "Failed to upload the file.",
    toastType = NiceToastType.ERROR,
    position = Gravity.BOTTOM,
    duration = 4000L,
    font = null
)
```

### For Jetpack Compose (`cnicetoast`)

In Compose, you can use the `CNiceToast` composable. You can control its visibility with a state variable.

```kotlin

// ... inside your Composable function

setContent {
    // --- SETUP FOR NEW (COMPOSE) LIBRARY ---
    val cNiceToastState = remember { CNiceToastState() }
    val scope = rememberCoroutineScope()
    val customCNiceToastConfig = remember {
        CNiceToastConfiguration().copy(
            successToastColor = R.color.success_color,
            errorToastColor = R.color.error_color,
            warningToastColor = R.color.warning_color,
            infoToastColor = R.color.info_color,
            // You can add background colors here too if needed
        )
    }
    val defaultCNiceToastConfig = remember { CNiceToastConfiguration() }
    val currentComposeConfig = if (useCustomToasts) customCNiceToastConfig else defaultCNiceToastConfig

    CompositionLocalProvider(LocalCNiceToastConfig provides currentComposeConfig) {
        NiceToastTheme(darkTheme = isDarkMode) {
            MyScreen(
                onBtnClick = {
                    scope.launch {
                        cNiceToastState.show(
                            title = getString(R.string.low_battery),
                            message = getString(R.string.your_battery_is_running_low_please_connect_to_a_charger),
                            type = CNiceToastType.WARNING,
                            isDarkMode = isDarkMode,
                            isFullBackground = fullBackground
                        )
                    }
                }
            )

            // The Compose Host is ready to show toasts
            CNiceToastHost(hostState = cNiceToastState, modifier = Modifier.systemBarsPadding())
        }
    }
}
```

### Toast Types

Both libraries use the same toast types:

*   `NiceToastType.SUCCESS`
*   `NiceToastType.ERROR`
*   `NiceToastType.WARNING`
*   `NiceToastType.INFO`
*   `NiceToastType.DELETE`
*   `NiceToastType.NO_INTERNET`

### Global Configuration

You can customize the look and feel of all toasts globally. This is perfect for matching the library to your app's brand colors. It's recommended to do this once in your `Application` class.

This applies to `nicetoast` (View-based).

```kotlin
 private fun setCustomizedNiceToastColors(useMyColor: Boolean) {
    if (useMyColor) {
        niceToast.configure {
            setSuccessColor(R.color.success_color)
            setSuccessBackgroundColor(R.color.success_bg_color)

            setErrorColor(R.color.error_color)
            setErrorBackgroundColor(R.color.error_bg_color)

            setDeleteColor(R.color.delete_color)
            setDeleteBackgroundColor(R.color.delete_bg_color)

            setWarningColor(R.color.warning_color)
            setWarningBackgroundColor(R.color.warning_bg_color)

            setInfoColor(R.color.info_color)
            setInfoBackgroundColor(R.color.info_bg_color)
        }
    } else {
        niceToast.resetAll()
    }
}
```

This applies to `cnicetoast` (Compose-based).

```kotlin
setContent {
    val customCNiceToastConfig = remember {
        CNiceToastConfiguration().copy(
            successToastColor = R.color.success_color,
            errorToastColor = R.color.error_color,
            warningToastColor = R.color.warning_color,
            infoToastColor = R.color.info_color,
            // You can add background colors here too if needed
        )
    }

    val defaultCNiceToastConfig = remember { CNiceToastConfiguration() }
    val currentComposeConfig = if (useCustomToasts) customCNiceToastConfig else defaultCNiceToastConfig

    CompositionLocalProvider(LocalCNiceToastConfig provides currentComposeConfig) {
        MyComposeScreen(darkTheme = isDarkMode) {

        }
    }
}
```

## 💖 Buy Me a Coffee?

`NiceToast` is an open-source project that I maintain in my free time. If you've found it valuable for your own projects, please consider making a contribution.

Your support helps cover the costs of development and allows me to invest more time into:
*   Adding new features and animations
*   Providing faster bug fixes and support
*   Keeping the library up-to-date with the latest Android versions

**Your contribution, no matter the size, makes a big difference.**
<div style="text-align: center;">

👉 [Donate via PayPal](https://www.paypal.com/paypalme/komidonon)

</div>
