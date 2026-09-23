package com.cairosquad.evolvefit.design_system.colors

import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val lightM3ColorScheme = lightColorScheme(
    primary = Color(0xFF9BE03A),
    onPrimary = Color(0xFF161B07),
    primaryContainer = Color(0xFFF3FAD1),
    onPrimaryContainer = Color(0xFFD0F898),
    secondary = Color(0xFF586249),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFDBE7C3),
    onSecondaryContainer = Color(0xFF141E0A),
    tertiary = Color(0xFF3B6657),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFBEEBD9),
    onTertiaryContainer = Color(0xFF002117),
    error = Color(0xFFF65659),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002),
    background = Color(0xFFFDFDFD),
    onBackground = Color(0xFF2C2C2C),
    surface = Color(0xFFFDFDFD),
    onSurface = Color(0xFF2C2C2C),
    surfaceVariant = Color(0xFFFFFFFF),
    onSurfaceVariant = Color(0xFF929292),
    outline = Color(0xFF989898),
    outlineVariant = Color(0xFFE6E9DB),
    scrim = Color(0xFF000000),
    inverseSurface = Color(0xFF1C1C1C),
    inverseOnSurface = Color(0xFFF0F5FF),
    inversePrimary = Color(0xFF86D01D),
    surfaceContainer = Color(0xFFF8F8F8),
    surfaceDim = Color(0xFFEDEDED),
    surfaceBright = Color(0xFFFDFDFD),
    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFFF8F8F8),
    surfaceContainerHigh = Color(0xFFEEEEEE),
    surfaceContainerHighest = Color(0xFFE8E8E8),
)

val lightGradient = Gradiant(
    barGradiant = listOf(Color(0xFF86D01D), Color(0x03FFFFFF)),
    iconGradiant = listOf(Color(0xFF86D01D), Color(0xFFCAE99F)),
    shimmerGradientColors = listOf(Color.Transparent, Color(0xFFFFFFFF), Color.Transparent),
    loadingGradientColors = listOf(Color.Transparent, Color(0xFFF8F8F8), Color.Transparent),
)
