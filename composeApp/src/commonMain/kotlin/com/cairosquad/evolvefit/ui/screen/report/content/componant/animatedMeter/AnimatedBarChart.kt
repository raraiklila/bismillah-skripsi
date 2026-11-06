package com.cairosquad.evolvefit.ui.screen.report.content.componant.animatedMeter

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cairosquad.evolvefit.design_system.theme.AppMotion
import com.cairosquad.evolvefit.design_system.theme.Theme
import com.cairosquad.evolvefit.ui.screen.report.content.componant.animatedMeter.chartComponent.Tooltip
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AnimatedBarChart(
    data: List<Float>,
    focusedBarColor: Color,
    unfocusedBarColor: Color,
    isAnimationStarted: Boolean,
    animationDuration: Int = 1000,
    animationEasing: Easing = AppMotion.EmphasizedEasing,
    modifier: Modifier = Modifier,
    onBarCentersCalculated: (List<Float>) -> Unit = {}
) {
    val maxValue = (data.maxOrNull() ?: 0f).coerceAtLeast(1f)
    val maxIndex = data.indexOf(maxValue)

    val animatedHeights = data.map { value ->
        animateFloatAsState(
            targetValue = if (isAnimationStarted) value / maxValue else 0f,
            animationSpec = androidx.compose.animation.core.spring(
                dampingRatio = androidx.compose.animation.core.Spring.DampingRatioMediumBouncy,
                stiffness = androidx.compose.animation.core.Spring.StiffnessMediumLow
            ),
            label = "barHeightAnim"
        ).value
    }

    val density = LocalDensity.current
    var selectedIndex by remember { mutableStateOf(-1) }
    val barCenters = remember { mutableStateListOf<Offset>() }
    val barRects = remember { mutableStateListOf<androidx.compose.ui.geometry.Rect>() }

    BoxWithConstraints(
        modifier = modifier
            .height(144.dp)
            .fillMaxWidth()
    ) {

        Canvas(
            modifier = Modifier
                .matchParentSize()
                .pointerInput(data, animatedHeights, barRects) {
                    detectTapGestures { tap ->
                        val hitIndex = barRects.indexOfFirst { rect ->
                            tap.x >= rect.left && tap.x <= rect.right && tap.y >= rect.top && tap.y <= rect.bottom
                        }
                        selectedIndex = if (hitIndex != -1) hitIndex else -1
                    }
                }
        ) {
            val maxHeight = size.height
            val barCount = data.size
            val totalWidth = size.width

            val barWidthPx = totalWidth / (barCount * 2 - 1)
            val gapPx = barWidthPx

            val centers = mutableListOf<Offset>()
            val rects = mutableListOf<androidx.compose.ui.geometry.Rect>()

            data.forEachIndexed { index, _ ->
                val barHeight = animatedHeights[index] * maxHeight
                val barX = index * (barWidthPx + gapPx)
                val isMax = index == maxIndex
                val centerX = barX + barWidthPx / 2
                val topY = maxHeight - barHeight
                val bottomY = maxHeight
                centers.add(Offset(centerX, topY))

                rects.add(androidx.compose.ui.geometry.Rect(
                    left = barX,
                    top = topY,
                    right = barX + barWidthPx,
                    bottom = bottomY
                ))

                // 1. Material 3 Expressive Background Track Capsule (Shows full capacity scale)
                val trackRadius = androidx.compose.ui.geometry.CornerRadius(barWidthPx / 2f, barWidthPx / 2f)
                drawRoundRect(
                    color = unfocusedBarColor.copy(alpha = 0.25f),
                    topLeft = Offset(barX, 0f),
                    size = androidx.compose.ui.geometry.Size(barWidthPx, maxHeight),
                    cornerRadius = trackRadius
                )

                // 2. Material 3 Expressive Active Bar Capsule (Full Stadium / Pill Shape)
                if (barHeight > 0f) {
                    val activeBarRadius = androidx.compose.ui.geometry.CornerRadius(
                        barWidthPx / 2f,
                        (barWidthPx / 2f).coerceAtMost(barHeight / 2f)
                    )
                    drawRoundRect(
                        color = if (isMax) focusedBarColor else unfocusedBarColor,
                        topLeft = Offset(barX, topY),
                        size = androidx.compose.ui.geometry.Size(barWidthPx, barHeight),
                        cornerRadius = activeBarRadius
                    )
                }
            }

            barCenters.clear()
            barCenters.addAll(centers)

            barRects.clear()
            barRects.addAll(rects)

            onBarCentersCalculated(centers.map { it.x })
        }

        if (selectedIndex in barCenters.indices) {
            val extraDp = 12.dp
            val extraPx = with(density) { extraDp.toPx() }
            val anchor = barCenters[selectedIndex]
            val canvasY = anchor.y + extraPx

            Tooltip(
                canvasPoint = Offset(barCenters[selectedIndex].x, canvasY),
                density = density
            ) {
                Text(
                    text = data[selectedIndex].toString(),
                    color = Color.White,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Preview
@Composable
private fun AnimatedBarChartPreview() {
    AnimatedBarChart(
        data = listOf(10f, 20f, 30f, 40f, 50f),
        focusedBarColor = Theme.color.brand.primary,
        unfocusedBarColor = Theme.color.surfaces.surfaceVariant,
        true
    )
}