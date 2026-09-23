package com.cairosquad.evolvefit.design_system.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.cairosquad.evolvefit.design_system.colors.Brand
import com.cairosquad.evolvefit.design_system.colors.Gradiant
import com.cairosquad.evolvefit.design_system.colors.Surfaces
import com.cairosquad.evolvefit.design_system.colors.System
import com.cairosquad.evolvefit.design_system.colors.ThemeColors
import com.cairosquad.evolvefit.design_system.colors.darkGradient
import com.cairosquad.evolvefit.design_system.colors.darkM3ColorScheme
import com.cairosquad.evolvefit.design_system.colors.lightGradient
import com.cairosquad.evolvefit.design_system.colors.lightM3ColorScheme
import com.cairosquad.evolvefit.design_system.text_styles.AppTextStyle
import com.cairosquad.evolvefit.design_system.text_styles.DefaultTextStyle
import com.cairosquad.evolvefit.design_system.text_styles.TextStyle

@Composable
fun AppTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (isDarkTheme) darkM3ColorScheme else lightM3ColorScheme
    val gradient = if (isDarkTheme) darkGradient else lightGradient


    MaterialTheme(
        colorScheme = colorScheme,
    ) {
        val themeColors = buildThemeColors(isDarkTheme, gradient)
        val appTextStyle = TextStyle()

        UpdateStatusBarIconsForTheme(isDarkTheme)
        UpdateNavBarColorForTheme(isDarkTheme, colorScheme.surface.toArgb())

        CompositionLocalProvider(
            LocalThemeColor provides themeColors,
            LocalTextStyle provides appTextStyle,
            LocalIsDark provides isDarkTheme,
        ) {
            content()
        }
    }
}

@Composable
@ReadOnlyComposable
private fun buildThemeColors(isDark: Boolean, gradient: Gradiant): ThemeColors {
    val cs = MaterialTheme.colorScheme
    return ThemeColors(
        brand = Brand(
            primary = cs.primary,
            onPrimary = cs.onPrimary,
            primaryContainer = cs.primaryContainer,
            onPrimaryContainer = cs.onPrimaryContainer,
        ),
        surfaces = Surfaces(
            surface = cs.surface,
            onSurface = cs.onSurface,
            surfaceContainer = cs.surfaceContainer,
            onSurfaceContainer = cs.onSurface,
            textColor = cs.onSurface,
            surfaceVariant = cs.surfaceVariant,
            onSurfaceVariant = cs.onSurfaceVariant,
            outline = cs.outline,
            outlineVariant = cs.outlineVariant,
            onSurfaceAt1 = if (isDark) Color(0xDE000000) else Color(0xAD000000),
            onSurfaceAt2 = if (isDark) Color(0xAD0B0B0B) else Color(0x66000000),
            onSurfaceAt3 = if (isDark) Color(0x61000000) else Color(0x3D000000),
            onSurfaceAt4 = if (isDark) Color(0x1F000000) else Color(0x66919191),
            dropShadow = if (isDark) Color(0x29FFFFFF) else Color(0x29000000),
        ),
        system = System(
            warning = if (isDark) Color(0xFFF5D02D) else Color(0xFFD9CB00),
            error = cs.error,
            success = if (isDark) Color(0xFF2DAD58) else Color(0xFF8DD876),
            info = if (isDark) Color(0xFF2B60D3) else Color(0xFF4E95FF),
        ),
        gradiant = gradient,
        isDark = isDark,
    )
}

val LocalTextStyle = staticCompositionLocalOf<AppTextStyle> { DefaultTextStyle }
val LocalThemeColor = staticCompositionLocalOf { ThemeColors(
    brand = Brand(Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified),
    surfaces = Surfaces(Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified),
    system = System(Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified),
    gradiant = Gradiant(emptyList(), emptyList(), emptyList(), emptyList()),
    isDark = false,
) }
val LocalIsDark = staticCompositionLocalOf { false }

@Composable
expect fun UpdateStatusBarIconsForTheme(isStatusBarIconsLight: Boolean)

@Composable
expect fun UpdateNavBarColorForTheme(isDark: Boolean, color: Int)