package br.com.ads.imobiliaria.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val NubankSecondaryColor = Color(0xFF1B1D32) // Fundo escuro
private val NubankTertiaryColor = Color(0xFF00D3A1) // Verde

@Composable
fun ImobiliariaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (darkTheme) darkColorScheme(secondary = NubankSecondaryColor, tertiary = NubankTertiaryColor)
            else lightColorScheme(secondary = NubankSecondaryColor, tertiary = NubankTertiaryColor)
        }
        darkTheme -> darkColorScheme(secondary = NubankSecondaryColor, tertiary = NubankTertiaryColor)
        else -> lightColorScheme(secondary = NubankSecondaryColor, tertiary = NubankTertiaryColor)
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = NubankSecondaryColor.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}