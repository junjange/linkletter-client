package linkletter.client.core.designsystem.theme

import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse

val Blue01 = Color(0xFF5180FF)
val Blue01_A30 = Color(0x4D5180FF)
val Blue02 = Color(0xFF215BF6)

val Purple01 = Color(0xFFB469FF)
val Purple01_A30 = Color(0x4DB469FF)

val White = Color(0xFFFFFFFF)
val PaleGray = Color(0xFFF9F9F9)
val LightGray = Color(0xFFF2F2F7)
val DarkGray = Color(0xFF2C2C2E)
val NeutralGray = Color(0xFFF1F1F1)
val Black = Color(0xFF000000)
val Graphite = Color(0xFF292929)

val Gray900 = Color(0xFF212121)
val Gray800 = Color(0xFF424242)
val Gray700 = Color(0xFF616161)
val Gray600 = Color(0xFF757575)
val Gray500 = Color(0xFF9E9E9E)
val Gray400 = Color(0xFFBDBDBD)
val Gray300 = Color(0xFFE0E0E0)
val Gray200 = Color(0xFFEEEEEE)
val Gray100 = Color(0xFFF5F5F5)
val Gray50 = Color(0xFF7F7F7F)

@Immutable
data class LinkletterColorScheme(
    val primary: Color,
    val onPrimary: Color,
    val background: Color,
    val onBackground: Color,
    val surface: Color,
    val onSurface: Color,
    val primarySurface: Color,
    val onPrimarySurface: Color,
    val secondarySurface: Color,
    val onSecondarySurface: Color,
    val accentSurface: Color,
    val onAccentSurface: Color,
    val neutralSurface: Color,
    val onNeutralSurface: Color,
    val borderColor: Color,
    val selectedIconColor: Color,
    val unselectedIconColor: Color,
    val darkSurface: Color = Graphite,
    val onDarkSurface: Color = Color.White,
    val lightSurface: Color = White,
    val onLightSurface: Color = Color.Black,
    val iconBackground: Color,
    val placeholderColor: Color,
) {
    companion object {
        val lightColorScheme =
            LinkletterColorScheme(
                primary = Blue02,
                onPrimary = White,
                background = PaleGray,
                onBackground = Black,
                surface = White,
                onSurface = Black,
                primarySurface = Blue02,
                onPrimarySurface = White,
                secondarySurface = Blue01_A30,
                onSecondarySurface = Blue01,
                accentSurface = Purple01_A30,
                onAccentSurface = Purple01,
                neutralSurface = DarkGray,
                onNeutralSurface = LightGray,
                borderColor = LightGray,
                selectedIconColor = Black,
                unselectedIconColor = Gray400,
                iconBackground = NeutralGray,
                placeholderColor = LightGray,
            )

        val darkColorScheme =
            LinkletterColorScheme(
                primary = Blue01,
                onPrimary = White,
                background = Black,
                onBackground = White,
                surface = Graphite,
                onSurface = White,
                primarySurface = Blue02,
                onPrimarySurface = White,
                secondarySurface = Blue01_A30,
                onSecondarySurface = White,
                accentSurface = Purple01_A30,
                onAccentSurface = Purple01,
                neutralSurface = DarkGray,
                onNeutralSurface = LightGray,
                borderColor = DarkGray,
                selectedIconColor = White,
                unselectedIconColor = Gray700,
                iconBackground = Graphite,
                placeholderColor = DarkGray,
            )
    }
}

@Stable
private fun LinkletterColorScheme.contentColorFor(backgroundColor: Color): Color =
    when (backgroundColor) {
        primary -> onPrimary
        background -> onBackground
        surface -> onSurface
        darkSurface -> onDarkSurface
        lightSurface -> onLightSurface
        primarySurface -> onPrimarySurface
        secondarySurface -> onSecondarySurface
        accentSurface -> onAccentSurface
        else -> Color.Unspecified
    }

@Composable
@ReadOnlyComposable
fun contentColorFor(backgroundColor: Color) =
    LinkletterTheme.colorScheme.contentColorFor(backgroundColor).takeOrElse {
        LocalContentColor.current
    }

val LocalColorScheme = staticCompositionLocalOf { LinkletterColorScheme.lightColorScheme }
