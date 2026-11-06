package com.cairosquad.evolvefit.ui.screen.playWorkout.content

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cairosquad.evolvefit.design_system.component.CelebrationParticles
import com.cairosquad.evolvefit.design_system.component.ExpressiveLinearProgressIndicator
import com.cairosquad.evolvefit.design_system.component.PrimaryButton
import com.cairosquad.evolvefit.design_system.component.ProgressIndicatorStyle
import com.cairosquad.evolvefit.design_system.component.SecondaryButton
import com.cairosquad.evolvefit.design_system.theme.AppMotion
import com.cairosquad.evolvefit.design_system.theme.Theme
import com.cairosquad.evolvefit.ui.screen.nutrition.content.component.CelebrationCartoonMascot
import com.cairosquad.evolvefit.ui.screen.nutrition.content.component.CelebrationSunburstRays
import com.cairosquad.evolvefit.ui.screen.nutrition.content.component.CongratsType
import com.cairosquad.evolvefit.viewmodel.play_workout.PlayWorkoutInteractionListener
import com.cairosquad.evolvefit.viewmodel.play_workout.PlayWorkoutScreenState
import evolvefit.composeapp.generated.resources.Res
import evolvefit.composeapp.generated.resources.congratulations_message
import evolvefit.composeapp.generated.resources.congrats_goal_completed
import evolvefit.composeapp.generated.resources.exercises
import evolvefit.composeapp.generated.resources.finish
import evolvefit.composeapp.generated.resources.level_label
import evolvefit.composeapp.generated.resources.next_workout_button
import evolvefit.composeapp.generated.resources.workout_completed_message
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.stringResource

/**
 * Material 3 Expressive Workout Finished (Celebration / Congrats) Screen.
 * Design aligned with Calories & Water celebration screen:
 * - Clean deep dark surface background
 * - Rotating Sunburst Rays (Lime Green Glory)
 * - Multi-color celebratory Confetti Particles
 * - Hero Animated Kawaii Chubby Mascot (Squash & stretch, bobbing, blinking anime eyes, blush, smiling tongue)
 * - Material 3 Expressive 28.dp Stat Card with 100% green success badge & gapless wavy progress indicator
 * - Stacked Primary ("Next to Another Workout") & Secondary ("Finish") Action Buttons
 */
@Composable
fun PlayWorkoutContentFinish(
    screenState: PlayWorkoutScreenState,
    listener: PlayWorkoutInteractionListener,
    modifier: Modifier = Modifier
) {
    val primaryGreen = Theme.color.brand.primary
    val successColor = Theme.color.system.success
    val screenBackground = Theme.color.surfaces.surface
    val cardBackground = Theme.color.surfaces.surfaceContainer
    val cardBorder = Theme.color.surfaces.outlineVariant.copy(alpha = 0.5f)
    val cardShape = RoundedCornerShape(28.dp)

    // Entrance Spring Animation
    var contentVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(80)
        contentVisible = true
    }

    val contentScale by animateFloatAsState(
        targetValue = if (contentVisible) 1f else 0.8f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "WorkoutContentScale"
    )

    val barProgress by animateFloatAsState(
        targetValue = if (contentVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 900, easing = FastOutSlowInEasing),
        label = "WorkoutProgressBar"
    )

    // Counter animations for Exercises and Time
    val totalTimeSec = if (screenState.totalTimeSeconds > 0) {
        screenState.totalTimeSeconds
    } else {
        screenState.totalTimeMinutes * 60
    }

    val animatedExercises by animateIntAsState(
        targetValue = if (contentVisible) screenState.workout.exercises.size else 0,
        animationSpec = tween(durationMillis = 1000, easing = AppMotion.EmphasizedEasing),
        label = "AnimatedExercises"
    )

    val animatedSeconds by animateIntAsState(
        targetValue = if (contentVisible) totalTimeSec else 0,
        animationSpec = tween(durationMillis = 1200, easing = AppMotion.EmphasizedEasing),
        label = "AnimatedSeconds"
    )

    val hours = animatedSeconds / 3600
    val minutes = (animatedSeconds % 3600) / 60
    val seconds = animatedSeconds % 60
    val hoursText = hours.toString().padStart(2, '0')
    val minutesText = minutes.toString().padStart(2, '0')
    val secondsText = seconds.toString().padStart(2, '0')
    val totalTimeString = "$hoursText:$minutesText:$secondsText"

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(screenBackground),
        contentAlignment = Alignment.Center
    ) {
        // ── 1. Rotating Sunburst Rays Aura (Lime Green Glory) ───────────────────
        CelebrationSunburstRays(
            accentColor = primaryGreen,
            modifier = Modifier.fillMaxSize()
        )

        // ── 2. Confetti Particles ───────────────────────────────────────────
        CelebrationParticles(
            modifier = Modifier.fillMaxSize(),
            particleCount = 50,
            colors = listOf(
                primaryGreen,
                Color(0xFFFFD900), // Star Gold
                Color(0xFFFF4B4B), // Coral Red
                Color(0xFF29B6F6), // Sky Blue
                Color(0xFFCE82FF), // Purple
                Color.White
            )
        )

        // ── 3. Main Celebratory Content ─────────────────────────────────────
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
                .padding(top = 40.dp, bottom = 24.dp)
                .scale(contentScale),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Hero Animated Cartoon Mascot (Moving, Bouncing, Blinking Kawaii Mascot)
            CelebrationCartoonMascot(
                type = CongratsType.WORKOUT,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Cheerful Headline
            Text(
                text = stringResource(Res.string.congratulations_message) + "!",
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                color = Color.White,
                textAlign = TextAlign.Center,
                letterSpacing = 0.5.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Cheerful Subtitle
            Text(
                text = stringResource(Res.string.workout_completed_message),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Theme.color.surfaces.onSurfaceVariant,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp,
                modifier = Modifier.padding(horizontal = 12.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ── M3 Expressive Stat Card (Kotak / Card 28dp) ────────────────────
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(cardShape)
                    .background(cardBackground)
                    .border(1.dp, cardBorder, cardShape)
                    .padding(horizontal = 20.dp, vertical = 20.dp)
            ) {
                // Header Row: Category Emoji + Workout Name + 100% Success Badge
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "🏋️",
                            fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = screenState.workout.name,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    // 100% Badge (Tanpa centang, Warna hijau success, Pill M3 Expressive)
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(successColor.copy(alpha = 0.16f))
                            .border(1.dp, successColor.copy(alpha = 0.6f), CircleShape)
                            .padding(horizontal = 12.dp, vertical = 5.dp)
                    ) {
                        Text(
                            text = "100%",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black,
                            color = successColor
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // 3 Stat Metrics Row (Exercises, Jam:Mnt:Dtk, Level)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SingleStat(
                        value = animatedExercises.toString(),
                        label = stringResource(Res.string.exercises),
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 4.dp, vertical = 4.dp)
                    )
                    StatVerticalLine()
                    SingleStat(
                        value = totalTimeString,
                        label = "Jam:Mnt:Dtk",
                        modifier = Modifier
                            .weight(1.3f)
                            .padding(horizontal = 4.dp, vertical = 4.dp)
                    )
                    StatVerticalLine()
                    SingleStat(
                        value = stringResource(screenState.workout.level.nameResId),
                        label = stringResource(Res.string.level_label),
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 4.dp, vertical = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                // M3 Expressive Wavy Progress Bar (Full 100%)
                ExpressiveLinearProgressIndicator(
                    progress = barProgress,
                    style = ProgressIndicatorStyle.Wavy,
                    trackThickness = 8.dp,
                    indicatorColor = primaryGreen,
                    trackColor = Theme.color.surfaces.surfaceVariant.copy(alpha = 0.4f),
                    waveAmplitude = 2.5.dp,
                    waveLength = 16.dp,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Motivation Bottom Text
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "⭐ " + stringResource(Res.string.congrats_goal_completed),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Theme.color.surfaces.outline
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // ── Material 3 Expressive Action Buttons (Stacked, 12.dp spacing) ────
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                PrimaryButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(Res.string.next_workout_button),
                    onClick = listener::onNextToAnotherWorkoutClicked
                )
                SecondaryButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(Res.string.finish),
                    onClick = listener::onFinishClicked
                )
            }
        }
    }
}

@Composable
private fun StatVerticalLine(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(1.dp)
            .height(44.dp)
            .background(Theme.color.surfaces.outlineVariant.copy(alpha = 0.5f))
    )
}

@Composable
private fun SingleStat(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = value,
            style = Theme.textStyle.title.largeBold16,
            color = Theme.color.surfaces.textColor,
            maxLines = 1
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = label,
            style = Theme.textStyle.label.smallRegular12,
            color = Theme.color.surfaces.onSurfaceVariant,
            maxLines = 1
        )
    }
}