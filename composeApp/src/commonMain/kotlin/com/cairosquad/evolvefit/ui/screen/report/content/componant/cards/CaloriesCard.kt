package com.cairosquad.evolvefit.ui.screen.report.content.componant.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.cairosquad.evolvefit.design_system.theme.AppTheme
import com.cairosquad.evolvefit.design_system.theme.Theme
import com.cairosquad.evolvefit.ui.screen.report.content.componant.CardHeaderSection
import com.cairosquad.evolvefit.ui.screen.report.content.componant.animatedMeter.CaloriesMeter
import evolvefit.composeapp.generated.resources.Res
import evolvefit.composeapp.generated.resources.calories
import evolvefit.composeapp.generated.resources.ic_fire
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
fun CaloriesCard(
    expectedCalories: Int,
    takenCalories: Int,
    isAnimationStarted: Boolean,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    var isResetting by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val effectiveAnimationStarted = isAnimationStarted && !isPressed && !isResetting

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
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CardHeaderSection(
            painter = painterResource(Res.drawable.ic_fire),
            tint = Theme.color.system.success,
            containerColor = Theme.color.system.success.copy(alpha = 0.15f),
            title = stringResource(Res.string.calories)
        )
        CaloriesMeter(
            modifier = Modifier.weight(1f),
            expectedCalories = expectedCalories,
            takenCalories = takenCalories,
            isAnimationStarted = effectiveAnimationStarted
        )
    }
}

@Preview
@Composable
private fun CaloriesCardPreview() {
    AppTheme(isDarkTheme = true) {
        CaloriesCard(
            modifier = Modifier
                .width(200.dp).height(214.dp),
            expectedCalories = 3600,
            takenCalories = 2000,
            isAnimationStarted = true
        )
    }
}

@Preview
@Composable
private fun CaloriesCardLongPreview() {
    AppTheme(isDarkTheme = true) {
        CaloriesCard(
            modifier = Modifier
                .width(180.dp).height(262.dp),
            expectedCalories = 3600,
            takenCalories = 2000,
            isAnimationStarted = false
        )
    }
}