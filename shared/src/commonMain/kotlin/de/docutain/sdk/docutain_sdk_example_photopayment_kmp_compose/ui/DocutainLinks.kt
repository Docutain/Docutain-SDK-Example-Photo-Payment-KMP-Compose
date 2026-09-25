package de.docutain.sdk.docutain_sdk_example_photopayment_kmp_compose.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.Res
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.link_contact
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.link_documentation
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.link_website
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

/** Where someone who liked the sample goes next. */
enum class DocutainLink(val label: StringResource, val url: String) {
    Website(Res.string.link_website, "https://sdk.docutain.com"),
    Documentation(Res.string.link_documentation, "https://docs.docutain.com/docs/kmp/photoPayment"),
    Contact(Res.string.link_contact, "https://sdk.docutain.com/#Contact"),
}

/** [links] as a row of text buttons separated by dots, opened in the browser. */
@Composable
fun DocutainLinks(links: List<DocutainLink>, modifier: Modifier = Modifier) {
    val uriHandler = LocalUriHandler.current
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        links.forEachIndexed { index, link ->
            if (index > 0) {
                Text("·", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            TextButton(onClick = { uriHandler.openUri(link.url) }) {
                Text(stringResource(link.label))
            }
        }
    }
}
