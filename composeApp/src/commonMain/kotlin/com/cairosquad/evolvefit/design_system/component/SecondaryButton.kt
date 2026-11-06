package com.cairosquad.evolvefit.design_system.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.cairosquad.evolvefit.design_system.theme.AppTheme
import com.cairosquad.evolvefit.design_system.theme.Theme
import com.cairosquad.evolvefit.design_system.theme.bouncyClickable
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    enabledContainerColor: Color = Theme.color.surfaces.surfaceContainerHighest,
    disabledContainerColor: Color = Theme.color.surfaces.surfaceContainerHighest.copy(alpha = 0.38f),
    enabledTextColor: Color = Theme.color.surfaces.textColor,
    disabledTextColor: Color = Theme.color.surfaces.outline,
    textStyle: TextStyle = Theme.textStyle.body.mediumMedium14,
) {
    val containerColor by animateColorAsState(
        targetValue = if (isEnabled) enabledContainerColor else disabledContainerColor,
        animationSpec = com.cairosquad.evolvefit.design_system.theme.AppMotion.bouncySpring(),
        label = "M3SecondaryButtonContainer"
    )

    val textColor by animateColorAsState(
        targetValue = if (isEnabled) enabledTextColor else disabledTextColor,
        animationSpec = com.cairosquad.evolvefit.design_system.theme.AppMotion.bouncySpring(),
        label = "M3SecondaryButtonText"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(Theme.shapes.buttonShape)
            .background(containerColor)
            .bouncyClickable(enabled = isEnabled, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp),
            text = text,
            color = textColor,
            style = textStyle,
        )
    }
}

@Preview
@Composable
private fun EnabledButtonPreview() {
    AppTheme(isDarkTheme = true) {
        SecondaryButton(
            text = "Get Started", onClick = {}
        )
    }
}

@Preview
@Composable
private fun DisabledButtonPreview() {
    AppTheme(isDarkTheme = true) {
        SecondaryButton(
            text = "Get Started",
            isEnabled = false, onClick = {}
        )
    }
}