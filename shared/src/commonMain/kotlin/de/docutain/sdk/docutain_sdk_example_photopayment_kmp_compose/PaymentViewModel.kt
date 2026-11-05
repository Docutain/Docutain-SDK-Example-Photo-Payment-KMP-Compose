package de.docutain.sdk.docutain_sdk_example_photopayment_kmp_compose

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.docutain.sdk.docutain_sdk_example_photopayment_kmp_compose.payment.PaymentOptions
import de.docutain.sdk.docutain_sdk_example_photopayment_kmp_compose.payment.PaymentOutcome
import de.docutain.sdk.docutain_sdk_example_photopayment_kmp_compose.payment.initDocutainSdk
import de.docutain.sdk.docutain_sdk_example_photopayment_kmp_compose.payment.startPhotoPayment
import kotlinx.coroutines.launch

/**
 * Runs the photo payment and holds its result.
 *
 * A ViewModel outlives the Activity: when Android recreates it while the scanner is open,
 * e.g. because the font size changed, the photo payment keeps waiting here and its result
 * still arrives. Started from a scope of the UI, the result would be lost with the Activity.
 */
class PaymentViewModel : ViewModel() {

    //the SDK is initialized once when the app starts, before anything else uses it
    val isSdkInitialized = initDocutainSdk()

    var options by mutableStateOf(PaymentOptions())
    var isRunning by mutableStateOf(false)
        private set
    //the result of the last photo payment process, shown until the user goes back
    var result by mutableStateOf<PaymentOutcome.Detected?>(null)
    //why the last photo payment failed, shown until the user dismisses it
    var failure by mutableStateOf<PaymentOutcome.Failed?>(null)

    fun start(externalFiles: List<String> = emptyList()) {
        if (isRunning) return
        isRunning = true
        viewModelScope.launch {
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
}
