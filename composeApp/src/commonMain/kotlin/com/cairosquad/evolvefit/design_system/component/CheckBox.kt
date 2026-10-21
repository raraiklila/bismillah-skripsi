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
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.unit.dp
import com.cairosquad.evolvefit.design_system.theme.AppTheme
import com.cairosquad.evolvefit.design_system.theme.Theme
import evolvefit.composeapp.generated.resources.Res
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
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(color = Theme.color.surfaces.surfaceContainer)
            .fillMaxWidth()
            .clickable { onCheckedChange(!isChecked) }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        if (icon != null) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(icon),
                contentDescription = "",
                tint = Theme.color.surfaces.onSurfaceContainer
            )
        }
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(color = Theme.color.surfaces.surfaceContainer)
                .weight(1f)
        ) {
            Text(
                text = text,
                style = Theme.textStyle.body.mediumMedium14,
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
            CheckboxStyle.Tick -> {
                CustomTick(isChecked = isChecked, onCheckedChange = onCheckedChange)
            }

            CheckboxStyle.Switch -> {
                CustomSwitch(
                    isChecked = isChecked,
                )
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
                text = "This is a Switch Button",
                description = "this is a Description",
                isChecked = !isChecked,
                onCheckedChange = { isChecked = !isChecked },
                style = CheckboxStyle.Switch
            )
            CheckboxItem(
                text = "This is a Tick Button",
                description = "this is a Description",
                isChecked = isChecked,
                onCheckedChange = { isChecked = !isChecked },
                style = CheckboxStyle.Tick
            )
            CheckboxItem(
                text = "This is a Tick Button",
                isChecked = !isChecked,
                onCheckedChange = { isChecked = !isChecked },
                style = CheckboxStyle.Tick
            )
        }
    }
}

enum class CheckboxStyle {
    Tick,
    Switch
}

@Composable
fun CustomTick(
    isChecked: Boolean,
    modifier: Modifier = Modifier,
    onCheckedChange: (Boolean) -> Unit = {}
) {
    // Single transition drives every property so check & uncheck stay in sync
    // and nothing (e.g. green tint) lingers on the previously selected item.
    val transition = updateTransition(targetState = isChecked, label = "TickTransition")
    val progress by transition.animateFloat(
        transitionSpec = { tween(durationMillis = 220) },
        label = "TickProgress"
    ) { checked -> if (checked) 1f else 0f }
    val borderColor by transition.animateColor(
        transitionSpec = { tween(durationMillis = 220) },
        label = "TickBorder"
    ) { checked ->
        if (checked) Theme.color.surfaces.outlineVariant.copy(alpha = 0f)
        else Theme.color.surfaces.outlineVariant
    }

    Box(
        modifier = modifier
            .size(24.dp)
            .border(width = 1.dp, color = borderColor, shape = CircleShape),
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
                        val scale = 0.6f + 0.4f * progress
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
        label = "ThumbOffset"
    ) { isChecked ->
        if (isChecked) trackWidth - thumbSize - padding else padding
    }
    val trackColor by transition.animateColor(
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