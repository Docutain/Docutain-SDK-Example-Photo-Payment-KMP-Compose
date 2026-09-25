package de.docutain.sdk.docutain_sdk_example_photopayment_kmp_compose

import androidx.compose.ui.window.ComposeUIViewController
import de.docutain.sdk.docutain_sdk_example_photopayment_kmp_compose.payment.ExternalFiles

fun MainViewController() = ComposeUIViewController { App() }

/** Hands over the `file://` [url] of an invoice another app shared or opened in this app. */
fun openExternalFile(url: String) = ExternalFiles.receive(listOf(url))
