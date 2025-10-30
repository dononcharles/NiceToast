# Nice Toast for Android

[![API](https://img.shields.io/badge/API-24%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=24)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Kotlin](https://img.shields.io/badge/Kotlin-100%25-blue.svg?style=flat)](https://kotlinlang.org/)


Nice Toast is a stunning and highly customizable toast library for Android written in Kotlin. Elevate your app's user experience by replacing standard toasts with eye-catching, animated, and informative notifications that look great in both light and dark themes.

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
*   **Flexible & Easy to Use**: A simple, fluent API makes it effortless to display toasts from any Activity.
*   **Position Control**: Show toasts at the top, center, or bottom of the screen.
*   **Built with ViewBinding**: Modern, safe, and efficient view handling.

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

Add the dependency to your app's `build.gradle.kts` or `build.gradle` file:

### Kotlin (`build.gradle.kts`)

```kotlin
// In build.gradle.kts
implementation("com.github.yourusername:nicetoast:1.0.0")
```

### Groovy (`build.gradle`)

```groovy
// In build.gradle
implementation 'com.github.yourusername:nicetoast:1.0.0'
```

## 📖 How to Use

### Basic Toasts

Displaying a toast is as simple as calling one of the built-in methods from your Activity.

```kotlin
// Success Toast (Side-bar style)
NiceToast().createNiceToast(
    activity = this,
    message = "Your profile was updated successfully!",
    toastType = NiceToastType.SUCCESS,
    position = Gravity.TOP,
    duration = 3000L,
    font = null // Optional: pass a Typeface object
)

// Error Toast (Solid background style)
NiceToast().createNiceToastWithBackground(
    activity = this,
    title = "Oh No!",
    message = "Failed to upload the file.",
    toastType = NiceToastType.ERROR,
    position = Gravity.BOTTOM,
    duration = 4000L,
    font = null
)

// Dark Mode Warning Toast
NiceToast().createDarkNiceToast(
    activity = this,
    message = "Please check your network connection.",
    toastType = NiceToastType.WARNING,
    position = Gravity.CENTER,
    duration = 3000L,
    font = null
)
```

### All-in-One Function

For more advanced control, you can use the `magicCreate` function, which allows you to configure every aspect of the toast in a single call. This is useful when you need to dynamically set properties like dark mode or the background style.

```kotlin
niceToast.magicCreate(
    activity = this,
    title = getString(R.string.low_battery),
    message = getString(R.string.your_battery_is_running_low_please_connect_to_a_charger),
    toastType = NiceToastType.WARNING,
    position = TOAST_GRAVITY_BOTTOM,
    duration = LONG_DURATION,
    font = ResourcesCompat.getFont(this, R.font.poppins_bold),
    isDarkMode = isDarkMode,
    isFullBackground = fullBackground
)
```

### Toast Types

The library includes several predefined types:

*   `NiceToastType.SUCCESS`
*   `NiceToastType.ERROR`
*   `NiceToastType.WARNING`
*   `NiceToastType.INFO`
*   `NiceToastType.DELETE`
*   `NiceToastType.NO_INTERNET`

### Global Configuration

You can customize the look and feel of all toasts globally. This is perfect for matching the library to your app's brand colors. It's recommended to do this once in your `Application` class.

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
       // To reset all configurations to their default values
        niceToast.resetAll()
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