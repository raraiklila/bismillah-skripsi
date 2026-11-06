package com.cairosquad.evolvefit.design_system.component.appbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.cairosquad.evolvefit.design_system.theme.Theme
import com.cairosquad.evolvefit.design_system.theme.bouncyClickable

@Composable
fun ActionIconButton(
    icon: Painter,
    contentDescription: String?,
    onClick: () -> Unit,
    tint: Color = Theme.color.surfaces.onSurface,
    containerColor: Color = Color.Transparent,
    shape: Shape = Theme.shapes.full,
    modifier: Modifier = Modifier
) {
    if (containerColor != Color.Transparent) {
        Box(
            modifier = modifier
                .size(40.dp)
                .clip(shape)
                .background(containerColor)
                .bouncyClickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = icon,
                contentDescription = contentDescription,
                tint = tint,
                modifier = Modifier.size(20.dp)
            )
        }
    } else {
        IconButton(
            onClick = onClick,
            modifier = modifier
        ) {
            Icon(
                painter = icon,
                contentDescription = contentDescription,
                tint = tint,
            )
        }
    }
}

