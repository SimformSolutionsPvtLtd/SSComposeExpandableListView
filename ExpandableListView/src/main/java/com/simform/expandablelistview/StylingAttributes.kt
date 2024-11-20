package com.simform.expandablelistview

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Class for grouping styling parameters of [HeaderView]
 */
data class HeaderStylingAttributes(
    val backgroundColor: Color,
    val cornerRadius: Dp,
    val textStyle: TextStyle,
    val headerPaddings: HeaderPaddings
)

/**
 * Class for grouping styling parameters of [ListItemView]
 */
data class ListItemStylingAttributes(
    val backgroundColor: Color,
    val selectedBackgroundColor: Color,
    val textStyle: TextStyle,
    val selectedTextStyle: TextStyle,
    val contentPadding: PaddingValues
)

/**
 * Class for grouping [HeaderView] paddings.
 */
data class HeaderPaddings(
    val categoryIconPadding: PaddingValues,
    val textPadding: PaddingValues,
    val actionIconPadding: PaddingValues
)

/**
 * Class for grouping Expand/Collapse animation.
 */
data class ContentAnimation(
    val expandAnimation: EnterTransition,
    val collapseAnimation: ExitTransition
)