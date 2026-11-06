package com.cairosquad.evolvefit.design_system.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import kotlin.math.sin
import kotlin.random.Random

private data class ConfettiParticle(
    val initialXRatio: Float,
    val speed: Float,
    val size: Float,
    val color: Color,
    val isRibbon: Boolean,
    val swingFrequency: Float,
    val initialRotation: Float,
    val rotationSpeed: Float,
    val delayProgress: Float,
)

/**
 * Material 3 Expressive Celebratory Confetti Particle System.
 * Dynamically renders fluttering colorful ribbons and twinkling stars across the screen.
 */
@Composable
fun CelebrationParticles(
    modifier: Modifier = Modifier,
    particleCount: Int = 45,
    colors: List<Color> = listOf(
        Color(0xFF86D01D), // Brand Neon Green
        Color(0xFFF5D02D), // Gold
        Color(0xFF4E95FF), // Sky Blue
        Color(0xFFE65858), // Vibrant Coral
        Color(0xFFBEEBD9), // Mint / Tertiary
        Color(0xFFFFFFFF), // Pure White Star
    )
) {
    val particles = remember {
        val rnd = Random(42)
        List(particleCount) {
            ConfettiParticle(
                initialXRatio = rnd.nextFloat(),
                speed = 0.5f + rnd.nextFloat() * 0.7f,
                size = 6f + rnd.nextFloat() * 10f,
                color = colors[rnd.nextInt(colors.size)],
                isRibbon = rnd.nextBoolean(),
                swingFrequency = 2f + rnd.nextFloat() * 4f,
                initialRotation = rnd.nextFloat() * 360f,
                rotationSpeed = (rnd.nextFloat() - 0.5f) * 720f,
                delayProgress = rnd.nextFloat()
            )
        }
    }

    val infiniteTransition = rememberInfiniteTransition(label = "ConfettiLoop")
    val animationProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "confettiProgress"
    )

    Canvas(modifier = modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height

        particles.forEach { p ->
            val adjustedProgress = (animationProgress + p.delayProgress) % 1f
            val y = adjustedProgress * (height + 60f) - 30f
            val swing = sin(adjustedProgress * p.swingFrequency * 3.14159f) * 35f
            val x = (p.initialXRatio * width + swing).coerceIn(0f, width)
            val currentRotation = p.initialRotation + adjustedProgress * p.rotationSpeed

            rotate(degrees = currentRotation, pivot = Offset(x, y)) {
                if (p.isRibbon) {
                    drawRect(
                        color = p.color,
                        topLeft = Offset(x - p.size / 2, y - p.size),
                        size = Size(p.size * 0.7f, p.size * 1.8f)
                    )
                } else {
                    drawCircle(
                        color = p.color,
                        radius = p.size * 0.45f,
                        center = Offset(x, y)
                    )
                }
            }
        }
    }
}
