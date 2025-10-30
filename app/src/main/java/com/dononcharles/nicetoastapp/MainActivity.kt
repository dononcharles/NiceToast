package com.dononcharles.nicetoastapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.res.ResourcesCompat
import com.dononcharles.nicetoast.LONG_DURATION
import com.dononcharles.nicetoast.NiceToast
import com.dononcharles.nicetoast.NiceToastType
import com.dononcharles.nicetoast.TOAST_GRAVITY_BOTTOM

class MainActivity : ComponentActivity() {

    private val niceToast by lazy { NiceToast() }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NiceToastTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text("Nice Toast") }
                        )
                    }
                ) { innerPadding ->
                    val poppinsBold = FontFamily(Font(R.font.poppins_bold))

                    var useCustomToasts by remember { mutableStateOf(false) }
                    var isDarkMode by remember { mutableStateOf(false) }
                    var fullBackground by remember { mutableStateOf(false) }

                    MainScreen(
                        modifier = Modifier.padding(innerPadding),
                        fontFamily = poppinsBold,
                        useCustom = useCustomToasts,
                        onSwitchChange = { isChecked ->
                            useCustomToasts = isChecked
                            setCustomizedNiceToastColors(useCustomToasts)
                        },
                        isDarkMode = isDarkMode,
                        onDarkModeChecked = { isChecked ->
                            isDarkMode = isChecked
                        },
                        fullBackground = fullBackground,
                        onFullBackgroundChecked = { isChecked ->
                            fullBackground = isChecked
                        },
                        onSuccessClick = {
                            niceToast.magicCreate(
                                activity = this,
                                title = "Edit Profile",
                                message = "Your profile was updated successfully!",
                                toastType = NiceToastType.SUCCESS,
                                position = TOAST_GRAVITY_BOTTOM,
                                duration = LONG_DURATION,
                                font = ResourcesCompat.getFont(this, R.font.poppins_bold),
                                isDarkMode = isDarkMode,
                                isFullBackground = fullBackground
                            )
                        },
                        onErrorClick = {
                            niceToast.magicCreate(
                                activity = this,
                                title = "Upload Failed",
                                message = "There was an error uploading your file. Please try again.",
                                toastType = NiceToastType.ERROR,
                                position = TOAST_GRAVITY_BOTTOM,
                                duration = LONG_DURATION,
                                font = ResourcesCompat.getFont(this, R.font.poppins_bold),
                                isDarkMode = isDarkMode,
                                isFullBackground = fullBackground
                            )
                        },
                        onWarningClick = {
                            niceToast.magicCreate(
                                activity = this,
                                title = "Low Battery",
                                message = "Your battery is running low. Please connect to a charger.",
                                toastType = NiceToastType.WARNING,
                                position = TOAST_GRAVITY_BOTTOM,
                                duration = LONG_DURATION,
                                font = ResourcesCompat.getFont(this, R.font.poppins_bold),
                                isDarkMode = isDarkMode,
                                isFullBackground = fullBackground
                            )
                        },
                        onInfoClick = {
                            niceToast.magicCreate(
                                activity = this,
                                title = "New Update Available",
                                message = "A new version of the app is available. Please update to the latest version.",
                                toastType = NiceToastType.INFO,
                                position = TOAST_GRAVITY_BOTTOM,
                                duration = LONG_DURATION,
                                font = ResourcesCompat.getFont(this, R.font.poppins_bold),
                                isDarkMode = isDarkMode,
                                isFullBackground = fullBackground
                            )
                        },
                        onDeleteClick = {
                            niceToast.magicCreate(
                                activity = this,
                                title = "Delete Account",
                                message = "Your account has been deleted successfully.",
                                toastType = NiceToastType.DELETE,
                                position = TOAST_GRAVITY_BOTTOM,
                                duration = LONG_DURATION,
                                font = ResourcesCompat.getFont(this, R.font.poppins_bold),
                                isDarkMode = isDarkMode,
                                isFullBackground = fullBackground
                            )
                        },
                        onNoInternetClick = {
                            niceToast.magicCreate(
                                activity = this,
                                title = "No Internet Connection",
                                message = "Please check your internet connection and try again.",
                                toastType = NiceToastType.NO_INTERNET,
                                position = TOAST_GRAVITY_BOTTOM,
                                duration = LONG_DURATION,
                                font = ResourcesCompat.getFont(this, R.font.poppins_bold),
                                isDarkMode = isDarkMode,
                                isFullBackground = fullBackground
                            )
                        }
                    )
                }
            }
        }
    }

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
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    fontFamily: FontFamily,
    useCustom: Boolean,
    isDarkMode: Boolean,
    onDarkModeChecked: (Boolean) -> Unit,
    fullBackground: Boolean,
    onFullBackgroundChecked: (Boolean) -> Unit,
    onSwitchChange: (Boolean) -> Unit,
    onSuccessClick: () -> Unit,
    onErrorClick: () -> Unit,
    onWarningClick: () -> Unit,
    onInfoClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onNoInternetClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = androidx.compose.ui.graphics.Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(start = 72.dp, end = 72.dp, bottom = 32.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = fullBackground,
                    onCheckedChange = onFullBackgroundChecked
                )
                Text(text = "Full Background", modifier = Modifier.padding(start = 8.dp))
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isDarkMode,
                    onCheckedChange = onDarkModeChecked
                )
                Text(text = "Dark Mode", modifier = Modifier.padding(start = 8.dp))
            }

            Button(
                onClick = onSuccessClick,
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.success_color)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Success".uppercase(), fontFamily = fontFamily)
            }
            Button(
                onClick = onErrorClick,
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.error_color)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Error".uppercase(), fontFamily = fontFamily)
            }
            Button(
                onClick = onWarningClick,
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.warning_color)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Warning".uppercase(), fontFamily = fontFamily)
            }
            Button(
                onClick = onInfoClick,
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.info_color)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Info".uppercase(), fontFamily = fontFamily)
            }
            Button(
                onClick = onDeleteClick,
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.delete_color)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Delete".uppercase(), fontFamily = fontFamily)
            }
            Button(
                onClick = onNoInternetClick,
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.warning_color)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "No Internet".uppercase(), fontFamily = fontFamily)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Switch(
                    checked = useCustom,
                    onCheckedChange = onSwitchChange
                )
                Text(text = "Use custom colors", modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    val poppinsBold = FontFamily(Font(R.font.poppins_bold))
    NiceToastTheme {
        MainScreen(
            fontFamily = poppinsBold,
            useCustom = true,
            onSwitchChange = { },
            isDarkMode = true,
            onDarkModeChecked = { },
            fullBackground = true,
            onFullBackgroundChecked = { },
            onSuccessClick = { },
            onErrorClick = { },
            onWarningClick = { },
            onInfoClick = { },
            onDeleteClick = { },
            onNoInternetClick = {}
        )
    }
}