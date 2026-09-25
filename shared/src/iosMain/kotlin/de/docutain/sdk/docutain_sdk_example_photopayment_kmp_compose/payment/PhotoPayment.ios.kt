package de.docutain.sdk.docutain_sdk_example_photopayment_kmp_compose.payment

import de.docutain.sdk.kmp.DocutainSdk
import de.docutain.sdk.kmp.PhotoPaymentConfiguration

//the SDK takes exactly one file:// URL on iOS, which is what "Open in" hands over
internal actual suspend fun startPhotoPaymentWithExternalFiles(
    files: List<String>,
    config: PhotoPaymentConfiguration,
): String? = DocutainSdk.startPhotoPaymentWithExternalFiles(files.take(1), config)
