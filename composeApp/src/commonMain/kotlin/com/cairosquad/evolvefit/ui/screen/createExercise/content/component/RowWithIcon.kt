package com.cairosquad.evolvefit.ui.screen.createExercise.content.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.cairosquad.evolvefit.design_system.theme.AppMotion
import com.cairosquad.evolvefit.design_system.theme.Theme
import com.cairosquad.evolvefit.design_system.theme.bouncyClickable
import evolvefit.composeapp.generated.resources.Res
import evolvefit.composeapp.generated.resources.arrow
import evolvefit.composeapp.generated.resources.ic_arrow_down
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun RowWithIcon(
    modifier: Modifier = Modifier,
    text: String,
    isIconClicked: Boolean = false,
    textColor: Color = Theme.color.surfaces.onSurfaceVariant,
    iconTint: Color = Theme.color.surfaces.onSurfaceVariant,
    background: Color = Theme.color.surfaces.surfaceContainer,
    borderColor: Color? = null,
    onIconClicked: () -> Unit = {}
) {
    val rotation by animateFloatAsState(
        targetValue = if (isIconClicked) 180f else 0f,
        animationSpec = AppMotion.SpringTokens.fastSpatial(),
        label = "M3RowArrowRotation"
    )

    val targetBorder = borderColor ?: if (isIconClicked) {
        Theme.color.brand.primary
    } else {
        Theme.color.surfaces.outlineVariant
    }

    val animatedBorderColor by animateColorAsState(
        targetValue = targetBorder,
        animationSpec = AppMotion.SpringTokens.fastEffects(),
        label = "M3RowBorderColor"
    )

    Row(
        modifier = modifier
            .clip(Theme.shapes.inputShape)
            .background(
                color = background,
                shape = Theme.shapes.inputShape
            )
            .border(
                width = 1.dp,
                color = animatedBorderColor,
                shape = Theme.shapes.inputShape
            )
            .bouncyClickable(onClick = onIconClicked)
            .padding(horizontal = 12.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = Theme.textStyle.label.smallRegular14,
            color = textColor
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            modifier = Modifier.graphicsLayer { rotationZ = rotation },
            painter = painterResource(Res.drawable.ic_arrow_down),
            contentDescription = stringResource(Res.string.arrow),
            tint = iconTint
        )
    }
}

@Preview
@Composable
private fun RowWithIconPreview() {
    RowWithIcon(
        text = "Choose focus area",
        isIconClicked = true
    )
}