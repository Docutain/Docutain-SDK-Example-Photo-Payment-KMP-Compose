package de.docutain.sdk.docutain_sdk_example_photopayment_kmp_compose.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.Res
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.action_options
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.action_start
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.airplane
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.app_subtitle
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.app_title
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.check
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.description
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.feature_girocode
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.feature_multipage
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.feature_share
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.home_description
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.home_headline
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.powered_by
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.privacy_gdpr
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.privacy_no_cloud
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.privacy_offline
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.privacy_title
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.privacy_try
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.qr_code
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.settings
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.share
import docutainsdkexamplephotopaymentkmpcompose.shared.generated.resources.verified_user
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

/**
 * The start screen: one clear way into the photo payment process, followed by its strongest
 * argument - everything runs on the device - and what else the SDK does.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    isSdkInitialized: Boolean,
    isRunning: Boolean,
    onStart: () -> Unit,
    onOpenOptions: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(stringResource(Res.string.app_title))
                        Text(
                            stringResource(Res.string.app_subtitle),
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onOpenOptions) {
                        Icon(
                            painterResource(Res.drawable.settings),
                            contentDescription = stringResource(Res.string.action_options),
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
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(16.dp))
            InvoiceIllustration()
            Spacer(Modifier.height(32.dp))

            Text(
                stringResource(Res.string.home_headline),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(8.dp))
            Text(
                stringResource(Res.string.home_description),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )

            Spacer(Modifier.height(32.dp))

            Button(
                onClick = onStart,
                //nothing works with a license key the SDK refused
                enabled = !isRunning && isSdkInitialized,
                modifier = Modifier.fillMaxWidth().heightIn(min = 56.dp),
                contentPadding = PaddingValues(horizontal = 24.dp),
            ) {
                Text(stringResource(Res.string.action_start), style = MaterialTheme.typography.titleMedium)
            }

            Spacer(Modifier.height(24.dp))
            PrivacyCard()
            Spacer(Modifier.height(24.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Feature(painterResource(Res.drawable.qr_code), stringResource(Res.string.feature_girocode))
                Feature(painterResource(Res.drawable.description), stringResource(Res.string.feature_multipage))
                Feature(painterResource(Res.drawable.share), stringResource(Res.string.feature_share))
            }

            Spacer(Modifier.height(32.dp))
            Text(
                stringResource(Res.string.powered_by),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            DocutainLinks(DocutainLink.entries)
        }
    }
}

/**
 * Why running on the device matters: nothing leaves it, it works offline, and it spares the
 * app a data processing agreement. The airplane mode hint lets testers check it themselves.
 */
@Composable
private fun PrivacyCard() {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painterResource(Res.drawable.verified_user),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(28.dp),
                )
                Spacer(Modifier.width(12.dp))
                Text(
                    stringResource(Res.string.privacy_title),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                )
            }
            Spacer(Modifier.height(16.dp))
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                PrivacyPoint(stringResource(Res.string.privacy_no_cloud))
                PrivacyPoint(stringResource(Res.string.privacy_offline))
                PrivacyPoint(stringResource(Res.string.privacy_gdpr))
            }
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp),
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.15f),
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painterResource(Res.drawable.airplane),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                )
                Spacer(Modifier.width(12.dp))
                Text(
                    stringResource(Res.string.privacy_try),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                )
            }
        }
    }
}

@Composable
private fun PrivacyPoint(text: String) {
    Row {
        Icon(
            painterResource(Res.drawable.check),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(top = 2.dp).size(18.dp),
        )
        Spacer(Modifier.width(12.dp))
        Text(text, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
private fun Feature(icon: Painter, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(MaterialTheme.colorScheme.primaryContainer, CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.size(20.dp),
            )
        }
        Spacer(Modifier.width(16.dp))
        Text(text, style = MaterialTheme.typography.bodyLarge)
    }
}

/** A stylised invoice with a green check on it: the paper turned into a payment. */
@Composable
private fun InvoiceIllustration() {
    val line = MaterialTheme.colorScheme.outlineVariant
    //white paper in light mode, a raised grey in dark mode where white would glare
    val paper = if (MaterialTheme.colorScheme.surface.luminance() < 0.5f) {
        MaterialTheme.colorScheme.surfaceContainerHigh
    } else {
        MaterialTheme.colorScheme.surfaceContainerLowest
    }
    Box(modifier = Modifier.size(width = 190.dp, height = 170.dp), contentAlignment = Alignment.Center) {
        Surface(
            modifier = Modifier.size(width = 130.dp, height = 160.dp).rotate(-6f),
            shape = RoundedCornerShape(12.dp),
            color = paper,
            shadowElevation = 6.dp,
            tonalElevation = 1.dp,
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(Modifier.size(width = 56.dp, height = 8.dp).background(MaterialTheme.colorScheme.primary, CircleShape))
                Spacer(Modifier.height(6.dp))
                listOf(1f, 0.8f, 0.9f, 0.6f).forEach { fraction ->
                    Box(Modifier.fillMaxWidth(fraction).height(6.dp).background(line, CircleShape))
                }
                Spacer(Modifier.height(6.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Box(Modifier.size(width = 40.dp, height = 10.dp).background(MaterialTheme.colorScheme.onSurfaceVariant, CircleShape))
                }
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = (-4).dp, y = (-4).dp)
                .size(64.dp)
                .background(MaterialTheme.colorScheme.primary, CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                painterResource(Res.drawable.check),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(36.dp),
            )
        }
    }
}
