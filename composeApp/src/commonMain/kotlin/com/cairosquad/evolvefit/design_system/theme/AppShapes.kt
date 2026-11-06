package com.cairosquad.evolvefit.design_system.theme

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

/**
 * Material 3 Expressive Shape Library.
 * Following official https://m3.material.io/styles/shape/overview-principles guidance:
 * Features corner radius scale tokens, asymmetric shapes, and expressive shape presets.
 */
data class AppShapes(
    // Official Google M3 Expressive Corner Radius Scale
    val none: CornerBasedShape = RoundedCornerShape(0.dp),
    val extraSmall: CornerBasedShape = RoundedCornerShape(4.dp),
    val small: CornerBasedShape = RoundedCornerShape(8.dp),
    val medium: CornerBasedShape = RoundedCornerShape(12.dp),
    val large: CornerBasedShape = RoundedCornerShape(16.dp),
    val largeIncreased: CornerBasedShape = RoundedCornerShape(20.dp),
    val extraLarge: CornerBasedShape = RoundedCornerShape(28.dp),
    val extraExtraLarge: CornerBasedShape = RoundedCornerShape(32.dp),
    val full: CornerBasedShape = CircleShape,

    // Semantic Expressive Components Shapes
    val cardShape: CornerBasedShape = RoundedCornerShape(20.dp),
    val cardSmallShape: CornerBasedShape = RoundedCornerShape(12.dp),
    val sheetShape: CornerBasedShape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
    val inputShape: CornerBasedShape = RoundedCornerShape(12.dp),
    val buttonShape: CornerBasedShape = CircleShape,
    val chipShape: CornerBasedShape = CircleShape,
    val indicatorShape: CornerBasedShape = CircleShape,
    val snackbarShape: CornerBasedShape = RoundedCornerShape(12.dp),

    // M3 Expressive Custom Shapes (Tension & Asymmetry)
    val pillShape: CornerBasedShape = CircleShape,
    val archShape: CornerBasedShape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp, bottomStart = 4.dp, bottomEnd = 4.dp),
    val clamshellShape: CornerBasedShape = RoundedCornerShape(topStart = 28.dp, topEnd = 8.dp, bottomEnd = 28.dp, bottomStart = 8.dp),
    val gemShape: CornerBasedShape = RoundedCornerShape(topStart = 20.dp, topEnd = 4.dp, bottomEnd = 20.dp, bottomStart = 4.dp),
    val asymmetricCard: CornerBasedShape = RoundedCornerShape(topStart = 28.dp, topEnd = 12.dp, bottomEnd = 28.dp, bottomStart = 12.dp),
) {
    fun toMaterial3Shapes(): Shapes = Shapes(
        extraSmall = extraSmall,
        small = small,
        medium = medium,
        large = large,
        extraLarge = extraLarge,
    )
}

val defaultAppShapes = AppShapes()
