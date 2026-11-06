package com.cairosquad.evolvefit.design_system.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.cairosquad.evolvefit.design_system.theme.AppMotion
import com.cairosquad.evolvefit.design_system.theme.AppTheme
import com.cairosquad.evolvefit.design_system.theme.Theme
import com.cairosquad.evolvefit.design_system.theme.bouncyClickable
import evolvefit.composeapp.generated.resources.Res
import evolvefit.composeapp.generated.resources.ic_cancle
import evolvefit.composeapp.generated.resources.ic_search
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Material 3 Expressive SearchBar.
 * Features a full pill shape (CircleShape), surfaceContainerHighest background,
 * smooth focused border transition with theme primary, animated search icon tint,
 * and a tactile clear button with spring physics micro-interactions.
 */
@Composable
fun ExpressiveSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    onClearQuery: (() -> Unit)? = null,
    onSearch: (() -> Unit)? = null,
    enabled: Boolean = true,
) {
    var isFocused by remember { mutableStateOf(false) }

    val borderColor by animateColorAsState(
        targetValue = if (isFocused) Theme.color.brand.primary else Theme.color.surfaces.outlineVariant.copy(alpha = 0.25f),
        animationSpec = tween(durationMillis = 200, easing = AppMotion.EmphasizedEasing)
    )

    val iconTint by animateColorAsState(
        targetValue = if (isFocused) Theme.color.brand.primary else Theme.color.surfaces.onSurfaceVariant,
        animationSpec = tween(durationMillis = 200, easing = AppMotion.EmphasizedEasing)
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .clip(CircleShape)
            .background(Theme.color.surfaces.surfaceContainerHighest)
            .border(
                width = if (isFocused) 1.5.dp else 1.dp,
                color = borderColor,
                shape = CircleShape
            )
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(Res.drawable.ic_search),
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(22.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 4.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            if (query.isEmpty()) {
                Text(
                    text = placeholder,
                    style = Theme.textStyle.body.bodyMedium,
                    color = Theme.color.surfaces.onSurfaceVariant
                )
            }

            BasicTextField(
                value = query,
                onValueChange = onQueryChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { isFocused = it.isFocused },
                textStyle = Theme.textStyle.body.bodyMedium.copy(
                    color = Theme.color.surfaces.onSurface
                ),
                cursorBrush = SolidColor(Theme.color.brand.primary),
                singleLine = true,
                enabled = enabled,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { onSearch?.invoke() })
            )
        }

        AnimatedVisibility(
            visible = query.isNotEmpty(),
            enter = fadeIn(tween(150)) + scaleIn(tween(150)),
            exit = fadeOut(tween(150)) + scaleOut(tween(150))
        ) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(Theme.color.surfaces.outlineVariant.copy(alpha = 0.35f))
                    .bouncyClickable(
                        pressedScale = 0.85f,
                        onClick = {
                            onClearQuery?.invoke() ?: onQueryChange("")
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_cancle),
                    contentDescription = "Clear search",
                    tint = Theme.color.surfaces.onSurfaceVariant,
                    modifier = Modifier.size(14.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun ExpressiveSearchBarPreview() {
    AppTheme(isDarkTheme = true) {
        ExpressiveSearchBar(
            query = "Bench Press",
            onQueryChange = {},
            placeholder = "Search exercises..."
        )
    }
}
