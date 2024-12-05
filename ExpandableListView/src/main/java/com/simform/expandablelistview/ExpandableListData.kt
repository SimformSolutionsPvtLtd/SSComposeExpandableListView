package com.simform.expandablelistview

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.text.AnnotatedString

/**
 * Class for holding the input data for [ComposeExpandableListView]
 */
@Stable
@Immutable
data class ExpandableListData(
    val headerText: String,
    val listItems: List<ListItemData>,
    val isExpanded: Boolean = false,
    @DrawableRes val headerCategoryIcon: Int? = null
)

/**
 * Class for holding list item data [ListItemView]
 */
@Stable
@Immutable
data class ListItemData(
    val name: String? = null,
    val annotatedText: AnnotatedString? = null,
    val isSelected: Boolean = false
)