package de.docutain.sdk.docutain_sdk_example_photopayment_kmp_compose.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import de.docutain.sdk.docutain_sdk_example_photopayment_kmp_compose.payment.PaymentOptions
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.Res
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.option_bic_subtitle
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.option_bic_title
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.option_girocode_subtitle
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.option_girocode_title
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.option_payment_state_subtitle
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.option_payment_state_title
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.option_skip_editing_subtitle
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.option_skip_editing_title
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.options_more
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.options_more_link
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.options_title
import org.jetbrains.compose.resources.stringResource

/** The options of the photo payment process, kept off the start screen to keep it calm. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OptionsSheet(
    options: PaymentOptions,
    onOptionsChange: (PaymentOptions) -> Unit,
    onDismiss: () -> Unit,
) {
    val uriHandler = LocalUriHandler.current
    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(modifier = Modifier.navigationBarsPadding().padding(bottom = 16.dp)) {
            Text(
                stringResource(Res.string.options_title),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
            )
            OptionRow(
                title = stringResource(Res.string.option_girocode_title),
                subtitle = stringResource(Res.string.option_girocode_subtitle),
                checked = options.allowGiroCode,
                onCheckedChange = { onOptionsChange(options.copy(allowGiroCode = it)) },
            )
            OptionRow(
                title = stringResource(Res.string.option_bic_title),
                subtitle = stringResource(Res.string.option_bic_subtitle),
                checked = options.readBIC,
                onCheckedChange = { onOptionsChange(options.copy(readBIC = it)) },
            )
            OptionRow(
                title = stringResource(Res.string.option_payment_state_title),
                subtitle = stringResource(Res.string.option_payment_state_subtitle),
                checked = options.readPaymentState,
                onCheckedChange = { onOptionsChange(options.copy(readPaymentState = it)) },
            )
            OptionRow(
                title = stringResource(Res.string.option_skip_editing_title),
                subtitle = stringResource(Res.string.option_skip_editing_subtitle),
                checked = !options.allowPageEditing,
                onCheckedChange = { onOptionsChange(options.copy(allowPageEditing = !it)) },
            )

            //these are only a few of the options, the documentation lists all of them
            HorizontalDivider(modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp))
            Text(
                stringResource(Res.string.options_more),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
            )
            TextButton(
                onClick = { uriHandler.openUri(DocutainLink.Documentation.url) },
                modifier = Modifier.padding(horizontal = 12.dp),
            ) {
                Text(stringResource(Res.string.options_more_link))
            }
        }
    }
}

@Composable
private fun OptionRow(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!checked) }
            .padding(horizontal = 24.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.bodyLarge)
            Spacer(Modifier.height(2.dp))
            Text(
                subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        Spacer(Modifier.width(16.dp))
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}
