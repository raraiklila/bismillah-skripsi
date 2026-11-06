package com.cairosquad.evolvefit.design_system.component

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cairosquad.evolvefit.design_system.theme.AppMotion
import com.cairosquad.evolvefit.design_system.theme.Theme
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * Official Material 3 Expressive Morphing Loading Indicator.
 * Follows M3 Expressive specification:
 * Morphs smoothly between a sequence of rounded polygon shapes:
 * 1. Rounded Pentagon (5 lobes)
 * 2. Scallop Cookie (8 lobes)
 * 3. Clover (4 lobes)
 * 4. Smooth Circle
 *
 * @param modifier Custom modifier.
 * @param isContained Draws a circular container background if true.
 * @param indicatorColor Color of the morphing shape.
 * @param containerColor Background color for contained variant.
 * @param size Active size of the indicator.
 */
@Composable
fun AnimatedLoadingIndicator(
    modifier: Modifier = Modifier,
    isContained: Boolean = false,
    indicatorColor: Color = Theme.color.brand.primary,
    containerColor: Color = Theme.color.surfaces.surfaceContainer,
    iconTint: Color? = null,
    size: Dp = if (isContained) 38.dp else 28.dp,
    containerSize: Dp = 48.dp
) {
    val activeColor = iconTint ?: indicatorColor
    val infiniteTransition = rememberInfiniteTransition(label = "M3ExpressivePolygonMorphing")

    // Gentle, steady rotation (1 turn per 3.6 seconds)
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3600, easing = AppMotion.EmphasizedEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "M3LoadingRotation"
    )

    // Morph sequence progress (0f to 4f over 3.2s)
    val morphProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 4f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3200, easing = AppMotion.EmphasizedEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "M3PolygonMorphProgress"
    )

    // Gentle tactile scale (1.0f to 1.06f)
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.96f,
        targetValue = 1.04f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1600, easing = AppMotion.EmphasizedEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "M3PulseScale"
    )

    val boxModifier = if (isContained) {
        modifier
            .size(containerSize)
            .clip(CircleShape)
            .background(containerColor)
    } else {
        modifier.size(size)
    }

    Box(
        modifier = boxModifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(size)) {
            val cx = size.toPx() / 2f
            val cy = size.toPx() / 2f
            val maxRadius = (size.toPx() / 2f) * 0.85f

            // Determine current polygon stage and interpolation fraction alpha
            val currentStage = morphProgress.toInt() % 4
            val alpha = morphProgress - morphProgress.toInt()

            // Define (lobesCount, amplitudeFactor) for each stage:
            // 0: Pentagon (5 lobes, amp 0.16f)
            // 1: Cookie (8 lobes, amp 0.18f)
            // 2: Clover (4 lobes, amp 0.22f)
            // 3: Circle (0 lobes, amp 0.0f)
            val (n1, amp1) = when (currentStage) {
                0 -> 5f to 0.16f
                1 -> 8f to 0.18f
                2 -> 4f to 0.22f
                else -> 0f to 0.0f
            }
            val nextStage = (currentStage + 1) % 4
            val (n2, amp2) = when (nextStage) {
                0 -> 5f to 0.16f
                1 -> 8f to 0.18f
                2 -> 4f to 0.22f
                else -> 0f to 0.0f
            }

            // Smooth interpolation
            val currentN = n1 + (n2 - n1) * alpha
            val currentAmp = amp1 + (amp2 - amp1) * alpha

            val path = Path()
            val steps = 180
            val rotRad = (rotation * PI / 180f).toFloat()

            for (i in 0..steps) {
                val theta = (i * 2 * PI / steps).toFloat()
                val r = maxRadius * (1f - currentAmp + currentAmp * cos(currentN * theta + rotRad))
                val x = cx + r * cos(theta)
                val y = cy + r * sin(theta)

                if (i == 0) {
                    path.moveTo(x, y)
                } else {
                    path.lineTo(x, y)
                }
            }
            path.close()

            scale(scale = pulseScale) {
                drawPath(
                    path = path,
                    color = activeColor
                )
            }
        }
    }
}

/**
 * Backward compatibility overload.
 */
@Composable
fun AnimatedLoadingIndicator(
    size: Dp,
    iconTint: Color,
    modifier: Modifier = Modifier
) {
    AnimatedLoadingIndicator(
        modifier = modifier,
        isContained = false,
        indicatorColor = iconTint,
        size = size
    )
}

@Preview
@Composable
private fun PreviewUncontainedLoadingIndicator() {
    AnimatedLoadingIndicator(isContained = false)
}

@Preview
@Composable
private fun PreviewContainedLoadingIndicator() {
    AnimatedLoadingIndicator(isContained = true)
}
