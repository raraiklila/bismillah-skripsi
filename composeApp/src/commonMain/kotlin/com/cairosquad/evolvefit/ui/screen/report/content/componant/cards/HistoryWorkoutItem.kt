package com.cairosquad.evolvefit.ui.screen.report.content.componant.cards

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.cairosquad.evolvefit.design_system.theme.Theme
import com.cairosquad.evolvefit.design_system.util.NetworkImage
import evolvefit.composeapp.generated.resources.Res
import evolvefit.composeapp.generated.resources.duration
import evolvefit.composeapp.generated.resources.exercises
import evolvefit.composeapp.generated.resources.level_label
import evolvefit.composeapp.generated.resources.workouts
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun HistoryWorkoutItem(
    workoutName: String,
    workoutDate: String,
    workoutImage: String,
    exerciseCount: Int,
    duration: String,
    level: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            NetworkImage(
                modifier = Modifier
                    .size(width = 72.dp, height = 56.dp)
                    .clip(Theme.shapes.large),
                model = workoutImage,
                contentDescription = stringResource(Res.string.workouts),
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = workoutName,
                    style = Theme.textStyle.title.titleMediumEmphasized,
                    color = Theme.color.surfaces.onSurfaceContainer
                )
                Text(
                    text = workoutDate,
                    style = Theme.textStyle.label.smallRegular12,
                    color = Theme.color.surfaces.onSurfaceVariant
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Theme.color.surfaces.surfaceContainerHighest.copy(alpha = 0.55f),
                    shape = Theme.shapes.cardSmallShape
                )
                .padding(vertical = 10.dp, horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            WorkoutDetailsItem(
                modifier = Modifier.weight(1f),
                title = stringResource(Res.string.exercises),
                value = exerciseCount.toString()
            )
            WorkoutDetailsItem(
                modifier = Modifier.weight(1f),
                title = stringResource(Res.string.duration),
                value = duration
            )
            WorkoutDetailsItem(
                modifier = Modifier.weight(1.4f),
                title = stringResource(Res.string.level_label),
                value = level,
                isHighlightedBadge = true
            )
        }
    }
}

@Composable
private fun WorkoutDetailsItem(
    title: String,
    value: String,
    isHighlightedBadge: Boolean = false,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = Theme.textStyle.label.smallRegular12,
            color = Theme.color.surfaces.onSurfaceVariant
        )
        if (isHighlightedBadge) {
            Box(
                modifier = Modifier
                    .background(
                        color = Theme.color.brand.primary.copy(alpha = 0.16f),
                        shape = Theme.shapes.pillShape
                    )
                    .padding(horizontal = 8.dp, vertical = 2.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = value,
                    style = Theme.textStyle.label.labelSmallEmphasized,
                    color = Theme.color.brand.primary,
                    maxLines = 1,
                    softWrap = false,
                    overflow = TextOverflow.Ellipsis
                )
            }
        } else {
            Text(
                text = value,
                style = Theme.textStyle.title.titleSmallEmphasized,
                color = Theme.color.surfaces.onSurfaceContainer
            )
        }
    }
}

@Preview
@Composable
private fun HistoryWorkoutItemPreview() {
    HistoryWorkoutItem(
        workoutName = "Bodyweight Squats",
        workoutDate = "Today, 8:30 AM",
        workoutImage = "",
        exerciseCount = 5,
        duration = "45 min",
        level = "Intermediate"
    )
}