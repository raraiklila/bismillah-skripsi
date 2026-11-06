package com.cairosquad.evolvefit.design_system.component

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cairosquad.evolvefit.design_system.theme.AppMotion
import com.cairosquad.evolvefit.design_system.theme.Theme
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * Material 3 Expressive Progress Indicator Style.
 */
enum class ProgressIndicatorStyle {
    Flat,
    Wavy
}

/**
 * Material 3 Expressive Linear Progress Indicator.
 * Following https://m3.material.io/components/progress-indicators/overview:
 *
 * Supports Flat and Wavy tracks, thick (8dp) and standard (4dp) options,
 * gap size, stop indicator dot, and determinate / indeterminate modes.
 */
@Composable
fun ExpressiveLinearProgressIndicator(
    progress: Float? = null, // null for indeterminate
    modifier: Modifier = Modifier,
    style: ProgressIndicatorStyle = ProgressIndicatorStyle.Wavy,
    trackThickness: Dp = 8.dp,
    indicatorColor: Color = Theme.color.brand.primary,
    trackColor: Color = Theme.color.brand.primaryContainer,
    gapSize: Dp = 4.dp,
    waveAmplitude: Dp = 3.dp,
    waveLength: Dp = 16.dp
) {
    val isIndeterminate = progress == null
    val infiniteTransition = rememberInfiniteTransition(label = "M3LinearProgress")

    // Indeterminate animation offset
    val animOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = AppMotion.EmphasizedEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "M3LinearOffset"
    )

    // Wave animation offset
    val waveOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = AppMotion.EmphasizedEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "M3LinearWave"
    )

    val canvasHeight = if (style == ProgressIndicatorStyle.Wavy) trackThickness + waveAmplitude * 2 else trackThickness

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(canvasHeight)
    ) {
        val width = size.width
        val cy = size.height / 2f
        val strokeWidthPx = trackThickness.toPx()
        val gapPx = gapSize.toPx()
        val waveAmpPx = waveAmplitude.toPx()
        val waveLenPx = waveLength.toPx()

        // 1. Inactive Background Track
        drawLine(
            color = trackColor,
            start = Offset(0f, cy),
            end = Offset(width, cy),
            strokeWidth = strokeWidthPx,
            cap = StrokeCap.Round
        )

        // 2. Active Indicator Track
        val activeWidthFraction = if (isIndeterminate) {
            val startFrac = (animOffset * 1.4f - 0.4f).coerceIn(0f, 1f)
            val endFrac = (animOffset * 1.4f).coerceIn(0f, 1f)
            Pair(startFrac, endFrac)
        } else {
            Pair(0f, progress!!.coerceIn(0f, 1f))
        }

        val effectiveGap = if (progress != null && progress >= 0.999f) 0f else gapPx
        val startX = activeWidthFraction.first * width
        val endX = (activeWidthFraction.second * width - effectiveGap).coerceAtLeast(startX)

        if (endX > startX) {
            if (style == ProgressIndicatorStyle.Flat) {
                drawLine(
                    color = indicatorColor,
                    start = Offset(startX, cy),
                    end = Offset(endX, cy),
                    strokeWidth = strokeWidthPx,
                    cap = StrokeCap.Round
                )
            } else {
                // M3 Expressive Wavy path
                val wavePath = Path()
                val steps = ((endX - startX) / 2f).toInt().coerceAtLeast(10)
                val wavePhase = waveOffset * 2 * PI

                for (i in 0..steps) {
                    val x = startX + (i.toFloat() / steps) * (endX - startX)
                    val sinArg = ((x - startX) / waveLenPx) * 2 * PI + wavePhase
                    val y = cy + (sin(sinArg) * waveAmpPx).toFloat()

                    if (i == 0) wavePath.moveTo(x, y) else wavePath.lineTo(x, y)
                }

                drawPath(
                    path = wavePath,
                    color = indicatorColor,
                    style = Stroke(width = strokeWidthPx, cap = StrokeCap.Round)
                )
            }
        }

        // 3. Stop Indicator Dot for Determinate
        if (progress != null && progress < 0.999f) {
            drawCircle(
                color = indicatorColor,
                radius = strokeWidthPx / 2f,
                center = Offset(width - strokeWidthPx / 2f, cy)
            )
        }
    }
}

/**
 * Material 3 Expressive Circular Progress Indicator.
 * Following https://m3.material.io/components/progress-indicators/overview:
 *
 * Supports Flat and Wavy circular tracks, thick (8dp) and standard (4dp) options.
 */
@Composable
fun ExpressiveCircularProgressIndicator(
    progress: Float? = null, // null for indeterminate
    modifier: Modifier = Modifier,
    style: ProgressIndicatorStyle = ProgressIndicatorStyle.Wavy,
    indicatorSize: Dp = 52.dp,
    trackThickness: Dp = 8.dp,
    indicatorColor: Color = Theme.color.brand.primary,
    trackColor: Color = Theme.color.brand.primaryContainer,
    gapSize: Dp = 4.dp,
    waveAmplitude: Dp = 2.5.dp,
    waveCount: Int = 12
) {
    val isIndeterminate = progress == null
    val infiniteTransition = rememberInfiniteTransition(label = "M3CircularProgress")

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = AppMotion.EmphasizedEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "M3CircularRotation"
    )

    val sweepAngleAnim by infiniteTransition.animateFloat(
        initialValue = 40f,
        targetValue = 280f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = AppMotion.EmphasizedEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "M3CircularSweep"
    )

    val waveOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = AppMotion.EmphasizedEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "M3CircularWave"
    )

    Canvas(modifier = modifier.size(indicatorSize)) {
        val strokeWidthPx = trackThickness.toPx()
        val radius = (size.minDimension - strokeWidthPx) / 2f
        val cx = size.width / 2f
        val cy = size.height / 2f

        // 1. Inactive Circular Background Track
        drawCircle(
            color = trackColor,
            radius = radius,
            center = Offset(cx, cy),
            style = Stroke(width = strokeWidthPx)
        )

        // 2. Active Indicator
        val startAngle = if (isIndeterminate) rotation else -90f
        val sweepAngle = if (isIndeterminate) sweepAngleAnim else (progress!! * 360f)

        if (style == ProgressIndicatorStyle.Flat) {
            drawArc(
                color = indicatorColor,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = strokeWidthPx, cap = StrokeCap.Round)
            )
        } else {
            // M3 Expressive Wavy Circular Active Arc
            val wavePath = Path()
            val steps = 180
            val startRad = (startAngle * PI / 180f).toFloat()
            val sweepRad = (sweepAngle * PI / 180f).toFloat()
            val waveAmpPx = waveAmplitude.toPx()
            val wavePhase = waveOffset * 2 * PI

            for (i in 0..steps) {
                val angle = startRad + (i.toFloat() / steps) * sweepRad
                val waveVal = sin(waveCount * angle + wavePhase).toFloat()
                val r = radius + waveVal * waveAmpPx

                val x = cx + r * cos(angle)
                val y = cy + r * sin(angle)

                if (i == 0) wavePath.moveTo(x, y) else wavePath.lineTo(x, y)
            }

            drawPath(
                path = wavePath,
                color = indicatorColor,
                style = Stroke(width = strokeWidthPx, cap = StrokeCap.Round)
            )
        }
    }
}

@Preview
@Composable
private fun PreviewExpressiveProgressIndicators() {
    ExpressiveLinearProgressIndicator(progress = 0.65f, style = ProgressIndicatorStyle.Wavy)
}
