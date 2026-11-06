package com.cairosquad.evolvefit.design_system.component.appbar

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.cairosquad.evolvefit.design_system.theme.AppTheme
import com.cairosquad.evolvefit.design_system.theme.Theme
import com.cairosquad.evolvefit.design_system.theme.bouncyClickable
import evolvefit.composeapp.generated.resources.Res
import evolvefit.composeapp.generated.resources.ic_back
import evolvefit.composeapp.generated.resources.ic_bookmark
import evolvefit.composeapp.generated.resources.ic_group
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Material 3 Expressive Flexible Small App Bar.
 * Focuses on describing the current page with 1-2 essential actions.
 * On scroll, applies a fill color (surfaceContainer) to separate from body content.
 */
@Composable
fun CustomAppBar(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    titleAlignment: TextAlign = TextAlign.Center,
    isScrolled: Boolean = false,
    contentColor: Color = Theme.color.surfaces.onSurface,
    containerColor: Color = if (isScrolled) Theme.color.surfaces.surfaceContainer else Color.Transparent,
    header: (@Composable (() -> Unit)) = { Box(modifier = Modifier) },
    tail: (@Composable RowScope.() -> Unit) = { Box(modifier = Modifier) }
) {
    val animatedContainerColor by animateColorAsState(
        targetValue = containerColor,
        animationSpec = tween(durationMillis = 200),
        label = "AppBarColorFill"
    )

    val density = LocalDensity.current
    var textStartPadding by remember { mutableStateOf(0.dp) }
    var textEndPadding by remember { mutableStateOf(0.dp) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(animatedContainerColor)
            .padding(vertical = Theme.spacing.space100, horizontal = Theme.spacing.space200)
    ) {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.CenterStart)
                .onGloballyPositioned {
                    textStartPadding = with(density) { it.size.width.toDp() }
                }
        ) {
            header()
        }

        Column(
            modifier = Modifier
                .align(if (titleAlignment == TextAlign.Center) Alignment.Center else Alignment.CenterStart)
                .padding(
                    start = if (titleAlignment == TextAlign.Center) maxOf(textStartPadding, textEndPadding) else textStartPadding + 8.dp,
                    end = if (titleAlignment == TextAlign.Center) maxOf(textStartPadding, textEndPadding) else textEndPadding + 8.dp
                ),
            horizontalAlignment = if (titleAlignment == TextAlign.Center) Alignment.CenterHorizontally else Alignment.Start
        ) {
            Text(
                text = title,
                color = contentColor,
                style = Theme.textStyle.title.largeBold16,
                textAlign = titleAlignment,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            if (!subtitle.isNullOrBlank()) {
                Text(
                    text = subtitle,
                    color = Theme.color.surfaces.onSurfaceVariant,
                    style = Theme.textStyle.body.mediumMedium12,
                    textAlign = titleAlignment,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Row(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.CenterEnd)
                .onGloballyPositioned {
                    textEndPadding = with(density) { it.size.width.toDp() }
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Theme.spacing.space100),
            content = tail
        )
    }
}

/**
 * Material 3 Expressive Medium Flexible App Bar.
 * Features reduced overall height, larger title text, and flexible action layout.
 */
@Composable
fun MediumFlexibleAppBar(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    isScrolled: Boolean = false,
    contentColor: Color = Theme.color.surfaces.onSurface,
    header: (@Composable () -> Unit) = { Box(modifier = Modifier) },
    tail: (@Composable RowScope.() -> Unit) = { Box(modifier = Modifier) }
) {
    val animatedContainerColor by animateColorAsState(
        targetValue = if (isScrolled) Theme.color.surfaces.surfaceContainer else Color.Transparent,
        animationSpec = tween(durationMillis = 200),
        label = "MediumAppBarColorFill"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(animatedContainerColor)
            .padding(horizontal = Theme.spacing.space200, vertical = Theme.spacing.space100)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box { header() }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Theme.spacing.space100),
                content = tail
            )
        }

        Spacer(modifier = Modifier.height(Theme.spacing.space150))

        Column(
            modifier = Modifier.padding(start = Theme.spacing.space100, bottom = Theme.spacing.space100)
        ) {
            Text(
                text = title,
                color = contentColor,
                style = Theme.textStyle.title.largeBold16,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            if (!subtitle.isNullOrBlank()) {
                Text(
                    text = subtitle,
                    color = Theme.color.surfaces.onSurfaceVariant,
                    style = Theme.textStyle.body.mediumMedium14,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

/**
 * Material 3 Expressive Large Flexible App Bar.
 */
@Composable
fun LargeFlexibleAppBar(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    isScrolled: Boolean = false,
    contentColor: Color = Theme.color.surfaces.onSurface,
    header: (@Composable () -> Unit) = { Box(modifier = Modifier) },
    tail: (@Composable RowScope.() -> Unit) = { Box(modifier = Modifier) }
) {
    val animatedContainerColor by animateColorAsState(
        targetValue = if (isScrolled) Theme.color.surfaces.surfaceContainer else Color.Transparent,
        animationSpec = tween(durationMillis = 200),
        label = "LargeAppBarColorFill"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(animatedContainerColor)
            .padding(horizontal = Theme.spacing.space200, vertical = Theme.spacing.space150)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box { header() }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Theme.spacing.space100),
                content = tail
            )
        }

        Spacer(modifier = Modifier.height(Theme.spacing.space300))

        Column(
            modifier = Modifier.padding(start = Theme.spacing.space100, bottom = Theme.spacing.space150)
        ) {
            Text(
                text = title,
                color = contentColor,
                style = Theme.textStyle.title.largeBold16,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            if (!subtitle.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(Theme.spacing.space25))
                Text(
                    text = subtitle,
                    color = Theme.color.surfaces.onSurfaceVariant,
                    style = Theme.textStyle.body.mediumMedium14,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

/**
 * Material 3 Expressive Search App Bar.
 * Features pill shape, centered or start text, internal/external icons, and click action.
 */
@Composable
fun SearchAppBar(
    placeholderText: String,
    modifier: Modifier = Modifier,
    onSearchClick: (() -> Unit)? = null,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Theme.spacing.space200, vertical = Theme.spacing.space100),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Theme.spacing.space150)
    ) {
        if (leadingIcon != null) {
            leadingIcon()
        }

        Row(
            modifier = Modifier
                .weight(1f)
                .height(48.dp)
                .clip(Theme.shapes.full)
                .background(Theme.color.surfaces.surfaceContainer)
                .then(if (onSearchClick != null) Modifier.bouncyClickable(onClick = onSearchClick) else Modifier)
                .padding(horizontal = Theme.spacing.space200),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = placeholderText,
                style = Theme.textStyle.body.mediumMedium14,
                color = Theme.color.surfaces.onSurfaceVariant,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        if (trailingIcon != null) {
            trailingIcon()
        }
    }
}

@Preview
@Composable
private fun CustomAppBarPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AppTheme(isDarkTheme = false) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Theme.color.surfaces.surface)
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // 1. Search app bar
                SearchAppBar(
                    placeholderText = "Search product",
                    leadingIcon = {
                        ActionIconButton(
                            icon = painterResource(Res.drawable.ic_back),
                            contentDescription = "Menu",
                            onClick = {}
                        )
                    }
                )

                // 2. Small flexible app bar with subtitle & filled button
                CustomAppBar(
                    title = "Image editor",
                    subtitle = "Refine your photo",
                    titleAlignment = TextAlign.Start,
                    header = {
                        ActionIconButton(
                            icon = painterResource(Res.drawable.ic_back),
                            contentDescription = "Back",
                            onClick = {}
                        )
                    },
                    tail = {
                        ActionIconButton(
                            icon = painterResource(Res.drawable.ic_bookmark),
                            contentDescription = "Done",
                            containerColor = Theme.color.brand.primary,
                            tint = Color.White,
                            onClick = {}
                        )
                    }
                )

                // 3. Medium flexible app bar
                MediumFlexibleAppBar(
                    title = "Daily activities",
                    subtitle = "Record new fitness goals",
                    header = {
                        ActionIconButton(
                            icon = painterResource(Res.drawable.ic_back),
                            contentDescription = "Back",
                            onClick = {}
                        )
                    },
                    tail = {
                        ActionIconButton(
                            icon = painterResource(Res.drawable.ic_group),
                            contentDescription = "Add",
                            containerColor = Theme.color.brand.primary,
                            tint = Color.White,
                            onClick = {}
                        )
                    }
                )

                // 4. Large flexible app bar
                LargeFlexibleAppBar(
                    title = "Top 10 hiking trails",
                    subtitle = "Discover popular trails",
                    header = {
                        ActionIconButton(
                            icon = painterResource(Res.drawable.ic_back),
                            contentDescription = "Back",
                            onClick = {}
                        )
                    },
                    tail = {
                        ActionIconButton(
                            icon = painterResource(Res.drawable.ic_bookmark),
                            contentDescription = "Save",
                            containerColor = Theme.color.surfaces.surfaceVariant,
                            tint = Theme.color.surfaces.onSurface,
                            onClick = {}
                        )
                    }
                )
            }
        }
    }
}
