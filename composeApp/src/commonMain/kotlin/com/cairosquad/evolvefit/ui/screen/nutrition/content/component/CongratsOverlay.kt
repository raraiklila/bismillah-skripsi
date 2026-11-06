package com.cairosquad.evolvefit.ui.screen.nutrition.content.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cairosquad.evolvefit.design_system.component.CelebrationParticles
import com.cairosquad.evolvefit.design_system.component.ExpressiveLinearProgressIndicator
import com.cairosquad.evolvefit.design_system.component.PrimaryButton
import com.cairosquad.evolvefit.design_system.component.ProgressIndicatorStyle
import com.cairosquad.evolvefit.design_system.theme.Theme
import evolvefit.composeapp.generated.resources.Res
import evolvefit.composeapp.generated.resources.congrats_button_continue
import evolvefit.composeapp.generated.resources.congrats_calories_headline
import evolvefit.composeapp.generated.resources.congrats_calories_subtitle
import evolvefit.composeapp.generated.resources.congrats_goal_completed
import evolvefit.composeapp.generated.resources.congrats_water_headline
import evolvefit.composeapp.generated.resources.congrats_water_subtitle
import evolvefit.composeapp.generated.resources.kcal_unit
import evolvefit.composeapp.generated.resources.liter_unit
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.stringResource
import kotlin.math.sin

enum class CongratsType {
    CALORIES, WATER, WORKOUT
}

@Composable
fun CongratsOverlay(
    isVisible: Boolean,
    type: CongratsType,
    currentValue: Float,
    goalValue: Float,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    autoDismissSeconds: Int = 5
) {
    var secondsLeft by remember { mutableIntStateOf(autoDismissSeconds) }

    LaunchedEffect(isVisible) {
        if (isVisible) {
            secondsLeft = autoDismissSeconds
            repeat(autoDismissSeconds) {
                delay(1000L)
                secondsLeft--
            }
            onDismiss()
        }
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(durationMillis = 300)),
        exit = fadeOut(animationSpec = tween(durationMillis = 200))
    ) {
        CelebrationScreen(
            type = type,
            currentValue = currentValue,
            goalValue = goalValue,
            secondsLeft = secondsLeft,
            onDismiss = onDismiss,
            modifier = modifier
        )
    }
}

/**
 * Material 3 Expressive celebration screen.
 * Features:
 * - Animated rotating sunburst rays aura
 * - Lively animated cartoon mascot that moves, blinks, breathes & bounces (Flame for Calories, Water Drop for Hydration)
 * - Material 3 Expressive stat card with rounded container (28dp)
 * - Stat row with category emoji (🥣 for calories, 💧 for water)
 * - Clean "100%" success pill badge without checkmark in success green
 * - M3 Expressive Wavy Progress Bar with rounded endpoints
 * - Design system PrimaryButton with M3 expressive spring bounce physics
 * - Multi-color celebratory confetti particles
 */
@Composable
private fun CelebrationScreen(
    type: CongratsType,
    currentValue: Float,
    goalValue: Float,
    secondsLeft: Int,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isCalories = type == CongratsType.CALORIES

    val primaryGreen = Theme.color.brand.primary
    val primaryBlue = Theme.color.system.info
    val primaryAccent = if (isCalories) primaryGreen else primaryBlue
    val successColor = Theme.color.system.success

    val cardBackground = Theme.color.surfaces.surfaceContainer
    val cardBorder = Theme.color.surfaces.outlineVariant.copy(alpha = 0.5f)
    val cardShape = RoundedCornerShape(28.dp)
    val screenBackground = Theme.color.surfaces.surface

    val headline = stringResource(
        if (isCalories) Res.string.congrats_calories_headline else Res.string.congrats_water_headline
    )
    val subtitle = stringResource(
        if (isCalories) Res.string.congrats_calories_subtitle else Res.string.congrats_water_subtitle
    )
    val unit = stringResource(
        if (isCalories) Res.string.kcal_unit else Res.string.liter_unit
    )

    val valueDisplay = if (isCalories) {
        currentValue.toInt().toString()
    } else {
        ((currentValue * 10).toInt() / 10f).toString()
    }
    val goalDisplay = if (isCalories) {
        goalValue.toInt().toString()
    } else {
        ((goalValue * 10).toInt() / 10f).toString()
    }

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
        label = "ContentScale"
    )

    // Animated Bar Progress
    val barProgress by animateFloatAsState(
        targetValue = if (contentVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 900, easing = FastOutSlowInEasing),
        label = "ProgressBar"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(screenBackground)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onDismiss
            ),
        contentAlignment = Alignment.Center
    ) {
        // ── 1. Rotating Sunburst Rays Aura ─────────────────────────────────────
        CelebrationSunburstRays(
            accentColor = primaryAccent,
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
                primaryBlue,
                Color(0xFFCE82FF), // Purple
                Color.White
            )
        )

        // ── 3. Main Celebratory Content (Non-clickable container to pass dismiss outside)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .scale(contentScale)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = { /* prevent tap inside from dismissing immediately */ }
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // ── Hero Animated Cartoon Mascot (Gambar 2: Kartun/Animasi Gerak2) ──────
            CelebrationCartoonMascot(
                type = type,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // ── Cheerful Title ────────────────────────────────────────────
            Text(
                text = headline,
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                color = Color.White,
                textAlign = TextAlign.Center,
                letterSpacing = 0.5.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = subtitle,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Theme.color.surfaces.onSurfaceVariant,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp,
                modifier = Modifier.padding(horizontal = 12.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ── M3 Expressive Stat Card (Kotak / Card 28dp) ────────────────────────
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(cardShape)
                    .background(cardBackground)
                    .border(1.dp, cardBorder, cardShape)
                    .padding(horizontal = 20.dp, vertical = 20.dp)
            ) {
                // Stat Header Row (Gambar 3: Emot Sesuai Nutrisi/Kalori & Liter Air)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = if (isCalories) "🥣" else "💧",
                            fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "$valueDisplay / $goalDisplay $unit",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                    }

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

                // M3 Expressive Wavy Progress Bar (Rounded caps, Full width)
                ExpressiveLinearProgressIndicator(
                    progress = barProgress,
                    style = ProgressIndicatorStyle.Wavy,
                    trackThickness = 8.dp,
                    indicatorColor = primaryAccent,
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

            // ── Design System Primary Button with M3 Expressive Bouncy Click ────
            PrimaryButton(
                text = "${stringResource(Res.string.congrats_button_continue)}  ($secondsLeft)",
                enabledContainerColor = primaryAccent,
                enabledTextColor = if (isCalories) Theme.color.brand.onPrimary else Color.White,
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

/**
 * Kawaii Chubby Cartoon Mascot (Hero) matching Gambar 2:
 * - Chubby dumpling / teardrop geometry (wide plump base, soft pillowy cheeks, pointed apex)
 * - Inner diamond/almond flame patch (warm custard yellow for Calories, icy baby blue for Water)
 * - Dual color themes:
 *     * Calories: Golden Amber -> Bright Orange -> Red-Orange base
 *     * Water: Electric Cyan -> Azure Blue -> Deep Ocean Blue base
 * - Big shiny anime eyes with double white sparkle highlights
 * - Natural eye blinking animation (~2.8s interval)
 * - Soft horizontal oval blushing cheeks beside the eyes
 * - Cheerful open smiling mouth with cute pink tongue
 * - Bouncy bobbing & squash/stretch physics
 * - Subtle apex tip sway
 * - Ambient floating particles (embers / bubbles) & soft glow aura
 */
@Composable
fun CelebrationCartoonMascot(
    type: CongratsType,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "CartoonMascotAnim")

    // 1. Bobbing / floating Y offset
    val bobOffset by infiniteTransition.animateFloat(
        initialValue = -7f,
        targetValue = 7f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "MascotBob"
    )

    // 2. Squash and stretch bounce physics (squishy kawaii jelly/dumpling)
    val squashX by infiniteTransition.animateFloat(
        initialValue = 0.96f,
        targetValue = 1.04f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "MascotSquashX"
    )
    val squashY by infiniteTransition.animateFloat(
        initialValue = 1.04f,
        targetValue = 0.96f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "MascotSquashY"
    )

    // 3. Tip sway / wiggle
    val tipSway by infiniteTransition.animateFloat(
        initialValue = -3.5f,
        targetValue = 3.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "MascotTipSway"
    )

    // 4. Glow pulse
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.25f,
        targetValue = 0.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "MascotGlowPulse"
    )

    // 5. Periodic Eye Blinking (Blinks naturally every ~2.8s)
    var isBlinking by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        while (true) {
            delay(2800L)
            isBlinking = true
            delay(160L)
            isBlinking = false
        }
    }

    // 6. Floating ambient particles (embers, bubbles, or energy sparks)
    val particlePhase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "MascotParticlePhase"
    )

    Box(
        modifier = modifier
            .size(156.dp)
            .offset { IntOffset(0, bobOffset.dp.roundToPx()) },
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .size(146.dp)
                .graphicsLayer {
                    scaleX = squashX
                    scaleY = squashY
                }
        ) {
            val w = size.width
            val h = size.height

            // ── A. Glow Aura Background ──────────────────────────────────────────
            val glowColor = when (type) {
                CongratsType.CALORIES -> Color(0xFFFF9800)
                CongratsType.WATER -> Color(0xFF00E5FF)
                CongratsType.WORKOUT -> Color(0xFF76FF03)
            }
            drawCircle(
                color = glowColor.copy(alpha = glowAlpha * 0.35f),
                radius = w * 0.48f,
                center = Offset(w / 2f, h * 0.58f)
            )
            drawCircle(
                color = glowColor.copy(alpha = glowAlpha * 0.16f),
                radius = w * 0.58f,
                center = Offset(w / 2f, h * 0.58f)
            )

            // ── B. Floating mini particles (embers / bubbles / energy sparks) ──────
            val particleOffsets = listOf(
                Pair(0.12f, 0.35f),
                Pair(0.88f, 0.38f),
                Pair(0.18f, 0.76f),
                Pair(0.84f, 0.74f)
            )
            particleOffsets.forEachIndexed { idx, (baseX, baseY) ->
                val progress = (particlePhase + idx * 0.25f) % 1f
                val py = h * (baseY - progress * 0.35f)
                val px = w * (baseX + sin(progress * 6.28f + idx) * 0.05f)
                val pAlpha = (1f - progress) * 0.85f
                val pRadius = (3.dp + (idx % 3).dp).toPx() * (1f - progress * 0.35f)

                when (type) {
                    CongratsType.CALORIES -> {
                        // Golden Ember spark
                        drawCircle(
                            color = Color(0xFFFFD54F).copy(alpha = pAlpha),
                            radius = pRadius,
                            center = Offset(px, py)
                        )
                    }
                    CongratsType.WATER -> {
                        // Shimmering Water bubble
                        drawCircle(
                            color = Color(0xFF80D8FF).copy(alpha = pAlpha),
                            radius = pRadius,
                            center = Offset(px, py),
                            style = Stroke(width = 1.5.dp.toPx())
                        )
                        drawCircle(
                            color = Color.White.copy(alpha = pAlpha * 0.6f),
                            radius = pRadius * 0.35f,
                            center = Offset(px - pRadius * 0.3f, py - pRadius * 0.3f)
                        )
                    }
                    CongratsType.WORKOUT -> {
                        // Energetic Lime-Gold Star / Energy Orb
                        drawCircle(
                            color = Color(0xFFAEEA00).copy(alpha = pAlpha),
                            radius = pRadius,
                            center = Offset(px, py)
                        )
                        drawCircle(
                            color = Color(0xFFFFFFFF).copy(alpha = pAlpha * 0.75f),
                            radius = pRadius * 0.4f,
                            center = Offset(px, py)
                        )
                    }
                }
            }

            // ── C. Chubby Mascot Body Path (Gambar 2 Plump Onigiri/Slime Silhouette)
            val tipX = w * 0.5f + (tipSway * 1.2f)
            val tipY = h * 0.13f

            val chubbyBodyPath = Path().apply {
                moveTo(tipX, tipY)
                // Top-right slope down to chubby right cheek
                cubicTo(
                    w * 0.63f, h * 0.28f,
                    w * 0.89f, h * 0.54f,
                    w * 0.90f, h * 0.72f
                )
                // Chubby right cheek curving down to bottom base
                cubicTo(
                    w * 0.91f, h * 0.84f,
                    w * 0.74f, h * 0.88f,
                    w * 0.50f, h * 0.87f
                )
                // Bottom base curving to chubby left cheek
                cubicTo(
                    w * 0.26f, h * 0.88f,
                    w * 0.09f, h * 0.84f,
                    w * 0.10f, h * 0.72f
                )
                // Chubby left cheek curving up to apex tip
                cubicTo(
                    w * 0.11f, h * 0.54f,
                    w * 0.37f, h * 0.28f,
                    tipX, tipY
                )
                close()
            }

            val bodyBrush = when (type) {
                CongratsType.CALORIES -> {
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFFA000), // Golden Amber at tip
                            Color(0xFFFF6D00), // Vibrant Bright Orange
                            Color(0xFFFF3D00)  // Warm Coral Red-Orange at base
                        ),
                        startY = h * 0.10f,
                        endY = h * 0.90f
                    )
                }
                CongratsType.WATER -> {
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF80D8FF), // Radiant Cyan at tip
                            Color(0xFF00B0FF), // Vibrant Azure Blue
                            Color(0xFF0288D1)  // Deep Ocean Blue at base
                        ),
                        startY = h * 0.10f,
                        endY = h * 0.90f
                    )
                }
                CongratsType.WORKOUT -> {
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFCCFF00), // Electric Neon Lime at tip
                            Color(0xFF76FF03), // Vibrant Energetic Green
                            Color(0xFF00C853)  // Deep Athletic Emerald at base
                        ),
                        startY = h * 0.10f,
                        endY = h * 0.90f
                    )
                }
            }

            drawPath(path = chubbyBodyPath, brush = bodyBrush)

            // ── D. Inner Face / Belly Diamond-Almond Flame Patch ───────────────────
            val innerPatchPath = Path().apply {
                val patchTipX = w * 0.50f + (tipSway * 0.6f)
                val patchTipY = h * 0.37f
                val patchBottomX = w * 0.50f
                val patchBottomY = h * 0.84f

                moveTo(patchTipX, patchTipY)
                // Upper right arc
                cubicTo(
                    w * 0.58f, h * 0.44f,
                    w * 0.66f, h * 0.53f,
                    w * 0.66f, h * 0.61f
                )
                // Lower right arc
                cubicTo(
                    w * 0.66f, h * 0.71f,
                    w * 0.58f, h * 0.79f,
                    patchBottomX, patchBottomY
                )
                // Lower left arc
                cubicTo(
                    w * 0.42f, h * 0.79f,
                    w * 0.34f, h * 0.71f,
                    w * 0.34f, h * 0.61f
                )
                // Upper left arc
                cubicTo(
                    w * 0.34f, h * 0.53f,
                    w * 0.42f, h * 0.44f,
                    patchTipX, patchTipY
                )
                close()
            }

            val patchBrush = when (type) {
                CongratsType.CALORIES -> {
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFFFDE7), // Pastel cream
                            Color(0xFFFFF176), // Warm bright buttercup yellow
                            Color(0xFFFFD54F)  // Amber glow yellow
                        ),
                        startY = h * 0.35f,
                        endY = h * 0.85f
                    )
                }
                CongratsType.WATER -> {
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFFFFFF), // Crisp glistening white
                            Color(0xFFE1F5FE), // Pure ice baby blue
                            Color(0xFFB3E5FC)  // Soft sky blue
                        ),
                        startY = h * 0.35f,
                        endY = h * 0.85f
                    )
                }
                CongratsType.WORKOUT -> {
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFFFFE0), // Light ivory cream
                            Color(0xFFF4FF81), // Pastel lime-cream
                            Color(0xFFFFEE58)  // Bright gold highlight
                        ),
                        startY = h * 0.35f,
                        endY = h * 0.85f
                    )
                }
            }

            drawPath(path = innerPatchPath, brush = patchBrush)

            // ── E. Water Specular Highlight (for Water Droplet Sheen) ─────────────
            if (type == CongratsType.WATER) {
                val waterSheenPath = Path().apply {
                    moveTo(w * 0.37f, h * 0.23f)
                    cubicTo(
                        w * 0.28f, h * 0.33f,
                        w * 0.22f, h * 0.45f,
                        w * 0.25f, h * 0.53f
                    )
                    cubicTo(
                        w * 0.23f, h * 0.45f,
                        w * 0.27f, h * 0.34f,
                        w * 0.39f, h * 0.25f
                    )
                    close()
                }
                drawPath(path = waterSheenPath, color = Color.White.copy(alpha = 0.50f))
            }

            // ── F. Kawaii Facial Details (Eyes, Blush, Cheerful Smile) ───────────
            val eyeCenterY = h * 0.565f
            val eyeSpacing = w * 0.11f
            val leftEyeX = w * 0.50f - eyeSpacing
            val rightEyeX = w * 0.50f + eyeSpacing
            val eyeRadius = 9.dp.toPx()

            // 1. Blushing Cheeks (Horizontal oval/pill blush beside eyes)
            val blushColor = when (type) {
                CongratsType.CALORIES -> Color(0xFFFF5252).copy(alpha = 0.70f)
                CongratsType.WATER -> Color(0xFFFF80AB).copy(alpha = 0.65f)
                CongratsType.WORKOUT -> Color(0xFFFF4081).copy(alpha = 0.65f)
            }
            val blushW = 14.dp.toPx()
            val blushH = 6.5.dp.toPx()
            val cheekCenterY = eyeCenterY + 7.dp.toPx()
            val leftCheekX = leftEyeX - 16.dp.toPx()
            val rightCheekX = rightEyeX + 16.dp.toPx()

            drawOval(
                color = blushColor,
                topLeft = Offset(leftCheekX - blushW / 2f, cheekCenterY - blushH / 2f),
                size = Size(blushW, blushH)
            )
            drawOval(
                color = blushColor,
                topLeft = Offset(rightCheekX - blushW / 2f, cheekCenterY - blushH / 2f),
                size = Size(blushW, blushH)
            )

            // 2. Kawaii Eyes (Blinking vs Big Shiny Anime Eyes)
            if (isBlinking) {
                // Happy squinting smiling arcs (＾ ＾)
                val strokeW = 3.5.dp.toPx()
                val arcW = 13.dp.toPx()
                val arcH = 7.dp.toPx()

                val leftArc = Path().apply {
                    moveTo(leftEyeX - arcW / 2f, eyeCenterY + 2.dp.toPx())
                    quadraticTo(
                        leftEyeX, eyeCenterY - arcH,
                        leftEyeX + arcW / 2f, eyeCenterY + 2.dp.toPx()
                    )
                }
                drawPath(
                    path = leftArc,
                    color = Color(0xFF1E1B18),
                    style = Stroke(width = strokeW, cap = StrokeCap.Round)
                )

                val rightArc = Path().apply {
                    moveTo(rightEyeX - arcW / 2f, eyeCenterY + 2.dp.toPx())
                    quadraticTo(
                        rightEyeX, eyeCenterY - arcH,
                        rightEyeX + arcW / 2f, eyeCenterY + 2.dp.toPx()
                    )
                }
                drawPath(
                    path = rightArc,
                    color = Color(0xFF1E1B18),
                    style = Stroke(width = strokeW, cap = StrokeCap.Round)
                )
            } else {
                // Big dark cartoon eyes with dual sparkling highlights
                listOf(leftEyeX, rightEyeX).forEach { eyeX ->
                    // Base dark pupil
                    drawCircle(
                        color = Color(0xFF1A1A1A),
                        radius = eyeRadius,
                        center = Offset(eyeX, eyeCenterY)
                    )
                    // Primary sparkle highlight (Top-left, large)
                    drawCircle(
                        color = Color.White,
                        radius = eyeRadius * 0.42f,
                        center = Offset(eyeX - eyeRadius * 0.20f, eyeCenterY - eyeRadius * 0.26f)
                    )
                    // Secondary sparkle highlight (Bottom-right, small)
                    drawCircle(
                        color = Color.White,
                        radius = eyeRadius * 0.22f,
                        center = Offset(eyeX + eyeRadius * 0.28f, eyeCenterY + eyeRadius * 0.32f)
                    )
                }
            }

            // 3. Cheerful Cute Open Mouth with Tongue
            val mouthCenterX = w * 0.50f
            val mouthCenterY = eyeCenterY + 9.5.dp.toPx()
            val mouthW = 14.dp.toPx()
            val mouthH = 8.5.dp.toPx()

            // Dark mouth cavity
            val mouthPath = Path().apply {
                moveTo(mouthCenterX - mouthW / 2f, mouthCenterY - mouthH * 0.15f)
                quadraticTo(
                    mouthCenterX, mouthCenterY - mouthH * 0.05f,
                    mouthCenterX + mouthW / 2f, mouthCenterY - mouthH * 0.15f
                )
                quadraticTo(
                    mouthCenterX, mouthCenterY + mouthH,
                    mouthCenterX - mouthW / 2f, mouthCenterY - mouthH * 0.15f
                )
                close()
            }
            drawPath(path = mouthPath, color = Color(0xFF221115))

            // Sweet pink tongue
            val tonguePath = Path().apply {
                val tongueStartX = mouthCenterX - mouthW * 0.32f
                val tongueEndX = mouthCenterX + mouthW * 0.32f
                val tongueStartY = mouthCenterY + mouthH * 0.32f
                moveTo(tongueStartX, tongueStartY)
                quadraticTo(
                    mouthCenterX, mouthCenterY + mouthH * 0.15f,
                    tongueEndX, tongueStartY
                )
                quadraticTo(
                    mouthCenterX, mouthCenterY + mouthH * 0.95f,
                    tongueStartX, tongueStartY
                )
                close()
            }
            drawPath(path = tonguePath, color = Color(0xFFFF6384))
        }
    }
}

/**
 * Rotating Sunburst Rays (Halo of Glory) in celebration style.
 */
@Composable
fun CelebrationSunburstRays(
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "RaysTransition")
    val rotationAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 35000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "SunburstRotation"
    )

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .rotate(rotationAngle)
    ) {
        val center = Offset(size.width / 2f, size.height / 2f)
        val rayCount = 12
        val maxRadius = kotlin.math.max(size.width, size.height) * 1.2f
        val rayAngle = 360f / rayCount

        for (i in 0 until rayCount) {
            val angleStart = (i * rayAngle) * (kotlin.math.PI / 180f).toFloat()
            val angleEnd = ((i * rayAngle) + (rayAngle * 0.45f)) * (kotlin.math.PI / 180f).toFloat()

            val p1 = center
            val p2 = Offset(
                center.x + maxRadius * kotlin.math.cos(angleStart),
                center.y + maxRadius * kotlin.math.sin(angleStart)
            )
            val p3 = Offset(
                center.x + maxRadius * kotlin.math.cos(angleEnd),
                center.y + maxRadius * kotlin.math.sin(angleEnd)
            )

            val path = Path().apply {
                moveTo(p1.x, p1.y)
                lineTo(p2.x, p2.y)
                lineTo(p3.x, p3.y)
                close()
            }

            drawPath(
                path = path,
                color = accentColor.copy(alpha = 0.08f)
            )
        }
    }
}
