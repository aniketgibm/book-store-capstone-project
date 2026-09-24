package com.bookstore.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryContainer,
    onPrimaryContainer = OnPrimaryContainer,
    secondary = Secondary,
    onSecondary = OnSecondary,
    secondaryContainer = SecondaryContainer,
    onSecondaryContainer = OnSecondaryContainer,
    tertiary = Tertiary,
    onTertiary = OnTertiary,
    tertiaryContainer = TertiaryContainer,
    background = Background,
    onBackground = OnBackground,
    surface = Surface,
    onSurface = OnSurface,
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = OnSurfaceVariant,
    outline = Outline,
    error = Error,
    onError = OnError
)

private val DarkColorScheme = darkColorScheme(
    primary = androidx.compose.ui.graphics.Color(0xFFFFB945),
    onPrimary = androidx.compose.ui.graphics.Color(0xFF472A00),
    primaryContainer = androidx.compose.ui.graphics.Color(0xFF663E00),
    secondary = androidx.compose.ui.graphics.Color(0xFFB3C5FF),
    onSecondary = androidx.compose.ui.graphics.Color(0xFF002082),
    background = androidx.compose.ui.graphics.Color(0xFF17130C),
    surface = androidx.compose.ui.graphics.Color(0xFF17130C),
    onBackground = androidx.compose.ui.graphics.Color(0xFFEDE0D1),
    onSurface = androidx.compose.ui.graphics.Color(0xFFEDE0D1)
)

@Composable
fun BookStoreTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = !darkTheme
                isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
