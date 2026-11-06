package com.cairosquad.evolvefit.ui.screen.playWorkout.content.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cairosquad.evolvefit.design_system.component.ExpressiveCircularProgressIndicator
import com.cairosquad.evolvefit.design_system.component.PrimaryButton
import com.cairosquad.evolvefit.design_system.component.ProgressIndicatorStyle
import com.cairosquad.evolvefit.design_system.component.clockTimer.ClockTimer
import com.cairosquad.evolvefit.design_system.component.clockTimer.ClockTimerState
import com.cairosquad.evolvefit.design_system.component.clockTimer.rememberClockTimerState
import com.cairosquad.evolvefit.design_system.theme.AppTheme
import com.cairosquad.evolvefit.design_system.theme.Theme
import evolvefit.composeapp.generated.resources.Res
import evolvefit.composeapp.generated.resources.seconds_label
import evolvefit.composeapp.generated.resources.skip_rest_button
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun RestTimer(
    timeInSeconds: Int,
    onFinish: () -> Unit,
    onClickSkipRest: () -> Unit,
    modifier: Modifier = Modifier,
    timeIncrement: Int = 15,
    textColor: Color = Theme.color.surfaces.onSurface,
    textStyle: TextStyle = Theme.textStyle.title.largeBold16.copy(fontSize = 44.sp),
) {
    val clockTimerState = rememberClockTimerState(
        totalTime = timeInSeconds,
        onFinish = onFinish
    )

    RestTimer(
        modifier = modifier,
        clockTimerState = clockTimerState,
        onClickSkipRest = onClickSkipRest,
        timeIncrement = timeIncrement,
        textColor = textColor,
        textStyle = textStyle,
    )
}

@Composable
fun RestTimer(
    clockTimerState: ClockTimerState,
    onClickSkipRest: () -> Unit,
    modifier: Modifier = Modifier,
    timeIncrement: Int = 15,
    textColor: Color = Theme.color.surfaces.onSurface,
    textStyle: TextStyle = Theme.textStyle.title.largeBold16.copy(fontSize = 44.sp),
) {
    val currentSeconds by clockTimerState.currentTimeInSeconds.collectAsState(initial = clockTimerState.totalTimeInSeconds)
    val totalSec = clockTimerState.totalTimeInSeconds ?: 1
    val curSec = currentSeconds ?: 0
    val progress = if (totalSec > 0) {
        (curSec.toFloat() / totalSec.toFloat()).coerceIn(0f, 1f)
    } else 0f

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(bottom = Theme.spacing.space300)
        ) {
            ExpressiveCircularProgressIndicator(
                progress = progress,
                style = ProgressIndicatorStyle.Wavy,
                indicatorSize = 180.dp,
                trackThickness = 10.dp,
                indicatorColor = Theme.color.brand.primary,
                trackColor = Theme.color.surfaces.surfaceContainer,
                waveAmplitude = 3.5.dp,
                waveCount = 14
            )
            ClockTimer(
                clockTimerState = clockTimerState,
                textColor = textColor,
                textStyle = textStyle,
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(Theme.spacing.space150)
        ) {
            PrimaryButton(
                modifier = Modifier.weight(1f),
                text = "+${timeIncrement} " + stringResource(Res.string.seconds_label),
                onClick = { clockTimerState.addSeconds(timeIncrement) },
                enabledContainerColor = Theme.color.surfaces.surfaceContainer,
                enabledTextColor = Theme.color.surfaces.onSurface
            )
            PrimaryButton(
                modifier = Modifier.weight(1f),
                text = stringResource(Res.string.skip_rest_button),
                onClick = onClickSkipRest,
                enabledContainerColor = Theme.color.brand.primary,
                enabledTextColor = Theme.color.brand.onPrimary
            )
        }
    }
}

@Preview
@Composable
private fun RestTimerPreview() {
    AppTheme(isDarkTheme = true) {
        RestTimer(
            modifier = Modifier.padding(16.dp),
            timeInSeconds = 30,
            onFinish = {},
            onClickSkipRest = {}
        )
    }
}