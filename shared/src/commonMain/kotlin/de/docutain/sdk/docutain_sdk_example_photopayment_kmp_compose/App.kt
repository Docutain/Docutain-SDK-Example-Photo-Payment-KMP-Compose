package de.docutain.sdk.docutain_sdk_example_photopayment_kmp_compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigationevent.NavigationEventInfo
import androidx.navigationevent.compose.NavigationBackHandler
import androidx.navigationevent.compose.rememberNavigationEventState
import de.docutain.sdk.docutain_sdk_example_photopayment_kmp_compose.payment.ExternalFiles
import de.docutain.sdk.docutain_sdk_example_photopayment_kmp_compose.payment.PaymentOptions
import de.docutain.sdk.docutain_sdk_example_photopayment_kmp_compose.payment.PaymentOutcome
import de.docutain.sdk.docutain_sdk_example_photopayment_kmp_compose.payment.initDocutainSdk
import de.docutain.sdk.docutain_sdk_example_photopayment_kmp_compose.payment.startPhotoPayment
import de.docutain.sdk.docutain_sdk_example_photopayment_kmp_compose.ui.DocutainTheme
import de.docutain.sdk.docutain_sdk_example_photopayment_kmp_compose.ui.ErrorDialog
import de.docutain.sdk.docutain_sdk_example_photopayment_kmp_compose.ui.HomeScreen
import de.docutain.sdk.docutain_sdk_example_photopayment_kmp_compose.ui.LicenseErrorDialog
import de.docutain.sdk.docutain_sdk_example_photopayment_kmp_compose.ui.OptionsSheet
import de.docutain.sdk.docutain_sdk_example_photopayment_kmp_compose.ui.ResultScreen
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.Res
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.error_payment_failed
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

@Composable
fun App() {
    //the SDK is initialized once when the app starts, before anything else uses it
    val isSdkInitialized = remember { initDocutainSdk() }

    DocutainTheme {
        val scope = rememberCoroutineScope()
        //why the last photo payment failed, shown until the user dismisses it
        var failure by remember { mutableStateOf<PaymentOutcome.Failed?>(null) }
        var options by remember { mutableStateOf(PaymentOptions()) }
        var showOptions by remember { mutableStateOf(false) }
        var isRunning by remember { mutableStateOf(false) }
        //the result of the last photo payment process, shown until the user goes back
        var result by remember { mutableStateOf<PaymentOutcome.Detected?>(null) }

        fun start(externalFiles: List<String> = emptyList()) {
            if (isRunning) return
            isRunning = true
            scope.launch {
                val outcome = try {
                    startPhotoPayment(options, externalFiles)
                } finally {
                    isRunning = false
                }

                when (outcome) {
                    is PaymentOutcome.Detected -> result = outcome
                    //leaving the scanner is a normal way out, nothing to report - but the
                    //previous result is not what the user came back for, so back to the start
                    PaymentOutcome.Cancelled -> result = null
                    is PaymentOutcome.Failed -> {
                        result = null
                        failure = outcome
                    }
                }
            }
        }

        //an invoice shared with or opened in the app starts the photo payment right away
        val externalFiles by ExternalFiles.pending.collectAsState()
        LaunchedEffect(externalFiles, isRunning) {
            if (externalFiles.isNotEmpty() && !isRunning && isSdkInitialized) {
                start(ExternalFiles.take())
            }
        }

        //back leaves the result screen instead of the app
        NavigationBackHandler(
            state = rememberNavigationEventState(NavigationEventInfo.None),
            isBackEnabled = result != null,
            onBackCompleted = { result = null },
        )

        val shown = result
        if (shown == null) {
            HomeScreen(
                isSdkInitialized = isSdkInitialized,
                isRunning = isRunning,
                onStart = { start() },
                onOpenOptions = { showOptions = true },
            )
        } else {
            ResultScreen(
                json = shown.json,
                data = shown.data,
                onBack = { result = null },
                onStartAgain = { start() },
            )
        }

        failure?.let {
            ErrorDialog(
                message = it.reason.ifBlank { stringResource(Res.string.error_payment_failed) },
                onDismiss = { failure = null },
            )
        }

        //nothing works without a license key the SDK accepts, so this one cannot be dismissed
        if (!isSdkInitialized) {
            LicenseErrorDialog()
        }

        if (showOptions) {
            OptionsSheet(
                options = options,
                onOptionsChange = { options = it },
                onDismiss = { showOptions = false },
            )
        }
    }
}
