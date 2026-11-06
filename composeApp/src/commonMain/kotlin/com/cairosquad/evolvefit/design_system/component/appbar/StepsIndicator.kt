package com.cairosquad.evolvefit.design_system.component.appbar

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cairosquad.evolvefit.design_system.component.ExpressiveLinearProgressIndicator
import com.cairosquad.evolvefit.design_system.component.ProgressIndicatorStyle
import com.cairosquad.evolvefit.design_system.theme.AppTheme
import com.cairosquad.evolvefit.design_system.theme.Theme
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Material 3 Expressive Steps Progress Indicator.
 * Renders an Expressive Wavy Linear Progress Track with Stop Indicator Dot matching M3 specifications.
 */
@Composable
fun StepsIndicator(
    currentStep: Int,
    totalSteps: Int,
    modifier: Modifier = Modifier,
    spacing: Dp = Theme.spacing.space50,
) {
    val progressRatio = if (totalSteps > 0) {
        (currentStep.toFloat() / totalSteps.toFloat()).coerceIn(0f, 1f)
    } else 0f

    val animatedProgress by animateFloatAsState(
        targetValue = progressRatio,
        animationSpec = tween(
            durationMillis = 400,
            easing = LinearOutSlowInEasing
        ),
        label = "M3StepsProgressAnimation"
    )

    ExpressiveLinearProgressIndicator(
        progress = animatedProgress,
        style = ProgressIndicatorStyle.Wavy,
        indicatorColor = Theme.color.brand.primary,
        trackColor = Theme.color.surfaces.surfaceContainer,
        trackThickness = 8.dp,
        waveAmplitude = 2.5.dp,
        waveLength = 14.dp,
        modifier = modifier
    )
}

@Preview
@Composable
private fun StepsIndicatorPreview() {
    AppTheme(isDarkTheme = true) {
        Column(
            modifier = Modifier
                .background(Theme.color.surfaces.surface)
                .padding(16.dp)
        ) {
            StepsIndicator(
                currentStep = 5,
                totalSteps = 7,
            )
        }
    }
}
