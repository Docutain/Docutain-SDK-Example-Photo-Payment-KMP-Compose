package de.docutain.sdk.docutain_sdk_example_photopayment_kmp_compose

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import de.docutain.sdk.docutain_sdk_example_photopayment_kmp_compose.payment.ExternalFiles

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        //started by "Share" or "Open with" from another app - not again when the activity is recreated
        if (savedInstanceState == null) {
            ExternalFiles.receive(intent.externalFileUris())
        }

        setContent {
            App()
        }
    }

    //the app was already running, launchMode singleTask delivers the next invoice here
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        ExternalFiles.receive(intent.externalFileUris())
    }
}

/**
 * The invoices another app shared with ("Share") or opened in ("Open with") this app, as the
 * `content://` URIs the SDK reads them from. Empty for a normal start from the launcher.
 */
@Suppress("DEPRECATION") //the typed getParcelableExtra needs API 33, minSdk is 24
private fun Intent.externalFileUris(): List<String> {
    val uris = when (action) {
        Intent.ACTION_VIEW -> listOfNotNull(data)
        Intent.ACTION_SEND -> listOfNotNull(getParcelableExtra<Uri>(Intent.EXTRA_STREAM))
        Intent.ACTION_SEND_MULTIPLE -> getParcelableArrayListExtra<Uri>(Intent.EXTRA_STREAM).orEmpty()
        else -> emptyList()
    }
    return uris.filter { it.scheme == "content" }.map { it.toString() }
}
