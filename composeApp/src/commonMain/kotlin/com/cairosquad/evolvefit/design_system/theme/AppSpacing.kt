package com.cairosquad.evolvefit.design_system.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Material 3 Expressive Spacing Tokens.
 *
 * Spacing units follow an 8dp scale where [space100] = 8dp.
 */
@Immutable
data class AppSpacing(
    /** 4dp - Micro spacing */
    val space25: Dp = 4.dp,
    /** 6dp - Compact element gap */
    val space50: Dp = 6.dp,
    /** 8dp - Baseline spacing unit */
    val space100: Dp = 8.dp,
    /** 12dp - Focused search margin / medium-small padding */
    val space150: Dp = 12.dp,
    /** 16dp - Standard component padding & gap */
    val space200: Dp = 16.dp,
    /** 24dp - Standard screen margin & large component gap */
    val space300: Dp = 24.dp,
    /** 32dp - Section spacing */
    val space400: Dp = 32.dp,
    /** 40dp - Large section gap */
    val space500: Dp = 40.dp,
    /** 48dp - Touch target minimum / extra large spacing */
    val space600: Dp = 48.dp,
    /** 56dp - Screen section divider spacing */
    val space700: Dp = 56.dp,
    /** 64dp - Hero section spacing */
    val space800: Dp = 64.dp,
    /** 72dp - Maximum spacing unit */
    val space900: Dp = 72.dp,
)

val defaultAppSpacing = AppSpacing()
