package com.simform.expandablelistview

/**
 * Class for holding the input data for [ComposeExpandableListView]
 */
data class ExpandableListData(
    val headerText: String,
    val listItems: List<ListItemData>,
    val isExpanded: Boolean = false
)

/**
 * Class for holding list item data [ListItemView]
 */
data class ListItemData(
    val name: String,
    val isSelected: Boolean = false
)
