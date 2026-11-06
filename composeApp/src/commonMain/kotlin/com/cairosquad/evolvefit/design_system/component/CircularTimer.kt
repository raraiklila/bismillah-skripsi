package com.cairosquad.evolvefit.design_system.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cairosquad.evolvefit.design_system.theme.AppTheme
import com.cairosquad.evolvefit.design_system.theme.Theme
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Material 3 Expressive Morphing Circular Timer.
 * Features M3 Expressive Wavy Bubble Track ("gelembung-gelembung morphing shape") around countdown digits.
 */
@Composable
fun CircularTimer(
    timeInSeconds: Int,
    onFinish: (Float) -> Unit,
    modifier: Modifier = Modifier,
    reverse: Boolean = false,
    radius: Dp = 75.dp,
    strokeWidth: Dp = 10.dp,
    animationDurationMilli: Int = timeInSeconds * 1000,
    animationDelay: Int = 0,
    progressColor: Color = Theme.color.brand.primary,
    textStyle: TextStyle = Theme.textStyle.title.largeBold16.copy(fontSize = 44.sp),
    textColor: Color = Theme.color.surfaces.textColor,
    backgroundColor: Color = Theme.color.surfaces.surfaceContainer
) {
    var hasAnimationPlayed by remember { mutableStateOf(false) }

    val startValue = remember { if (reverse) timeInSeconds.toFloat() else 0f }
    val endValue = remember { if (reverse) 0f else timeInSeconds.toFloat() }

    val animateValue by animateFloatAsState(
        targetValue = if (hasAnimationPlayed) endValue else startValue,
        animationSpec = tween(
            durationMillis = animationDurationMilli,
            delayMillis = animationDelay,
            easing = LinearEasing
        ),
        finishedListener = onFinish
    )

    val progressPercent = if (reverse) (animateValue / timeInSeconds) else (1f - animateValue / timeInSeconds)

    LaunchedEffect(timeInSeconds) {
        hasAnimationPlayed = true
    }

    val isUrgent = remember(animateValue) { animateValue.toInt() in 1..3 }
    val displayColor = if (isUrgent) Theme.color.system.warning else progressColor

    Box(
        modifier = modifier.size(radius * 2f),
        contentAlignment = Alignment.Center,
    ) {
        ExpressiveCircularProgressIndicator(
            progress = progressPercent.coerceIn(0f, 1f),
            style = ProgressIndicatorStyle.Wavy,
            indicatorSize = radius * 2f,
            trackThickness = strokeWidth,
            indicatorColor = displayColor,
            trackColor = backgroundColor,
            waveAmplitude = 3.dp,
            waveCount = 14
        )

        Text(
            text = animateValue.toInt().toString(),
            style = textStyle,
            color = if (isUrgent) Theme.color.system.warning else textColor,
        )
    }
}

@Preview
@Composable
fun CircularCounterPreview() {
    AppTheme(isDarkTheme = true) {
        Box(
            modifier = Modifier
                .background(Theme.color.surfaces.surface)
                .padding(16.dp)
        ) {
            CircularTimer(
                timeInSeconds = 15,
                onFinish = { },
                reverse = true
            )
        }
    }
}
