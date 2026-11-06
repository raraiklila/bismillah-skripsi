package com.cairosquad.evolvefit.design_system.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cairosquad.evolvefit.design_system.theme.Theme
import org.jetbrains.compose.resources.DrawableResource

@Composable
fun LabeledInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isDividerVisible: Boolean = false,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    readOnly: Boolean = false,
    leadingIcon: DrawableResource? = null,
    trailingIcon: DrawableResource? = null,
    onClick: (() -> Unit)? = null,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        if (label.isNotBlank()) {
            Text(
                text = label,
                style = Theme.textStyle.label.smallRegular12,
                color = Theme.color.surfaces.onSurfaceVariant,
                modifier = Modifier.padding(start = 2.dp)
            )
        }
        InputField(
            value = value,
            onValueChange = onValueChange,
            placeholder = placeholder.ifBlank { label },
            readOnly = readOnly,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            onTrailingIconClick = onClick,
            onClick = if (readOnly) onClick else null,
            modifier = Modifier.fillMaxWidth(),
            verticalPadding = 15.5.dp,
        )
    }
}
