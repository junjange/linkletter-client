package linkletter.client.core.designsystem.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.text.font.FontFamily

val LocalDarkTheme = compositionLocalOf { true }

@Composable
fun LinkletterTheme(
    fontFamily: FontFamily = FontFamily.Default,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalDarkTheme provides darkTheme,
        LocalColorScheme provides
            if (darkTheme) {
                LinkletterColorScheme.darkColorScheme
            } else {
                LinkletterColorScheme.lightColorScheme
            },
        LocalIndication provides ripple(),
        LocalTypography provides LinkletterTypography.with(fontFamily),
        content = content,
    )
}

object LinkletterTheme {
    val colorScheme: LinkletterColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalColorScheme.current

    val typography: LinkletterTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current
}
