package com.cairosquad.evolvefit.ui.screen.report.content.componant.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.cairosquad.evolvefit.design_system.theme.Theme

@Composable
fun ReportCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier,
    isBadge: Boolean = false,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(Theme.shapes.cardShape)
            .background(Theme.color.surfaces.surfaceContainer)
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = Theme.textStyle.headline.largeBold16,
                color = Theme.color.surfaces.onSurface
            )
            if (value.isNotEmpty()) {
                if (isBadge) {
                    Box(
                        modifier = Modifier
                            .clip(Theme.shapes.pillShape)
                            .background(Theme.color.surfaces.surfaceContainerHighest)
                            .padding(horizontal = 10.dp, vertical = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = value,
                            style = Theme.textStyle.label.labelSmallEmphasized,
                            color = Theme.color.surfaces.onSurfaceVariant
                        )
                    }
                } else {
                    Text(
                        text = value,
                        style = Theme.textStyle.title.titleMediumEmphasized,
                        color = Theme.color.brand.primary
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        content()
    }
}