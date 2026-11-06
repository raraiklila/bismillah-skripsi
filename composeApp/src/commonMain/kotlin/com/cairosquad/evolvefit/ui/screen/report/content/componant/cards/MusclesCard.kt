package com.cairosquad.evolvefit.ui.screen.report.content.componant.cards

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.cairosquad.evolvefit.design_system.component.ExpressiveLinearProgressIndicator
import com.cairosquad.evolvefit.design_system.component.ProgressIndicatorStyle
import com.cairosquad.evolvefit.design_system.theme.AppMotion
import com.cairosquad.evolvefit.design_system.theme.Theme
import evolvefit.composeapp.generated.resources.Res
import evolvefit.composeapp.generated.resources.most_trained_muscles
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun MusclesCard(
    musclesName: List<String>,
    trainedMusclesPercentage: List<Float>,
    isAnimationStarted: Boolean,
    modifier: Modifier = Modifier,
) {
    ReportCard(
        modifier = modifier,
        title = stringResource(Res.string.most_trained_muscles),
        value = ""
    ) {
        repeat(musclesName.size) {
            MuscleItem(
                modifier = Modifier.then(
                    if (it < musclesName.size - 1) Modifier.padding(bottom = 16.dp)
                    else Modifier
                ),
                muscleName = musclesName[it],
                percentage = trainedMusclesPercentage[it],
                isAnimationStarted = isAnimationStarted
            )
        }
    }
}


@Composable
private fun MuscleItem(
    muscleName: String,
    percentage: Float,
    modifier: Modifier = Modifier,
    isAnimationStarted: Boolean = false
) {
    val animatePercentage by animateFloatAsState(
        targetValue = if (isAnimationStarted) percentage else 0f,
        animationSpec = tween(
            durationMillis = 1000,
            easing = AppMotion.EmphasizedEasing
        )
    )
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(height = 36.dp, width = 4.dp)
                .clip(Theme.shapes.pillShape)
                .background(
                    if (animatePercentage > 0f) Theme.color.brand.primary
                    else Theme.color.surfaces.outlineVariant
                )
        )
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = muscleName,
                    style = Theme.textStyle.label.smallRegular14,
                    color = Theme.color.surfaces.onSurface
                )
                Text(
                    text = "${(animatePercentage * 100).toInt()}%",
                    style = Theme.textStyle.label.labelLargeEmphasized,
                    color = if (animatePercentage > 0f) Theme.color.brand.primary else Theme.color.surfaces.onSurfaceVariant
                )
            }
            ExpressiveLinearProgressIndicator(
                progress = animatePercentage,
                style = if (animatePercentage > 0f) ProgressIndicatorStyle.Wavy else ProgressIndicatorStyle.Flat,
                trackThickness = 8.dp,
                indicatorColor = Theme.color.brand.primary,
                trackColor = Theme.color.surfaces.surfaceVariant
            )
        }
    }
}

@Preview
@Composable
private fun MusclesCardPreview() {
    val musclesName = listOf("Back", "Legs", "Shoulders", "Arms", "Core")
    val musclesPercentage = listOf(0.32f, 0.24f, 0.68f, 0.49f, 0.11f)
    MusclesCard(
        musclesName = musclesName,
        trainedMusclesPercentage = musclesPercentage,
        true
    )
}