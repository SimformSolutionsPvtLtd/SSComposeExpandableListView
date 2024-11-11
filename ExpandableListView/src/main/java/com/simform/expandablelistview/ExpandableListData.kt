package com.simform.expandablelistview

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

/**
 * Class for holding the input data for [ComposeExpandableListView]
 */
@Stable
@Immutable
data class ExpandableListData(
    val headerText: String,
    val listItems: List<ListItemData>,
    val isExpanded: Boolean = false
)

/**
 * Class for holding list item data [ListItemView]
 */
@Stable
@Immutable
data class ListItemData(
    val name: String,
    val isSelected: Boolean = false
)
