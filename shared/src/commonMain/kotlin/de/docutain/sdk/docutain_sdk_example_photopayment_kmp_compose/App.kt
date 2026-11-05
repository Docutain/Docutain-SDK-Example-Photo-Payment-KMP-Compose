package de.docutain.sdk.docutain_sdk_example_photopayment_kmp_compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigationevent.NavigationEventInfo
import androidx.navigationevent.compose.NavigationBackHandler
import androidx.navigationevent.compose.rememberNavigationEventState
import de.docutain.sdk.docutain_sdk_example_photopayment_kmp_compose.payment.ExternalFiles
import de.docutain.sdk.docutain_sdk_example_photopayment_kmp_compose.ui.DocutainTheme
import de.docutain.sdk.docutain_sdk_example_photopayment_kmp_compose.ui.ErrorDialog
import de.docutain.sdk.docutain_sdk_example_photopayment_kmp_compose.ui.HomeScreen
import de.docutain.sdk.docutain_sdk_example_photopayment_kmp_compose.ui.LicenseErrorDialog
import de.docutain.sdk.docutain_sdk_example_photopayment_kmp_compose.ui.OptionsSheet
import de.docutain.sdk.docutain_sdk_example_photopayment_kmp_compose.ui.ResultScreen
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.Res
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.error_payment_failed
import org.jetbrains.compose.resources.stringResource

@Composable
fun App(viewModel: PaymentViewModel = viewModel { PaymentViewModel() }) {
    DocutainTheme {
        var showOptions by remember { mutableStateOf(false) }

        //an invoice shared with or opened in the app starts the photo payment right away
        val externalFiles by ExternalFiles.pending.collectAsState()
        LaunchedEffect(externalFiles, viewModel.isRunning) {
            if (externalFiles.isNotEmpty() && !viewModel.isRunning && viewModel.isSdkInitialized) {
                viewModel.start(ExternalFiles.take())
            }
        }

        //back leaves the result screen instead of the app
        NavigationBackHandler(
            state = rememberNavigationEventState(NavigationEventInfo.None),
            isBackEnabled = viewModel.result != null,
            onBackCompleted = { viewModel.result = null },
        )

        val shown = viewModel.result
        if (shown == null) {
            HomeScreen(
                isSdkInitialized = viewModel.isSdkInitialized,
                isRunning = viewModel.isRunning,
                onStart = { viewModel.start() },
                onOpenOptions = { showOptions = true },
            )
        } else {
            ResultScreen(
                json = shown.json,
                data = shown.data,
                onBack = { viewModel.result = null },
                onStartAgain = { viewModel.start() },
            )
        }

        viewModel.failure?.let {
            ErrorDialog(
                message = it.reason.ifBlank { stringResource(Res.string.error_payment_failed) },
                onDismiss = { viewModel.failure = null },
            )
        }

        //nothing works without a license key the SDK accepts, so this one cannot be dismissed
        if (!viewModel.isSdkInitialized) {
            LicenseErrorDialog()
        }

        if (showOptions) {
            OptionsSheet(
                options = viewModel.options,
                onOptionsChange = { viewModel.options = it },
                onDismiss = { showOptions = false },
            )
        }
    }
}
