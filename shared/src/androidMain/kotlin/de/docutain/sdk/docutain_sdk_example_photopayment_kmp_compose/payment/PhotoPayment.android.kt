package de.docutain.sdk.docutain_sdk_example_photopayment_kmp_compose.payment

import de.docutain.sdk.kmp.DocutainSdk
import de.docutain.sdk.kmp.PhotoPaymentConfiguration

//one or more content:// URIs, as MainActivity reads them from the intent
internal actual suspend fun startPhotoPaymentWithExternalFiles(
    files: List<String>,
    config: PhotoPaymentConfiguration,
): String? = DocutainSdk.startPhotoPaymentWithExternalFiles(files, config)
