package com.cairosquad.evolvefit.design_system.component.appbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.cairosquad.evolvefit.design_system.theme.AppTheme
import com.cairosquad.evolvefit.design_system.theme.Theme
import com.cairosquad.evolvefit.design_system.theme.bouncyClickable
import evolvefit.composeapp.generated.resources.Res
import evolvefit.composeapp.generated.resources.ic_profile
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun UserGreetingBar(
    userName: String,
    greetingMessage: String,
    contentColor: Color = Theme.color.surfaces.onSurface,
    modifier: Modifier = Modifier,
    userProfile: DrawableResource = Res.drawable.ic_profile,
    onProfileClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Theme.spacing.space200),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Theme.spacing.space150)
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(Theme.shapes.full)
                .background(Theme.color.surfaces.surfaceContainer)
                .then(if (onProfileClick != null) Modifier.bouncyClickable(onClick = onProfileClick) else Modifier),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(resource = userProfile),
                contentDescription = "User Profile",
                modifier = Modifier.size(44.dp),
            )
        }

        Column {
            Text(
                text = "Hello $userName",
                color = Theme.color.surfaces.onSurfaceVariant,
                style = Theme.textStyle.label.mediumMedium16
            )
            Text(
                text = greetingMessage,
                color = contentColor,
                style = Theme.textStyle.title.mediumMedium16
            )
        }
    }
}
