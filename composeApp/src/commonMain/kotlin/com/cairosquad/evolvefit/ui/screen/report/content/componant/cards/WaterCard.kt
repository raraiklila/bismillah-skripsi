package com.cairosquad.evolvefit.ui.screen.report.content.componant.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import com.cairosquad.evolvefit.design_system.theme.Theme
import com.cairosquad.evolvefit.ui.screen.report.content.componant.CardHeaderSection
import com.cairosquad.evolvefit.ui.screen.report.content.componant.animatedMeter.RisingWaveAnimation
import evolvefit.composeapp.generated.resources.Res
import evolvefit.composeapp.generated.resources.ic_water_drop
import evolvefit.composeapp.generated.resources.liters
import evolvefit.composeapp.generated.resources.water
import evolvefit.composeapp.generated.resources.water_drops
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.cairosquad.evolvefit.design_system.theme.AppMotion
import com.cairosquad.evolvefit.design_system.theme.bouncyClickable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun WaterCard(
    waterConsumed: Float,
    isAnimationStarted: Boolean,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    var isResetting by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val effectiveAnimationStarted = isAnimationStarted && !isPressed && !isResetting

    val targetFillPercent = if (waterConsumed > 0f) {
        if (waterConsumed > 5f) {
            (waterConsumed / 18f).coerceIn(0.25f, 0.85f)
        } else {
            (waterConsumed / 3.5f).coerceIn(0.25f, 0.85f)
        }
    } else {
        0.5f
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .bouncyClickable(
                interactionSource = interactionSource,
                pressedScale = 0.95f,
                springSpec = AppMotion.SpringTokens.defaultSpatial(),
                onClick = {
                    scope.launch {
                        isResetting = true
                        delay(180)
                        isResetting = false
                    }
                }
            )
            .clip(Theme.shapes.large)
            .background(Theme.color.surfaces.surfaceContainer)
            .padding(horizontal = 12.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CardHeaderSection(
            painter = painterResource(Res.drawable.ic_water_drop),
            title = stringResource(Res.string.water),
            tint = Theme.color.system.info
        )
        Box(
            modifier = Modifier
                .padding(top = 16.dp)
                .width(134.dp)
                .weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .border(
                        width = 5.dp,
                        shape = CircleShape,
                        color = Theme.color.system.info
                    )
            ) {
                Image(
                    modifier = Modifier.padding(8.dp),
                    painter = painterResource(Res.drawable.water_drops),
                    contentDescription = stringResource(Res.string.water_drops)
                )
                RisingWaveAnimation(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .scale(scaleX = -1f, scaleY = 1f),
                    fillPercent = targetFillPercent,
                    isAnimationStarted = effectiveAnimationStarted
                )
            }
            Row(
                modifier = Modifier.align(Alignment.Center)
                    .offset(y = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = waterConsumed.toString(),
                    color = Theme.color.surfaces.textColor,
                    style = Theme.textStyle.display.largeBold24.copy(fontWeight = androidx.compose.ui.text.font.FontWeight.ExtraBold)
                )
                Text(
                    text = "/${stringResource(Res.string.liters)}",
                    color = Theme.color.surfaces.textColor,
                    style = Theme.textStyle.label.smallRegular14
                )
            }
        }
    }
}

@Preview
@Composable
private fun WaterCardPreview() {
    WaterCard(
        modifier = Modifier
            .width(160.dp)
            .height(214.dp),
        waterConsumed = 6f,
        isAnimationStarted = true
    )
}

@Preview
@Composable
private fun WaterCardLongPreview() {
    WaterCard(
        modifier = Modifier
            .width(160.dp)
            .height(260.dp),
        waterConsumed = 8f,
        isAnimationStarted = true
    )
}