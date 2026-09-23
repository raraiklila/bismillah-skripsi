package com.cairosquad.evolvefit.design_system.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.cairosquad.evolvefit.design_system.colors.ThemeColors
import com.cairosquad.evolvefit.design_system.text_styles.AppTextStyle

object Theme {
    val color: ThemeColors
        @Composable @ReadOnlyComposable get() = LocalThemeColor.current

    val textStyle: AppTextStyle
        @Composable @ReadOnlyComposable get() = LocalTextStyle.current

    val typography: Typography
        @Composable @ReadOnlyComposable get() = MaterialTheme.typography

    val isDark: Boolean
        @Composable @ReadOnlyComposable get() = LocalIsDark.current
}