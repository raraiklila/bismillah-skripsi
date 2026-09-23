package com.cairosquad.evolvefit.design_system.colors

import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.graphics.Color

val darkM3ColorScheme = darkColorScheme(
    primary = Color(0xFF86D01D),
    onPrimary = Color(0xFF181B14),
    primaryContainer = Color(0xFF151814),
    onPrimaryContainer = Color(0xFFBDDF8C),
    secondary = Color(0xFFBFCBAB),
    onSecondary = Color(0xFF2A331C),
    secondaryContainer = Color(0xFF404A31),
    onSecondaryContainer = Color(0xFFDBE7C3),
    tertiary = Color(0xFFA2CFBD),
    onTertiary = Color(0xFF07372A),
    tertiaryContainer = Color(0xFF1F4E3F),
    onTertiaryContainer = Color(0xFFBEEBD9),
    error = Color(0xFFE65858),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),
    background = Color(0xFF090A09),
    onBackground = Color(0xFFF0F5FF),
    surface = Color(0xFF090A09),
    onSurface = Color(0xFFF0F5FF),
    surfaceVariant = Color(0xFF373737),
    onSurfaceVariant = Color(0xFF828282),
    outline = Color(0xFF9C9C9C),
    outlineVariant = Color(0xFF313131),
    scrim = Color(0xFF000000),
    inverseSurface = Color(0xFFF0F5FF),
    inverseOnSurface = Color(0xFF2C2C2C),
    inversePrimary = Color(0xFF4D7500),
    surfaceContainer = Color(0xFF1C1C1C),
    surfaceDim = Color(0xFF090A09),
    surfaceBright = Color(0xFF373737),
    surfaceContainerLowest = Color(0xFF040404),
    surfaceContainerLow = Color(0xFF141414),
    surfaceContainerHigh = Color(0xFF262626),
    surfaceContainerHighest = Color(0xFF313131),
)

val darkGradient = Gradiant(
    barGradiant = listOf(Color(0xFF86D01D), Color(0x03000000)),
    iconGradiant = listOf(Color(0xFF86D01D), Color(0xFFCAE99F)),
    shimmerGradientColors = listOf(Color.Transparent, Color(0xFF1A1A1A), Color.Transparent),
    loadingGradientColors = listOf(Color.Transparent, Color(0xFF1C1C1C), Color.Transparent),
)