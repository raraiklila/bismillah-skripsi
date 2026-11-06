package com.cairosquad.evolvefit.design_system.theme

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback

/**
 * Material 3 Expressive Motion Physics System.
 * Following official specification from https://m3.material.io/ :
 *
 * 1. Motion Schemes:
 *    - Expressive: Overshoots final values to add bounce and life to key interactions.
 *    - Standard: Eases into final values without overshoot for utilitarian moments.
 *
 * 2. Spring Tokens (md.sys.motion.spring.*):
 *    - Spatial tokens: Used for movement, scale, rotation, size, corner radius (with overshoot).
 *    - Effects tokens: Used for opacity, color, elevation (without overshoot, damping = 1.0f).
 *    - Speeds: Fast (switches, buttons), Default (sheets, cards), Slow (full-screen, celebration).
 *
 * 3. Easing & Duration pairs (Transitions):
 *    - Emphasized (500ms): Begin and end on screen
 *    - Emphasized Decelerate (400ms): Enter screen
 *    - Emphasized Accelerate (200ms): Exit screen
 *    - Standard (300ms): Utility on screen
 *    - Standard Decelerate (250ms): Utility enter
 *    - Standard Accelerate (200ms): Utility exit
 */
object AppMotion {

    /**
     * Official M3 Motion Physics Spring Tokens.
     */
    object SpringTokens {
        // --- Fast Speed (Buttons, Switches, Pills) ---
        // md.sys.motion.spring.fast.spatial: Overshoots and bounces into place
        fun <T> fastSpatial(): SpringSpec<T> = spring(
            dampingRatio = 0.65f, // Responsive, bouncy overshoot
            stiffness = 450f      // Smooth and fluid response
        )

        // md.sys.motion.spring.fast.effects: Smooth color/alpha change without overshoot
        fun <T> fastEffects(): SpringSpec<T> = spring(
            dampingRatio = 1.0f, // No overshoot
            stiffness = 450f
        )

        // --- Default Speed (Bottom Sheets, Navigation Rail, Cards, Dialogs) ---
        // md.sys.motion.spring.default.spatial: Fluid motion with natural settling
        fun <T> defaultSpatial(): SpringSpec<T> = spring(
            dampingRatio = 0.7f, // Gentle, organic bounce
            stiffness = 320f     // Natural tactile cadence
        )

        // md.sys.motion.spring.default.effects: Opacity and fade without overshoot
        fun <T> defaultEffects(): SpringSpec<T> = spring(
            dampingRatio = 1.0f, // No overshoot
            stiffness = 350f
        )

        // --- Slow Speed (Full-screen transitions, Hero celebrations, Expressive Meters) ---
        // md.sys.motion.spring.slow.spatial: Expressive hero entrance with lingering bounce
        fun <T> slowSpatial(): SpringSpec<T> = spring(
            dampingRatio = 0.68f,
            stiffness = 180f
        )

        // md.sys.motion.spring.slow.effects: Smooth full-screen fade/scrim
        fun <T> slowEffects(): SpringSpec<T> = spring(
            dampingRatio = 1.0f,
            stiffness = 200f
        )
    }

    // --- Official M3 Easing Curves ---
    // Emphasized easing set (Recommended for M3 Expressive)
    val EmphasizedEasing: Easing = CubicBezierEasing(0.2f, 0.0f, 0.0f, 1.0f)
    val EmphasizedDecelerate: Easing = CubicBezierEasing(0.05f, 0.7f, 0.1f, 1.0f)
    val EmphasizedAccelerate: Easing = CubicBezierEasing(0.3f, 0.0f, 0.8f, 0.15f)

    // Standard easing set (Utility fallback)
    val StandardEasing: Easing = FastOutSlowInEasing
    val StandardDecelerate: Easing = LinearOutSlowInEasing
    val StandardAccelerate: Easing = CubicBezierEasing(0.3f, 0.0f, 1.0f, 1.0f)

    // Backward-compatibility shortcuts
    fun <T> bouncySpring(): SpringSpec<T> = SpringTokens.fastSpatial()
    fun <T> softSpring(): SpringSpec<T> = SpringTokens.defaultSpatial()
    fun <T> crispSpring(): SpringSpec<T> = SpringTokens.fastEffects()

    /**
     * M3 Expressive Shape Morphing Spring Spec.
     * Overshoots corner radii transformation to create tactile tension.
     */
    fun <T> cornerMorphSpring(): SpringSpec<T> = spring(
        dampingRatio = 0.65f,
        stiffness = 500f
    )
}

/**
 * Material 3 Expressive tactile bounce modifier.
 * Applies [AppMotion.SpringTokens.defaultSpatial] on touch press/release for a smooth, fluid feel.
 */
@Composable
fun Modifier.bouncyClickable(
    enabled: Boolean = true,
    pressedScale: Float = 0.95f,
    interactionSource: MutableInteractionSource? = null,
    springSpec: SpringSpec<Float> = AppMotion.SpringTokens.defaultSpatial(),
    onClick: () -> Unit
): Modifier {
    val source = interactionSource ?: remember { MutableInteractionSource() }
    val isPressed by source.collectIsPressedAsState()
    val haptic = LocalHapticFeedback.current

    LaunchedEffect(isPressed) {
        if (isPressed && enabled) {
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
        }
    }

    val scale by animateFloatAsState(
        targetValue = if (isPressed && enabled) pressedScale else 1f,
        animationSpec = springSpec,
        label = "M3ExpressiveBouncyScale"
    )

    return this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .clickable(
            interactionSource = source,
            indication = null,
            enabled = enabled,
            onClick = onClick
        )
}

