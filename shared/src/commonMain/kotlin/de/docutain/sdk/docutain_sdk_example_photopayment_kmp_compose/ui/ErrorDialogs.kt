package de.docutain.sdk.docutain_sdk_example_photopayment_kmp_compose.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalUriHandler
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.Res
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.action_ok
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.license_error_action
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.license_error_message
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.license_error_title
import org.jetbrains.compose.resources.stringResource

/** Tells the user that the photo payment failed, with [message] as the SDK reported it. */
@Composable
fun ErrorDialog(message: String, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        text = { Text(message) },
        confirmButton = {
            TextButton(onClick = onDismiss) { Text(stringResource(Res.string.action_ok)) }
        },
    )
}

/**
 * Tells the user that the SDK refused the license key at start, e.g. because it expired. It
 * cannot be dismissed, because nothing works in that state - the way out is the support.
 */
@Composable
fun LicenseErrorDialog() {
    val uriHandler = LocalUriHandler.current
    AlertDialog(
        onDismissRequest = {},
        title = { Text(stringResource(Res.string.license_error_title)) },
        text = { Text(stringResource(Res.string.license_error_message)) },
        confirmButton = {
            TextButton(onClick = { uriHandler.openUri(DocutainLink.Contact.url) }) {
                Text(stringResource(Res.string.license_error_action))
            }
        },
    )
}
