package com.cairosquad.evolvefit.ui.screen.report.content.componant

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.cairosquad.evolvefit.design_system.theme.AppMotion
import com.cairosquad.evolvefit.design_system.theme.Theme
import com.cairosquad.evolvefit.design_system.theme.bouncyClickable
import evolvefit.composeapp.generated.resources.Res
import evolvefit.composeapp.generated.resources.arrow_down
import evolvefit.composeapp.generated.resources.ic_arrow_down
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import androidx.compose.foundation.border

@Composable
fun WeekFilter(
    currentWeek: String,
    onMenuClick: () -> Unit,
    isDropDownMenuDown: Boolean,
    modifier: Modifier = Modifier
) {
    val rotation by animateFloatAsState(
        targetValue = if (isDropDownMenuDown) 180f else 0f,
        animationSpec = AppMotion.SpringTokens.fastSpatial(),
        label = "M3WeekFilterArrow"
    )

    Row(
        modifier = modifier
            .clip(CircleShape)
            .border(
                width = 1.dp,
                color = Theme.color.surfaces.outline.copy(alpha = 0.20f),
                shape = CircleShape
            )
            .background(Theme.color.surfaces.surfaceContainer)
            .bouncyClickable(onClick = onMenuClick)
            .padding(horizontal = 14.dp, vertical = 7.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = currentWeek,
            style = Theme.textStyle.body.mediumMedium12,
            color = Theme.color.surfaces.onSurface
        )
        Image(
            modifier = Modifier
                .size(14.dp)
                .graphicsLayer { rotationZ = rotation },
            painter = painterResource(Res.drawable.arrow_down),
            contentDescription = stringResource(Res.string.ic_arrow_down),
            colorFilter = ColorFilter.tint(Theme.color.surfaces.onSurface)
        )
    }
}

@Preview
@Composable
private fun WeekFilterPreview() {
    WeekFilter("This week", {}, false)
}