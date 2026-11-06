package com.cairosquad.evolvefit.design_system.text_styles

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

/**
 * M3 Expressive Type Roles and Styles.
 * Supports the 30 M3 type styles (15 baseline + 15 emphasized):
 * - Roles: Display, Headline, Title, Body, Label
 * - Sizes: Large, Medium, Small
 * - Emphasized tokens: Configurable weight increase for highlighted moments and clearer hierarchies.
 */
data class DisplayTextStyle(
    // M3 Expressive Baseline
    val displayLarge: TextStyle,
    val displayMedium: TextStyle,
    val displaySmall: TextStyle,
    // M3 Expressive Emphasized (md.sys.typescale.emphasized.display-*)
    val displayLargeEmphasized: TextStyle,
    val displayMediumEmphasized: TextStyle,
    val displaySmallEmphasized: TextStyle,

    // Backward-compatibility properties
    val largeBold24: TextStyle = displaySmall,
    val largeBold20: TextStyle = displaySmall.copy(fontSize = 20.sp, lineHeight = 28.sp),
    val mediumMedium20: TextStyle = displaySmall.copy(fontSize = 20.sp, lineHeight = 28.sp),
    val largeBold18: TextStyle = displaySmall.copy(fontSize = 18.sp, lineHeight = 24.sp),
    val expressiveHero36: TextStyle = displayLarge,
    val expressiveLarge32: TextStyle = displayMedium,
)

data class HeadlineTextStyle(
    // M3 Expressive Baseline
    val headlineLarge: TextStyle,
    val headlineMedium: TextStyle,
    val headlineSmall: TextStyle,
    // M3 Expressive Emphasized (md.sys.typescale.emphasized.headline-*)
    val headlineLargeEmphasized: TextStyle,
    val headlineMediumEmphasized: TextStyle,
    val headlineSmallEmphasized: TextStyle,

    // Backward-compatibility properties
    val largeBold18: TextStyle = headlineMedium,
    val mediumMedium18: TextStyle = headlineMedium,
    val largeBold16: TextStyle = headlineSmall,
    val mediumMedium16: TextStyle = headlineSmall,
    val expressiveHeadline28: TextStyle = headlineMedium,
)

data class TitleTextStyle(
    // M3 Expressive Baseline
    val titleLarge: TextStyle,
    val titleMedium: TextStyle,
    val titleSmall: TextStyle,
    // M3 Expressive Emphasized (md.sys.typescale.emphasized.title-*)
    val titleLargeEmphasized: TextStyle,
    val titleMediumEmphasized: TextStyle,
    val titleSmallEmphasized: TextStyle,

    // Backward-compatibility properties
    val largeBold16: TextStyle = titleMedium,
    val mediumMedium16: TextStyle = titleMedium,
    val largeBold14: TextStyle = titleSmall,
    val mediumMedium14: TextStyle = titleSmall,
    val expressiveTitle22: TextStyle = titleLarge,
)

data class LabelTextStyle(
    // M3 Expressive Baseline
    val labelLarge: TextStyle,
    val labelMedium: TextStyle,
    val labelSmall: TextStyle,
    // M3 Expressive Emphasized (md.sys.typescale.emphasized.label-*)
    val labelLargeEmphasized: TextStyle,
    val labelMediumEmphasized: TextStyle,
    val labelSmallEmphasized: TextStyle,

    // Backward-compatibility properties
    val mediumMedium16: TextStyle = labelLarge,
    val mediumMedium14: TextStyle = labelLarge,
    val smallRegular14: TextStyle = labelLarge,
    val mediumMedium12: TextStyle = labelMedium,
    val smallRegular12: TextStyle = labelSmall,
    val expressivePill14: TextStyle = labelLargeEmphasized,
)

data class BodyTextStyle(
    // M3 Expressive Baseline
    val bodyLarge: TextStyle,
    val bodyMedium: TextStyle,
    val bodySmall: TextStyle,
    // M3 Expressive Emphasized (md.sys.typescale.emphasized.body-*)
    val bodyLargeEmphasized: TextStyle,
    val bodyMediumEmphasized: TextStyle,
    val bodySmallEmphasized: TextStyle,

    // Backward-compatibility properties
    val smallRegular16: TextStyle = bodyLarge,
    val mediumMedium14: TextStyle = bodyMedium,
    val mediumMedium12: TextStyle = bodySmall,
    val smallRegular10: TextStyle = bodySmall.copy(fontSize = 10.sp, lineHeight = 14.sp),
    val expressiveBody16: TextStyle = bodyLarge,
)

data class AppTextStyle(
    val display: DisplayTextStyle,
    val headline: HeadlineTextStyle,
    val title: TitleTextStyle,
    val label: LabelTextStyle,
    val body: BodyTextStyle,
)