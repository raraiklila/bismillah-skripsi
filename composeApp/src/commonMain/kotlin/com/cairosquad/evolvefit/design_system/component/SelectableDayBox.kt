package com.cairosquad.evolvefit.design_system.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cairosquad.evolvefit.design_system.theme.AppMotion
import com.cairosquad.evolvefit.design_system.theme.AppTheme
import com.cairosquad.evolvefit.design_system.theme.Theme
import com.cairosquad.evolvefit.design_system.theme.bouncyClickable
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SelectableDayBox(
    textDay: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val targetBackgroundColor = when {
        isSelected -> Theme.color.brand.primary
        isPressed -> Theme.color.surfaces.surfaceContainerHighest
        else -> Theme.color.surfaces.surfaceContainer
    }
    val targetTextColor = if (isSelected) Theme.color.brand.onPrimary else Theme.color.surfaces.onSurface

    val backgroundColor by animateColorAsState(
        targetValue = targetBackgroundColor,
        animationSpec = AppMotion.SpringTokens.defaultEffects(),
        label = "M3DayBackgroundColor"
    )
    val textColor by animateColorAsState(
        targetValue = targetTextColor,
        animationSpec = AppMotion.SpringTokens.defaultEffects(),
        label = "M3DayTextColor"
    )

    // True Material 3 Expressive Day Box:
    // - Unselected: dark surfaceContainer with white text (Image 2)
    // - Selected: solid primary green with onPrimary dark text (Image 2)
    // - Rounded: generous large rounded corners (Theme.shapes.large / 20.dp)
    // - Motion: tactile spring squash 0.94x and elastic rebound
    val targetBorderColor = when {
        isPressed -> Theme.color.surfaces.outlineVariant.copy(alpha = 0.5f)
        else -> Color.Transparent
    }
    val borderColor by animateColorAsState(
        targetValue = targetBorderColor,
        animationSpec = AppMotion.SpringTokens.defaultEffects(),
        label = "M3DayBorderColor"
    )

    Box(
        modifier = modifier
            .bouncyClickable(
                interactionSource = interactionSource,
                pressedScale = 0.94f,
                springSpec = AppMotion.SpringTokens.defaultSpatial(),
                onClick = onClick
            )
            .border(
                width = 1.dp,
                color = borderColor,
                shape = Theme.shapes.large
            )
            .clip(Theme.shapes.large)
            .background(
                color = backgroundColor,
                shape = Theme.shapes.large
            )
            .height(52.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = textDay,
            color = textColor,
            style = if (isSelected) Theme.textStyle.body.mediumMedium14.copy(fontWeight = FontWeight.Bold) else Theme.textStyle.body.mediumMedium14
        )
    }
}



@Preview()
@Composable
private fun SelectableBoxPreview() {
    AppTheme(isDarkTheme = true) {
        SelectableDayBox(
            textDay = "Wednesday",
            isSelected = true,
            onClick = {},
            modifier = Modifier.width(103.dp)
        )
    }
}

@Preview()
@Composable
private fun SelectableBoxPreview2() {
    AppTheme(isDarkTheme = true) {
        SelectableDayBox(
            textDay = "Sunday",
            isSelected = true,
            onClick = {},
            modifier = Modifier.width(103.dp)
        )
    }
}
