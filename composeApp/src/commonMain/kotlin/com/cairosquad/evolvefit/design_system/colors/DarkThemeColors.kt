package com.cairosquad.evolvefit.design_system.colors

import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.graphics.Color

/**
 * Material 3 Expressive Dark Color Scheme.
 * Based on Google Material 3 Expressive design specifications (High-Contrast Expressive Dark Palette):
 * - Primary: Vibrant Neon Lime (#86D01D) maintaining strong energetic brand visibility in dark mode
 * - OnPrimary: Deep contrast tone (#111A04, > 9.5:1 ratio)
 * - PrimaryContainer: Prominent expressive container (#2A3D06) with bright text (#D4F788, > 8.0:1 ratio)
 * - Surfaces: Premium deep dark surface (#0D0F0C) with full M3 Expressive container hierarchy (lowest to highest)
 */
val darkM3ColorScheme = darkColorScheme(
    primary = Color(0xFF86D01D),
    onPrimary = Color(0xFF111A04),
    primaryContainer = Color(0xFF2A3D06),
    onPrimaryContainer = Color(0xFFD4F788),
    secondary = Color(0xFFBCCBA7),
    onSecondary = Color(0xFF263417),
    secondaryContainer = Color(0xFF3C4B2B),
    onSecondaryContainer = Color(0xFFD8E7C2),
    tertiary = Color(0xFFA0D0BF),
    onTertiary = Color(0xFF00382A),
    tertiaryContainer = Color(0xFF1F4E40),
    onTertiaryContainer = Color(0xFFBCECDD),
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),
    background = Color(0xFF0D0F0C),
    onBackground = Color(0xFFE2E4DA),
    surface = Color(0xFF0D0F0C),
    onSurface = Color(0xFFE2E4DA),
    surfaceVariant = Color(0xFF43483E),
    onSurfaceVariant = Color(0xFFC4C8BC),
    outline = Color(0xFF8E9287),
    outlineVariant = Color(0xFF43483E),
    scrim = Color(0xFF000000),
    inverseSurface = Color(0xFFE2E4DA),
    inverseOnSurface = Color(0xFF2E3129),
    inversePrimary = Color(0xFF4A6705),
    surfaceDim = Color(0xFF0D0F0C),
    surfaceBright = Color(0xFF363931),
    surfaceContainerLowest = Color(0xFF080A07),
    surfaceContainerLow = Color(0xFF151814),
    surfaceContainer = Color(0xFF191C18),
    surfaceContainerHigh = Color(0xFF232622),
    surfaceContainerHighest = Color(0xFF2E312C),
)

val darkGradient = Gradiant(
    barGradiant = listOf(Color(0xFF86D01D), Color(0x03000000)),
    iconGradiant = listOf(Color(0xFF86D01D), Color(0xFFCAE99F)),
    shimmerGradientColors = listOf(Color.Transparent, Color(0xFF1A1A1A), Color.Transparent),
    loadingGradientColors = listOf(Color.Transparent, Color(0xFF1C1C1C), Color.Transparent),
)