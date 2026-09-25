package de.docutain.sdk.docutain_sdk_example_photopayment_kmp_compose.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.docutain.sdk.docutain_sdk_example_photopayment_kmp_compose.payment.PaymentData
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.Res
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.action_back
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.action_start_again
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.arrow_back
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.expand_more
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.field_amount
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.field_bic
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.field_iban
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.field_recipient
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.field_reference
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.result_offline_hint
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.result_integrate_hint
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.result_raw_json
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.result_transfer_section
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.result_title
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.verified_user
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

/**
 * Shows what the SDK detected the way a transfer form would, read-only: this sample is about
 * what the SDK recognises, not about the form a banking app would build around it. Everything
 * else the SDK returned is in the raw JSON below.
 *
 * [json] is the raw answer of the SDK, [data] what was parsed from it.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(
    json: String,
    data: PaymentData,
    onBack: () -> Unit,
    onStartAgain: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(Res.string.result_title)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            painterResource(Res.drawable.arrow_back),
                            contentDescription = stringResource(Res.string.action_back),
                        )
                    }
                },
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
        ) {
            //an empty result never gets here, the SDK shows its own screen for it
            AmountHeader(data)
            Spacer(Modifier.height(16.dp))
            TransferCard(
                listOf(
                    Res.string.field_recipient to data.recipient,
                    Res.string.field_iban to data.iban,
                    Res.string.field_bic to data.bic,
                    Res.string.field_reference to data.reference,
                ).filter { (_, value) -> value.isNotBlank() }
            )

            Spacer(Modifier.height(12.dp))
            RawJson(json)

            Spacer(Modifier.height(24.dp))
            Button(
                onClick = onStartAgain,
                modifier = Modifier.fillMaxWidth().heightIn(min = 56.dp),
            ) {
                Text(stringResource(Res.string.action_start_again), style = MaterialTheme.typography.titleMedium)
            }

            //right after a convincing result is when integrating it is most interesting
            Spacer(Modifier.height(24.dp))
            Text(
                stringResource(Res.string.result_integrate_hint),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
            DocutainLinks(DocutainLink.entries, modifier = Modifier.fillMaxWidth())
        }
    }
}

/** The amount, the first thing a user checks, and the reminder that nothing left the device. */
@Composable
private fun AmountHeader(data: PaymentData) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(stringResource(Res.string.field_amount), style = MaterialTheme.typography.labelLarge)
            Text(
                data.displayAmount ?: "–",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painterResource(Res.drawable.verified_user),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(16.dp),
                )
                Spacer(Modifier.width(6.dp))
                Text(stringResource(Res.string.result_offline_hint), style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}

/** The fields of a transfer form, as label and detected value. */
@Composable
private fun TransferCard(fields: List<Pair<StringResource, String>>) {
    if (fields.isEmpty()) return
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(vertical = 8.dp)) {
            Text(
                stringResource(Res.string.result_transfer_section),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            )
            fields.forEachIndexed { index, (label, value) ->
                if (index > 0) HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
                    Text(
                        stringResource(label),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    SelectionContainer {
                        Text(value, style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }
    }
}

/** The JSON exactly as the SDK returned it, for developers who want to see the raw result. */
@Composable
private fun RawJson(json: String) {
    //keyed by the JSON, so the next result starts collapsed again
    var expanded by rememberSaveable(json) { mutableStateOf(false) }
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                stringResource(Res.string.result_raw_json),
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.weight(1f),
            )
            Icon(
                painterResource(Res.drawable.expand_more),
                contentDescription = null,
                modifier = Modifier.rotate(if (expanded) 180f else 0f),
            )
        }
        AnimatedVisibility(expanded) {
            SelectionContainer {
                Text(
                    json,
                    style = MaterialTheme.typography.bodySmall,
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                )
            }
        }
    }
}
