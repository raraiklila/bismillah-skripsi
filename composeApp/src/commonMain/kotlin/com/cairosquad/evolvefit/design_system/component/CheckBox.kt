package com.cairosquad.evolvefit.design_system.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import evolvefit.composeapp.generated.resources.Res
import evolvefit.composeapp.generated.resources.ic_check_mark
import evolvefit.composeapp.generated.resources.ic_green_check_circle
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun CheckboxItem(
    text: String,
    isChecked: Boolean,
    onCheckedChange: (isUnChecked: Boolean) -> Unit,
    modifier: Modifier = Modifier,
    description: String? = null,
    titleColor: Color = Theme.color.surfaces.onSurface,
    style: CheckboxStyle = CheckboxStyle.Tick,
    icon: DrawableResource? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    // Consistent field background: matches the fields above (surfaceContainer), never filled with green
    val targetBackgroundColor = when {
        isPressed -> Theme.color.surfaces.surfaceContainerHighest
        else -> Theme.color.surfaces.surfaceContainer
    }

    val animatedBackgroundColor by animateColorAsState(
        targetValue = targetBackgroundColor,
        animationSpec = tween(durationMillis = 200),
        label = "M3CheckboxCardBackground"
    )

    // Outline: When checked -> prominent primary outline! When unselected -> outlineVariant matching input fields
    val targetBorderColor = when {
        isChecked -> Theme.color.brand.primary
        isPressed -> Theme.color.surfaces.outline
        else -> Theme.color.surfaces.outlineVariant
    }

    val animatedBorderColor by animateColorAsState(
        targetValue = targetBorderColor,
        animationSpec = AppMotion.SpringTokens.defaultEffects(),
        label = "M3CheckboxCardBorder"
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(Theme.shapes.inputShape)
            .background(color = animatedBackgroundColor, shape = Theme.shapes.inputShape)
            .border(
                width = 1.dp,
                color = animatedBorderColor,
                shape = Theme.shapes.inputShape
            )
            .bouncyClickable(
                interactionSource = interactionSource,
                pressedScale = 0.98f,
                springSpec = AppMotion.SpringTokens.defaultSpatial(),
                onClick = { onCheckedChange(!isChecked) }
            )
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (icon != null) {
            val iconTint = if (isChecked) Theme.color.brand.primary else Theme.color.surfaces.onSurfaceContainer
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(icon),
                contentDescription = "",
                tint = iconTint
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = text,
                style = if (isChecked) Theme.textStyle.body.mediumMedium14.copy(fontWeight = FontWeight.SemiBold) else Theme.textStyle.body.mediumMedium14,
                color = titleColor
            )
            description?.let { desc ->
                Text(
                    text = desc,
                    style = Theme.textStyle.label.smallRegular12,
                    color = Theme.color.surfaces.onSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
        when (style) {
            CheckboxStyle.Checkbox -> {
                M3ExpressiveCheckbox(isChecked = isChecked)
            }
            CheckboxStyle.RadioButton -> {
                M3ExpressiveRadioButton(isChecked = isChecked)
            }
            CheckboxStyle.Tick -> {
                CustomTick(isChecked = isChecked)
            }
            CheckboxStyle.Switch -> {
                CustomSwitch(isChecked = isChecked)
            }
        }
    }
}

@Preview
@Composable
private fun CheckboxItemPreview() {
    var isChecked by remember { mutableStateOf(false) }
    AppTheme(
        isDarkTheme = true
    ) {
        Column(
            modifier = Modifier
                .background(Theme.color.surfaces.surface)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CheckboxItem(
                text = "This is a Switch Button",
                isChecked = isChecked,
                onCheckedChange = { isChecked = !isChecked },
                style = CheckboxStyle.Switch
            )
            CheckboxItem(
                text = "This is a Checkbox",
                description = "this is a Description",
                isChecked = !isChecked,
                onCheckedChange = { isChecked = !isChecked },
                style = CheckboxStyle.Checkbox
            )
            CheckboxItem(
                text = "This is a Radio Button",
                description = "this is a Description",
                isChecked = isChecked,
                onCheckedChange = { isChecked = !isChecked },
                style = CheckboxStyle.RadioButton
            )
        }
    }
}

enum class CheckboxStyle {
    Checkbox,
    RadioButton,
    Tick,
    Switch
}

/**
 * Material 3 Expressive Checkbox (Square container with rounded corners and animated checkmark).
 * Designed for multiple-choice selections where selected state is prominent.
 */
@Composable
fun M3ExpressiveCheckbox(
    isChecked: Boolean,
    modifier: Modifier = Modifier,
    onCheckedChange: ((Boolean) -> Unit)? = null
) {
    val transition = updateTransition(targetState = isChecked, label = "M3CheckboxTransition")
    val progress by transition.animateFloat(
        transitionSpec = { AppMotion.SpringTokens.fastSpatial() },
        label = "M3CheckboxProgress"
    ) { checked -> if (checked) 1f else 0f }

    val backgroundColor by transition.animateColor(
        transitionSpec = { AppMotion.SpringTokens.fastEffects() },
        label = "M3CheckboxBg"
    ) { checked ->
        if (checked) Theme.color.brand.primary else Color.Transparent
    }

    val borderColor by transition.animateColor(
        transitionSpec = { AppMotion.SpringTokens.fastEffects() },
        label = "M3CheckboxBorder"
    ) { checked ->
        if (checked) Theme.color.brand.primary else Theme.color.surfaces.outlineVariant
    }

    val shape = RoundedCornerShape(6.dp)

    Box(
        modifier = modifier
            .size(22.dp)
            .clip(shape)
            .background(backgroundColor)
            .border(width = 1.5.dp, color = borderColor, shape = shape)
            .then(
                if (onCheckedChange != null) Modifier.bouncyClickable { onCheckedChange(!isChecked) }
                else Modifier
            ),
        contentAlignment = Alignment.Center
    ) {
        if (progress > 0f) {
            Icon(
                painter = painterResource(Res.drawable.ic_check_mark),
                contentDescription = null,
                tint = Theme.color.brand.onPrimary,
                modifier = Modifier
                    .size(13.dp)
                    .graphicsLayer {
                        alpha = progress
                        val scale = 0.6f + 0.4f * progress
                        scaleX = scale
                        scaleY = scale
                    }
            )
        }
    }
}

/**
 * Material 3 Expressive Radio Button (Circular container with animated checkmark).
 * Designed for single-choice / mutually exclusive selections: centang dibungkus lingkaran (Gambar 1).
 */
@Composable
fun M3ExpressiveRadioButton(
    isChecked: Boolean,
    modifier: Modifier = Modifier,
    onCheckedChange: ((Boolean) -> Unit)? = null
) {
    CustomTick(
        isChecked = isChecked,
        modifier = modifier,
        onCheckedChange = onCheckedChange
    )
}

@Composable
fun CustomTick(
    isChecked: Boolean,
    modifier: Modifier = Modifier,
    onCheckedChange: ((Boolean) -> Unit)? = null
) {
    val transition = updateTransition(targetState = isChecked, label = "TickTransition")
    val progress by transition.animateFloat(
        transitionSpec = { AppMotion.SpringTokens.fastSpatial() },
        label = "TickProgress"
    ) { checked -> if (checked) 1f else 0f }
    val borderColor by transition.animateColor(
        transitionSpec = { AppMotion.SpringTokens.fastEffects() },
        label = "TickBorder"
    ) { checked ->
        if (checked) Theme.color.surfaces.outlineVariant.copy(alpha = 0f)
        else Theme.color.surfaces.outlineVariant
    }

    Box(
        modifier = modifier
            .size(24.dp)
            .border(width = 1.2.dp, color = borderColor, shape = CircleShape)
            .then(
                if (onCheckedChange != null) Modifier.bouncyClickable { onCheckedChange(!isChecked) }
                else Modifier
            ),
        contentAlignment = Alignment.Center
    ) {
        if (progress > 0f) {
            Icon(
                painter = painterResource(Res.drawable.ic_green_check_circle),
                contentDescription = null,
                tint = Theme.color.brand.primary,
                modifier = Modifier
                    .size(24.dp)
                    .graphicsLayer {
                        alpha = progress
                        val scale = 0.5f + 0.5f * progress
                        scaleX = scale
                        scaleY = scale
                    }
            )
        }
    }
}

@Composable
fun CustomSwitch(
    isChecked: Boolean,
    modifier: Modifier = Modifier,
) {
    val trackWidth = 48.dp
    val trackHeight = 28.dp
    val thumbSize = 20.dp
    val padding = 4.dp
    val transition = updateTransition(targetState = isChecked, label = "SwitchTransition")
    val thumbOffsetX by transition.animateDp(
        transitionSpec = { AppMotion.SpringTokens.fastSpatial() },
        label = "ThumbOffset"
    ) { isChecked ->
        if (isChecked) trackWidth - thumbSize - padding else padding
    }
    val trackColor by transition.animateColor(
        transitionSpec = { AppMotion.SpringTokens.fastEffects() },
        label = "TrackColor"
    ) { isChecked ->
        if (isChecked) Theme.color.brand.primary else Theme.color.surfaces.outlineVariant
    }
    val thumbColor = Theme.color.surfaces.surface
    Box(
        modifier = modifier
            .size(trackWidth, trackHeight)
            .clip(CircleShape)
            .background(color = trackColor),
        contentAlignment = Alignment.CenterStart
    ) {
        Box(
            modifier = Modifier
                .offset(x = thumbOffsetX)
                .size(thumbSize)
                .background(color = thumbColor, shape = CircleShape)
        )
    }
}