package de.docutain.sdk.docutain_sdk_example_photopayment_kmp_compose.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * The Docutain green, the same the other Docutain samples use, on neutral surfaces - the
 * Material defaults would tint every surface, card, sheet and dialog purple.
 */
private val LightColors = lightColorScheme(
    primary = Color(0xFF4CAF50),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFDDF2DE),
    onPrimaryContainer = Color(0xFF0B3D0E),
    background = Color(0xFFFAFAFA),
    surface = Color(0xFFFAFAFA),
    onSurfaceVariant = Color(0xFF44474A),
    outlineVariant = Color(0xFFC6C8C5),
    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFFF4F5F3),
    surfaceContainerHigh = Color(0xFFE9EAE8),
    surfaceContainerHighest = Color(0xFFEFF0EE),
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF58AC5B),
    onPrimary = Color(0xFF000000),
    primaryContainer = Color(0xFF1F4A21),
    onPrimaryContainer = Color(0xFFDDF2DE),
    background = Color(0xFF121212),
    surface = Color(0xFF121212),
    onSurfaceVariant = Color(0xFFC4C7C5),
    outlineVariant = Color(0xFF444746),
    surfaceContainerLowest = Color(0xFF0D0D0D),
    surfaceContainerLow = Color(0xFF1A1B1A),
    surfaceContainerHigh = Color(0xFF2B2C2B),
    surfaceContainerHighest = Color(0xFF232423),
)

/** Wraps the app in the Material theme with the colors of this sample. */
@Composable
fun DocutainTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        content = content,
    )
}
