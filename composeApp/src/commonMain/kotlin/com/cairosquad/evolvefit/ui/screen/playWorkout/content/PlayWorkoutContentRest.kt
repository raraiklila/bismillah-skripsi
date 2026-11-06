package com.cairosquad.evolvefit.ui.screen.playWorkout.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cairosquad.evolvefit.design_system.theme.AppTheme
import com.cairosquad.evolvefit.design_system.theme.Theme
import com.cairosquad.evolvefit.design_system.theme.bouncyClickable
import com.cairosquad.evolvefit.design_system.util.NetworkImage
import com.cairosquad.evolvefit.ui.screen.playWorkout.content.component.RestTimer
import com.cairosquad.evolvefit.viewmodel.play_workout.PlayWorkoutInteractionListener
import com.cairosquad.evolvefit.viewmodel.play_workout.PlayWorkoutScreenState
import com.cairosquad.evolvefit.viewmodel.play_workout.PlayWorkoutViewModel
import evolvefit.composeapp.generated.resources.Res
import evolvefit.composeapp.generated.resources.exercise_info
import evolvefit.composeapp.generated.resources.ic_app_logo
import evolvefit.composeapp.generated.resources.ic_play
import evolvefit.composeapp.generated.resources.im_default_workout
import evolvefit.composeapp.generated.resources.next_movement
import evolvefit.composeapp.generated.resources.take_a_rest
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun PlayWorkoutContentRest(
    screenState: PlayWorkoutScreenState,
    listener: PlayWorkoutInteractionListener
) {
    val currentExercise = screenState.workout.exercises
        .getOrNull(screenState.currentStep - 1)
        ?: PlayWorkoutScreenState.ExerciseUiState()

    // Premium M3 Expressive Gradient Background (Crisp & High Contrast)
    val backgroundBrush = Brush.verticalGradient(
        colors = listOf(
            Theme.color.surfaces.surface,
            Theme.color.brand.primaryContainer.copy(alpha = 0.25f),
            Theme.color.surfaces.surface
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundBrush)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = Theme.spacing.space200)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Clean Text "Take a Rest" without container, positioned slightly lower
            Spacer(modifier = Modifier.height(28.dp))
            Text(
                text = stringResource(Res.string.take_a_rest),
                style = Theme.textStyle.display.largeBold20,
                color = Theme.color.surfaces.onSurface,
            )

            Spacer(modifier = Modifier.height(20.dp))

            // M3 Wavy Circular Rest Timer with countdown digits and action buttons
            RestTimer(
                modifier = Modifier.fillMaxWidth(),
                timeInSeconds = PlayWorkoutViewModel.REST_TIMER_IN_SECONDS,
                onClickSkipRest = listener::onSkipRestClicked,
                onFinish = listener::onRestFinishClicked,
                timeIncrement = PlayWorkoutViewModel.REST_TIMER_INCREMENT_IN_SECONDS,
                textColor = Theme.color.surfaces.onSurface,
                textStyle = Theme.textStyle.display.largeBold20.copy(
                    fontSize = 44.sp,
                    fontWeight = FontWeight.Black
                )
            )

            // Ergonomic spacing between buttons and the Next Movement card (no giant void)
            Spacer(modifier = Modifier.height(48.dp))

            // Next Movement Container Card (M3 Expressive Card)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(Theme.shapes.cardShape)
                    .background(Theme.color.surfaces.surfaceContainer)
                    .padding(Theme.spacing.space200)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(Res.string.next_movement),
                        style = Theme.textStyle.label.mediumMedium12,
                        color = Theme.color.brand.primary
                    )
                    Text(
                        text = "${screenState.currentStep}/${screenState.workout.exercises.size}",
                        style = Theme.textStyle.label.smallRegular12,
                        color = Theme.color.surfaces.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(Theme.spacing.space50))

                Text(
                    text = currentExercise.name,
                    style = Theme.textStyle.headline.mediumMedium18,
                    color = Theme.color.surfaces.onSurface,
                    modifier = Modifier.padding(bottom = Theme.spacing.space150)
                )

                NetworkImageWithPlayButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(4f / 3f)
                        .clip(Theme.shapes.medium)
                        .bouncyClickable(onClick = { listener.onExerciseInfoClicked(currentExercise.id) }),
                    model = currentExercise.imageUrls.firstOrNull() ?: "",
                    contentDescription = currentExercise.name,
                    defaultImage = painterResource(Res.drawable.ic_app_logo),
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun NetworkImageWithPlayButton(
    model: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    defaultImage: Painter = painterResource(Res.drawable.im_default_workout)
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        NetworkImage(
            modifier = Modifier.matchParentSize(),
            model = model,
            contentDescription = contentDescription,
            defaultImage = defaultImage,
            loadingPlaceHolder = painterResource(Res.drawable.ic_app_logo)
        )
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Theme.color.brand.primary)
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_play),
                contentDescription = stringResource(Res.string.exercise_info),
                tint = Theme.color.brand.onPrimary,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Preview
@Composable
private fun PlayWorkoutContentRestPreview() {
    AppTheme(isDarkTheme = true) {
        PlayWorkoutContentRest(
            screenState = PlayWorkoutScreenState(),
            listener = object : PlayWorkoutInteractionListener {
                override fun onCancelWorkoutClicked() {}
                override fun onGetReadyCounterFinish() {}
                override fun onStartClicked() {}
                override fun onExerciseInfoClicked(id: String) {}
                override fun onRestFinishClicked() {}
                override fun onFinishExercise() {}
                override fun onForwardClicked() {}
                override fun onBackClicked() {}
                override fun onSkipRestClicked() {}
                override fun onNextToAnotherWorkoutClicked() {}
                override fun onFinishClicked() {}
                override fun onStayInWorkoutClinked() {}
                override fun onEndClinked() {}
                override fun onDismissExerciseInfo() {}
            }
        )
    }
}