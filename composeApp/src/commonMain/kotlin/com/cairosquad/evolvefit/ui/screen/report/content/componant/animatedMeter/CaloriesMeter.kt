package com.cairosquad.evolvefit.ui.screen.report.content.componant.animatedMeter

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cairosquad.evolvefit.design_system.theme.AppMotion
import com.cairosquad.evolvefit.design_system.theme.AppTheme
import com.cairosquad.evolvefit.design_system.theme.Theme
import com.cairosquad.evolvefit.ui.util.toFormattedString
import evolvefit.composeapp.generated.resources.Res
import evolvefit.composeapp.generated.resources.from_calories
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CaloriesMeter(
    expectedCalories: Int,
    takenCalories: Int,
    isAnimationStarted: Boolean,
    modifier: Modifier = Modifier
) {
    val animSpec = if (isAnimationStarted) {
        AppMotion.SpringTokens.slowSpatial<Float>()
    } else {
        tween<Float>(
            durationMillis = 180,
            easing = AppMotion.EmphasizedAccelerate
        )
    }

    val targetFraction = (takenCalories.toFloat() / expectedCalories.coerceAtLeast(1)).coerceAtMost(1f)

    val animateCurrentPercentage by animateFloatAsState(
        targetValue = if (isAnimationStarted) targetFraction else 0f,
        animationSpec = animSpec,
        label = "caloriesSweep"
    )
    val rotationState by animateFloatAsState(
        targetValue = if (isAnimationStarted) 80f else 95f,
        animationSpec = animSpec,
        label = "caloriesRotation"
    )

    val offsetX by animateFloatAsState(
        targetValue = if (isAnimationStarted) 0f else -0.5f,
        animationSpec = animSpec,
        label = "caloriesOffset"
    )
    val sweepAngle = 180f * animateCurrentPercentage
    val strokeWidth = 13.dp

    val backgroundColor = Theme.color.surfaces.outlineVariant
    val progressColor = Theme.color.system.success
    val dashedArcColor = Theme.color.surfaces.outlineVariant

    Box(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .width(123.dp + strokeWidth)
                .align(Alignment.TopCenter)
        ) {
            Canvas(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .width(123.dp).height(123.dp)
            ) {
                val dashEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 8f), 0f)
                val innerPadding = 12.dp.toPx()
                drawArc(
                    color = backgroundColor,
                    startAngle = 180f,
                    sweepAngle = 180f,
                    useCenter = false,
                    style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
                )
                if (sweepAngle > 0.5f) {
                    drawArc(
                        color = progressColor,
                        startAngle = 180f,
                        sweepAngle = sweepAngle,
                        useCenter = false,
                        style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
                    )
                }

                drawArc(
                    color = dashedArcColor,
                    startAngle = 180f,
                    sweepAngle = 180f,
                    useCenter = true,
                    topLeft = Offset(innerPadding, innerPadding),
                    size = Size(
                        width = size.width - innerPadding * 2,
                        height = size.height - innerPadding
                    ),
                    style = Stroke(
                        width = 1.dp.toPx(),
                        pathEffect = dashEffect,
                        cap = StrokeCap.Butt
                    )
                )
            }
            Box(
                Modifier.size(width = (123 - 22).dp, height = 2.dp)
                    .align(Alignment.Center)
                    .offset(y = 6.dp)
                    .background(Theme.color.surfaces.surfaceContainer)
            )
            NeedleComponent(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(80.dp)
                    .offset(y = 11.dp),
                backgroundColor = backgroundColor,
                sweepAngle = sweepAngle,
                rotationState = rotationState,
                offsetX = offsetX
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = takenCalories.toUInt().toFormattedString(),
                style = Theme.textStyle.display.largeBold24.copy(fontWeight = FontWeight.ExtraBold),
                color = Theme.color.surfaces.textColor
            )
            Text(
                text = stringResource(
                    Res.string.from_calories,
                    expectedCalories.toUInt().toFormattedString()
                ),
                style = Theme.textStyle.label.smallRegular12,
                color = Theme.color.surfaces.onSurfaceVariant
            )
        }
    }
}

@Preview
@Composable
private fun CaloriesMeterPreview() {
    AppTheme(isDarkTheme = true) {
        CaloriesMeter(
            expectedCalories = 14000,
            takenCalories = 8450,
            isAnimationStarted = true
        )
    }
}