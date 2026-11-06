package com.cairosquad.evolvefit.ui.screen.createExercise.content.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.cairosquad.evolvefit.design_system.component.CustomTick
import com.cairosquad.evolvefit.design_system.component.M3ExpressiveCheckbox
import com.cairosquad.evolvefit.design_system.theme.AppTheme
import com.cairosquad.evolvefit.design_system.theme.Theme
import com.cairosquad.evolvefit.design_system.theme.bouncyClickable
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun <T> CustomDropdownMenu(
    modifier: Modifier = Modifier,
    items: List<T>,
    onItemSelected: (T) -> Unit,
    isChecked: (T) -> Boolean,
    isSingleChoice: Boolean = false,
    labelProvider: @Composable (T) -> String
) {
    Column(
        modifier = Modifier
            .background(
                color = Theme.color.surfaces.surfaceContainer,
                shape = Theme.shapes.cardShape
            )
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items.forEach { item ->
            val checked = isChecked(item)
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .clip(Theme.shapes.small)
                    .bouncyClickable { onItemSelected(item) }
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = labelProvider(item),
                    style = if (checked) Theme.textStyle.body.mediumMedium14.copy(fontWeight = FontWeight.SemiBold)
                            else Theme.textStyle.body.mediumMedium14,
                    color = if (checked) Theme.color.surfaces.onSurface
                            else Theme.color.surfaces.onSurfaceVariant
                )
                Spacer(modifier = Modifier.weight(1f))
                if (isSingleChoice) {
                    CustomTick(
                        isChecked = checked,
                        modifier = Modifier.padding(end = 4.dp)
                    )
                } else {
                    M3ExpressiveCheckbox(
                        isChecked = checked,
                        modifier = Modifier.padding(end = 4.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun CustomDropdownMenuPreview() {
    AppTheme(isDarkTheme = true) {
        CustomDropdownMenu(
            items = listOf(
                "Item 1",
                "Item 2",
                "Item 3",
                "Item 4",
                "Item 5",
                "Item 6",
                "Item 7",
                "Item 8",
                "Item 9"
            ),
            onItemSelected = {},
            isChecked = { false },
            labelProvider = {""}
        )
    }
}