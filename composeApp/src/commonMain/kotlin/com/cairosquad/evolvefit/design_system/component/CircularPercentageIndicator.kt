package com.cairosquad.evolvefit.design_system.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cairosquad.evolvefit.design_system.theme.AppMotion
import com.cairosquad.evolvefit.design_system.theme.Theme
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Material 3 Expressive Circular Progress Indicator.
 * Following official https://m3.material.io/components/progress-indicators/overview:
 * Features Emphasized Decelerate motion, rounded caps, and expressive typography.
 */
@Composable
fun CircularProgressCard(
    modifier: Modifier = Modifier,
    percentage: Float,
    number: Int = 100,
    radius: Dp = 32.dp,
    strokeWidth: Dp = 3.dp,
    animationDuration: Int = 1200,
    animationDelay: Int = 100,
    progressColor: Color,
    backgroundColor: Color = Theme.color.surfaces.surfaceVariant,
    isWavy: Boolean = true
) {
    var hasAnimationPlayed by remember { mutableStateOf(false) }

    val animateCurrentPercentage by animateFloatAsState(
        targetValue = if (hasAnimationPlayed) percentage else 0f,
        animationSpec = tween(
            durationMillis = animationDuration,
            delayMillis = animationDelay,
            easing = AppMotion.EmphasizedEasing
        ),
        label = "M3ExpressiveProgress"
    )

    LaunchedEffect(percentage) {
        hasAnimationPlayed = true
    }

    Box(
        modifier = modifier.size(radius * 2f + strokeWidth),
        contentAlignment = Alignment.Center,
    ) {
        Canvas(modifier = Modifier.size(radius * 2f)) {
            val strokeWidthPx = strokeWidth.toPx()
            val r = (size.minDimension) / 2f
            val cx = size.width / 2f
            val cy = size.height / 2f

            // Background track with smooth rounded caps
            drawArc(
                color = backgroundColor,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(strokeWidthPx, cap = StrokeCap.Round)
            )

            // Expressive active progress arc
            if (animateCurrentPercentage > 0.001f) {
                val sweepAngle = (360f * animateCurrentPercentage).coerceIn(0f, 360f)
                if (isWavy) {
                    val wavePath = androidx.compose.ui.graphics.Path()
                    val steps = (sweepAngle * 0.8f).toInt().coerceAtLeast(20)
                    val startRad = (-90f * kotlin.math.PI / 180f).toFloat()
                    val sweepRad = (sweepAngle * kotlin.math.PI / 180f).toFloat()
                    val waveAmpPx = (2.5.dp).toPx()
                    val waveCount = 12

                    for (i in 0..steps) {
                        val angle = startRad + (i.toFloat() / steps) * sweepRad
                        val waveVal = kotlin.math.sin(waveCount * angle).toFloat()
                        val currentR = r + waveVal * waveAmpPx

                        val x = cx + currentR * kotlin.math.cos(angle)
                        val y = cy + currentR * kotlin.math.sin(angle)

                        if (i == 0) wavePath.moveTo(x, y) else wavePath.lineTo(x, y)
                    }

                    drawPath(
                        path = wavePath,
                        color = progressColor,
                        style = Stroke(width = strokeWidthPx, cap = StrokeCap.Round)
                    )
                } else {
                    drawArc(
                        color = progressColor,
                        startAngle = -90f,
                        sweepAngle = sweepAngle,
                        useCenter = false,
                        style = Stroke(strokeWidthPx, cap = StrokeCap.Round)
                    )
                }
            }
        }
        Text(
            text = "${(animateCurrentPercentage * number).toInt()}%",
            style = Theme.textStyle.headline.largeBold16,
            color = Theme.color.surfaces.textColor,
        )
    }
}

@Preview
@Composable
private fun CircularProgressCardPreview() {
    CircularProgressCard(
        percentage = 0.50f,
        number = 100,
        radius = 40.dp,
        strokeWidth = 8.dp,
        animationDuration = 1000,
        progressColor = Color.Blue
    )
}