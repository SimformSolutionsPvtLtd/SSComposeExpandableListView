package com.simform.expandablelistview

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


/**
 * Class for grouping styling parameters of [HeaderView]
 */
data class HeaderStylingAttributes(
    val backgroundColor: Color = Color.White,
    val cornerRadius: Dp = 8.dp,
    val textStyle: TextStyle = TextStyle(color = Color.DarkGray),
)

/**
 * Class for grouping styling parameters of [ListItemView]
 */
data class ListItemStylingAttributes(
    val backgroundColor: Color = Color.White,
    val selectedBackgroundColor: Color = Color.Unspecified,
    val textStyle: TextStyle = TextStyle.Default,
    val selectedTextStyle: TextStyle = TextStyle.Default,
)