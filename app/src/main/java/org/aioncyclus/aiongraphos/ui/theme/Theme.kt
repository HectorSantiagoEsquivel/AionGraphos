package org.aioncyclus.aiongraphos.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = SolarGold,
    secondary = LunarIvory,
    tertiary = MaritimeBlue,

    background = OceanicEbony,
    surface = LunarIvory.copy(alpha = 0.05F),

    onPrimary = OceanicEbony,
    onSecondary = OceanicEbony,
    onTertiary = LunarIvory,

    onBackground = LunarIvory,
    onSurface = LunarIvory
)

private val LightColorScheme = lightColorScheme(
    primary = SolarGold,
    secondary = OceanicEbony,
    tertiary = MaritimeBlue,

    background = LunarIvory,
    surface = SteelBlue.copy(alpha = 0.05F),

    onPrimary = OceanicEbony,
    onSecondary = LunarIvory,
    onTertiary = LunarIvory,

    onBackground = SteelBlue,
    onSurface = OceanicEbony,
    onSurfaceVariant = MaritimeBlue
)

@Composable
fun AiongraphosTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}