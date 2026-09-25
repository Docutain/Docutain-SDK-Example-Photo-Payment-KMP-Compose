package de.docutain.sdk.docutain_sdk_example_photopayment_kmp_compose.payment

import de.docutain.sdk.kmp.DocutainSdk
import de.docutain.sdk.kmp.PhotoPaymentConfiguration
import kotlinx.coroutines.CancellationException

/**
 * The parts of the SDK's [PhotoPaymentConfiguration] this sample lets the user change.
 *
 * The defaults are the defaults of the SDK, except that the payment state is read as well -
 * it is part of what the SDK can do, and shown in the raw JSON.
 */
data class PaymentOptions(
    val allowGiroCode: Boolean = true,
    val readBIC: Boolean = true,
    val readPaymentState: Boolean = true,
    val allowPageEditing: Boolean = true,
)

/** What came back from the photo payment. */
sealed interface PaymentOutcome {

    /** The user finished, [json] holds the payment data as the SDK returned it, [data] parsed. */
    data class Detected(val json: String, val data: PaymentData) : PaymentOutcome

    /** The user left the scanner without finishing. */
    data object Cancelled : PaymentOutcome

    /** The photo payment failed, [reason] as the SDK reported it. */
    data class Failed(val reason: String) : PaymentOutcome
}

/**
 * Starts the photo payment of the Docutain SDK and waits until the user is done.
 *
 * Without [externalFiles] the SDK opens the camera. With them - invoices another app handed
 * over, see [ExternalFiles] - it works on these files instead.
 */
suspend fun startPhotoPayment(
    options: PaymentOptions,
    externalFiles: List<String> = emptyList(),
): PaymentOutcome {
    //a few of the options, the documentation lists all of them
    val config = PhotoPaymentConfiguration().apply {
        //reads the payment data straight from a GiroCode (EPC QR code) when the invoice has one
        allowGiroCode = options.allowGiroCode
        //false skips the editing screen after the photo, which makes the process even faster
        allowPageEditing = options.allowPageEditing
        //what the analysis reads besides IBAN, amount, recipient and reference
        analyzeConfig.readBIC = options.readBIC
        analyzeConfig.readPaymentState = options.readPaymentState
    }

    return try {
        val json = if (externalFiles.isEmpty()) {
            //opens the scanner and returns the detected data as JSON once the user is done
            DocutainSdk.startPhotoPayment(config)
        } else {
            startPhotoPaymentWithExternalFiles(externalFiles, config)
        }
        //null is how the SDK reports that the user left the scanner
        if (json == null) {
            PaymentOutcome.Cancelled
        } else {
            PaymentOutcome.Detected(json, PaymentData.parse(json))
        }
    } catch (cancellation: CancellationException) {
        throw cancellation
    } catch (error: Exception) {
        //a DocutainException carries the message of the SDK, the JSON parser what it could not read
        PaymentOutcome.Failed(error.message.orEmpty())
    }
}

/**
 * Hands [files] received from another app to the SDK. The platforms take them differently:
 * one or more `content://` URIs on Android, exactly one `file://` URL on iOS.
 */
internal expect suspend fun startPhotoPaymentWithExternalFiles(
    files: List<String>,
    config: PhotoPaymentConfiguration,
): String?
