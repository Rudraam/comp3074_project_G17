@file:Suppress("DEPRECATION")

package ca.gbc.smartpocketprototype.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
<<<<<<< HEAD
    primary = md_theme_dark_primary,
    secondary = md_theme_dark_secondary,
    tertiary = md_theme_dark_tertiary,
=======
>>>>>>> Jasleen
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFFE0E0E0),
    onSurface = Color(0xFFE0E0E0),
)

private val LightColorScheme = lightColorScheme(
<<<<<<< HEAD
    primary = md_theme_light_primary,
    secondary = md_theme_light_secondary,
    tertiary = md_theme_light_tertiary,
    background = md_theme_light_background,
    surface = md_theme_light_surface,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = md_theme_light_onBackground,
    onSurface = md_theme_light_onSurface,
=======
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
>>>>>>> Jasleen
)

@Composable
fun SmartPocketPrototypeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
<<<<<<< HEAD
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
=======
>>>>>>> Jasleen
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}