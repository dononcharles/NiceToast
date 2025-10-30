package com.dononcharles.nicetoastapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Preview
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.res.ResourcesCompat
import androidx.core.net.toUri
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
            var useCustomToasts by remember { mutableStateOf(false) }
            var isDarkMode by remember { mutableStateOf(false) }
            var fullBackground by remember { mutableStateOf(false) }
            var showDialog by remember { mutableStateOf(false) }
            val context = LocalContext.current

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text(stringResource(R.string.buy_me_a_coffee)) },
                    text = { Text(stringResource(R.string.do_you_want_to_buy_me_a_coffee)) },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, BuildConfig.DONATE_LINK.toUri())
                                context.startActivity(intent)
                                showDialog = false
                            }
                        ) {
                            Text("Yes".uppercase())
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDialog = false }) {
                            Text("No")
                        }
                    }
                )
            }

            NiceToastTheme(darkTheme = isDarkMode) {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text(stringResource(R.string.app_name)) },
                            navigationIcon = {
                                Icon(
                                    imageVector = Icons.Default.Preview,
                                    tint = colorResource(R.color.white),
                                    contentDescription = stringResource(R.string.app_name)
                                )
                            },
                            actions = {
                                IconButton(onClick = { showDialog = true }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.baseline_coffee_24),
                                        tint = colorResource(R.color.warning_color),
                                        contentDescription = stringResource(R.string.buy_me_a_coffee)
                                    )
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                titleContentColor = MaterialTheme.colorScheme.onPrimary,
                                navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        )
                    }
                ) { innerPadding ->
                    val poppinsBold = FontFamily(Font(R.font.poppins_bold))

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
                                title = getString(R.string.edit_profile),
                                message = getString(R.string.your_profile_was_updated_successfully),
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
                                title = getString(R.string.upload_failed),
                                message = getString(R.string.there_was_an_error_uploading_your_file_please_try_again),
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
                                title = getString(R.string.low_battery),
                                message = getString(R.string.your_battery_is_running_low_please_connect_to_a_charger),
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
                                title = getString(R.string.new_update_available),
                                message = getString(R.string.a_new_version_of_the_app_is_available_please_update_to_the_latest_version),
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
                                title = getString(R.string.delete_account),
                                message = getString(R.string.your_account_has_been_deleted_successfully),
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
                                title = getString(R.string.no_internet_connection),
                                message = getString(R.string.please_check_your_internet_connection_and_try_again),
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
            .background(color = MaterialTheme.colorScheme.background),
    ) {
        val scrollState = rememberScrollState()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(start = 72.dp, end = 72.dp, bottom = 32.dp)
                .verticalScroll(scrollState)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Switch(
                    checked = useCustom,
                    onCheckedChange = onSwitchChange
                )
                Text(text = stringResource(R.string.custom_colors), modifier = Modifier.padding(start = 8.dp), color = MaterialTheme.colorScheme.onBackground)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = fullBackground,
                    onCheckedChange = onFullBackgroundChecked
                )
                Text(text = stringResource(R.string.full_background), modifier = Modifier.padding(start = 8.dp), color = MaterialTheme.colorScheme.onBackground)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isDarkMode,
                    onCheckedChange = onDarkModeChecked
                )
                Text(text = stringResource(R.string.dark_mode), modifier = Modifier.padding(start = 8.dp), color = MaterialTheme.colorScheme.onBackground)
            }

            Button(
                onClick = onSuccessClick,
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.success).uppercase(), fontFamily = fontFamily, color = MaterialTheme.colorScheme.onPrimary)
            }
            Button(
                onClick = onErrorClick,
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.error).uppercase(), fontFamily = fontFamily, color = MaterialTheme.colorScheme.onError)
            }
            Button(
                onClick = onWarningClick,
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.warning).uppercase(), fontFamily = fontFamily, color = MaterialTheme.colorScheme.onTertiary)
            }
            Button(
                onClick = onInfoClick,
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.info).uppercase(), fontFamily = fontFamily, color = MaterialTheme.colorScheme.onSecondary)
            }
            Button(
                onClick = onDeleteClick,
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.errorContainer),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.delete).uppercase(), fontFamily = fontFamily, color = MaterialTheme.colorScheme.onErrorContainer)
            }
            Button(
                onClick = onNoInternetClick,
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.no_internet).uppercase(), fontFamily = fontFamily, color = MaterialTheme.colorScheme.onTertiary)
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
