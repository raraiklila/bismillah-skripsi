package com.cairosquad.evolvefit.design_system.colors

import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

/**
 * Material 3 Expressive Light Color Scheme.
 * Based on Google Material 3 Expressive design specifications (High-Chroma Vibrant Active/Fitness Palette):
 * - Primary: High-chroma Neon Lime (Brand Identity: #86D01D / #9BE03A)
 * - OnPrimary: High-contrast deep dark (#111A04) ensuring WCAG AAA accessibility (> 9.5:1)
 * - PrimaryContainer: Expressive vibrant container (#D8F58D) with dark text (#142400) fixing legibility
 * - Surfaces: Clean, modern high-contrast background (#FFFFFF) with M3 Expressive container elevation steps
 */
val lightM3ColorScheme = lightColorScheme(
    primary = Color(0xFF86D01D),
    onPrimary = Color(0xFF111A04),
    primaryContainer = Color(0xFFD8F58D),
    onPrimaryContainer = Color(0xFF142400),
    secondary = Color(0xFF536340),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFD7E8BD),
    onSecondaryContainer = Color(0xFF121F04),
    tertiary = Color(0xFF386657),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFBBECE0),
    onTertiaryContainer = Color(0xFF002018),
    error = Color(0xFFBA1A1A),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002),
    background = Color(0xFFFFFFFF),
    onBackground = Color(0xFF191C16),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF191C16),
    surfaceVariant = Color(0xFFF2F4E8),
    onSurfaceVariant = Color(0xFF44483E),
    outline = Color(0xFF75796E),
    outlineVariant = Color(0xFFC5C9BB),
    scrim = Color(0xFF000000),
    inverseSurface = Color(0xFF2E3129),
    inverseOnSurface = Color(0xFFF1F2E8),
    inversePrimary = Color(0xFF86D01D),
    surfaceDim = Color(0xFFDADCD1),
    surfaceBright = Color(0xFFFFFFFF),
    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFFF8F9EF),
    surfaceContainer = Color(0xFFF2F4EA),
    surfaceContainerHigh = Color(0xFFECEEE4),
    surfaceContainerHighest = Color(0xFFE6E8DE),
)

val lightGradient = Gradiant(
    barGradiant = listOf(Color(0xFF86D01D), Color(0x03FFFFFF)),
    iconGradiant = listOf(Color(0xFF86D01D), Color(0xFFCAE99F)),
    shimmerGradientColors = listOf(Color.Transparent, Color(0xFFFFFFFF), Color.Transparent),
    loadingGradientColors = listOf(Color.Transparent, Color(0xFFF8F8F8), Color.Transparent),
)
