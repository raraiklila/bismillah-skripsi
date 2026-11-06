package com.cairosquad.evolvefit.design_system.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import com.cairosquad.evolvefit.design_system.theme.AppMotion
import com.cairosquad.evolvefit.design_system.theme.AppTheme
import com.cairosquad.evolvefit.design_system.theme.Theme
import com.cairosquad.evolvefit.design_system.theme.bouncyClickable
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    enabledContainerColor: Color = Theme.color.brand.primary,
    disabledContainerColor: Color = Theme.color.surfaces.outlineVariant,
    enabledTextColor: Color = Theme.color.brand.onPrimary,
    disabledTextColor: Color = Theme.color.surfaces.outline,
    textStyle: TextStyle = Theme.textStyle.body.mediumMedium14,
) {
    val containerColor by animateColorAsState(
        targetValue = if (isEnabled) enabledContainerColor else disabledContainerColor,
        animationSpec = AppMotion.SpringTokens.defaultEffects(),
        label = "M3ButtonContainerColor"
    )

    val textColor by animateColorAsState(
        targetValue = if (isEnabled) enabledTextColor else disabledTextColor,
        animationSpec = AppMotion.SpringTokens.defaultEffects(),
        label = "M3ButtonTextColor"
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(Theme.shapes.buttonShape)
            .background(containerColor)
            .bouncyClickable(
                enabled = isEnabled && !isLoading,
                pressedScale = 0.96f,
                springSpec = AppMotion.SpringTokens.defaultSpatial(),
                onClick = onClick
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        AnimatedContent(
            targetState = text,
            transitionSpec = {
                (fadeIn(animationSpec = tween(220, easing = AppMotion.EmphasizedDecelerate)) +
                        scaleIn(initialScale = 0.92f, animationSpec = tween(220)))
                    .togetherWith(
                        fadeOut(animationSpec = tween(150, easing = AppMotion.EmphasizedAccelerate)) +
                                scaleOut(targetScale = 0.92f, animationSpec = tween(150))
                    )
            },
            label = "M3ButtonTextTransition"
        ) { targetText ->
            Text(
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp),
                text = targetText,
                color = textColor,
                style = textStyle,
            )
        }
        Crossfade(targetState = isLoading, label = "M3ButtonLoading") { loading ->
            if (loading) {
                Spacer(modifier = Modifier.padding(start = 8.dp))
                AnimatedLoadingIndicator(
                    size = 20.dp,
                    iconTint = textColor
                )
            }
        }
    }
}

@Preview
@Composable
private fun EnabledButtonPreview() {
    AppTheme(isDarkTheme = true) {
        PrimaryButton(
            text = "Get Started", onClick = {}
        )
    }
}

@Preview
@Composable
private fun DisabledButtonPreview() {
    AppTheme(isDarkTheme = true) {
        PrimaryButton(
            text = "Get Started",
            isEnabled = false, onClick = {}
        )
    }
}