package de.docutain.sdk.docutain_sdk_example_photopayment_kmp_compose.payment

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate

/**
 * Invoices another app handed over via "Share" or "Open with", waiting for the photo payment.
 *
 * The platforms put them in - `MainActivity` on Android, `openExternalFile` on iOS - and the
 * app starts the photo payment with them: readable `content://` URIs on Android, one `file://`
 * URL on iOS, as `startPhotoPaymentWithExternalFiles` of the SDK expects them.
 */
object ExternalFiles {

    private val files = MutableStateFlow<List<String>>(emptyList())

    /** The files not processed yet, empty when there are none. */
    val pending: StateFlow<List<String>> = files.asStateFlow()

    /** Hands over [uris] received from another app. */
    fun receive(uris: List<String>) {
        if (uris.isNotEmpty()) files.value = uris
    }

    /** Returns the pending files and forgets them, so they are processed only once. */
    fun take(): List<String> = files.getAndUpdate { emptyList() }
}
