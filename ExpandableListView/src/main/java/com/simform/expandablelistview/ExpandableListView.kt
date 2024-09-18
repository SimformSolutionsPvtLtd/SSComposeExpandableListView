package com.simform.expandablelistview

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import com.simform.expandablelistview.ExpandableListViewDefaults.defaultContentAnimation
import com.simform.expandablelistview.ExpandableListViewDefaults.defaultHeaderPaddings
import com.simform.expandablelistview.ExpandableListViewDefaults.defaultHeaderStylingAttributes
import com.simform.expandablelistview.ExpandableListViewDefaults.defaultListContentPadding
import com.simform.expandablelistview.ExpandableListViewDefaults.defaultListItemStylingAttributes

/**
 * Displays an expandable list view with headers and child items.
 * Each header can be expanded to reveal items, and users can select them.
 *
 * @param modifier Modifier for the root container, allowing customization like padding or size.
 * @param expandableListData List of [ExpandableListData] representing the expandable list's headers and child items.
 * @param headerStylingAttributes Defines styling for headers, including appearance and layout.
 * @param listItemStylingAttributes Defines styling for list items, including appearance and layout.
 * @param contentAnimation Defines expand/collapse animation for expandable list content (default: [defaultContentAnimation]).
 * @param expandedIcon Drawable resource ID for the icon when a header is expanded (default: up arrow).
 * @param collapseIcon Drawable resource ID for the icon when a header is collapsed (default: down arrow).
 * @param itemSelectedIcon Drawable resource ID for the icon when a item is selected (default: check mark).
 * @param onStateChanged Callback for when a header's expand/collapse state changes, providing the header index and expanded state.
 * @param onListItemClicked Callback for when a list item is clicked, providing the header index, item index, and selection state.
 * @param onListItemLongClicked Callback for when a list item is long pressed, providing the header index, item index, and selection state.
 */
@Composable
fun ComposeExpandableListView(
    modifier: Modifier = Modifier,
    expandableListData: List<ExpandableListData>,
    headerStylingAttributes: HeaderStylingAttributes = defaultHeaderStylingAttributes,
    listItemStylingAttributes: ListItemStylingAttributes = defaultListItemStylingAttributes,
    contentAnimation: ContentAnimation = defaultContentAnimation,
    @DrawableRes expandedIcon: Int = R.drawable.ic_arrow_right,
    @DrawableRes collapseIcon: Int = R.drawable.ic_arrow_drop_down,
    @DrawableRes itemSelectedIcon: Int = R.drawable.ic_check,
    onStateChanged: (headerIndex: Int, isExpanded: Boolean) -> Unit = { _, _ -> },
    onListItemClicked: (headerIndex: Int, itemIndex: Int, isSelected: Boolean) -> Unit = { _, _, _ -> },
    onListItemLongClicked: (headerIndex: Int, itemIndex: Int, isSelected: Boolean) -> Unit = { _, _, _ -> },
) {

    LazyColumn(modifier = modifier) {
        composeExpandableListView(
            expandableListData = expandableListData,
            headerStylingAttributes = headerStylingAttributes,
            listItemStylingAttributes = listItemStylingAttributes,
            contentAnimation = contentAnimation,
            expandedIcon = expandedIcon,
            collapseIcon = collapseIcon,
            itemSelectedIcon = itemSelectedIcon,
            onStateChanged = onStateChanged,
            onListItemClicked = onListItemClicked,
            onListItemLongClicked = onListItemLongClicked
        )
    }
}

/**
 * Displays an expandable list view with headers and child items.
 * Each header can be expanded to reveal items, and users can select them.
 *
 * @param expandableListData List of [ExpandableListData] representing the expandable list's headers and child items.
 * @param headerStylingAttributes Defines styling for headers, including appearance and layout.
 * @param listItemStylingAttributes Defines styling for list items, including appearance and layout.
 * @param contentAnimation Defines expand/collapse animation for expandable list content (default: [defaultContentAnimation]).
 * @param expandedIcon Drawable resource ID for the icon when a header is expanded (default: up arrow).
 * @param collapseIcon Drawable resource ID for the icon when a header is collapsed (default: down arrow).
 * @param itemSelectedIcon Drawable resource ID for the icon when a item is selected (default: check mark).
 * @param onStateChanged Callback for when a header's expand/collapse state changes, providing the header index and expanded state.
 * @param onListItemClicked Callback for when a list item is clicked, providing the header index, item index, and selection state.
 * @param onListItemLongClicked Callback for when a list item is long pressed, providing the header index, item index, and selection state.
 */
@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.composeExpandableListView(
    expandableListData: List<ExpandableListData>,
    headerStylingAttributes: HeaderStylingAttributes? = null,
    listItemStylingAttributes: ListItemStylingAttributes? = null,
    contentAnimation: ContentAnimation = defaultContentAnimation,
    @DrawableRes expandedIcon: Int = R.drawable.ic_arrow_right,
    @DrawableRes collapseIcon: Int = R.drawable.ic_arrow_drop_down,
    @DrawableRes itemSelectedIcon: Int = R.drawable.ic_check,
    onStateChanged: (headerIndex: Int, isExpanded: Boolean) -> Unit = { _, _ -> },
    onListItemClicked: (headerIndex: Int, itemIndex: Int, isSelected: Boolean) -> Unit = { _, _, _ -> },
    onListItemLongClicked: (headerIndex: Int, itemIndex: Int, isSelected: Boolean) -> Unit = { _, _, _ -> },
) {
    expandableListData.fastForEachIndexed { index, data ->
        item(key = data.headerText) {
            // If not provide the headerStylingAttributes, we use defaultHeaderStylingAttributes
            val headerStyling = headerStylingAttributes ?: defaultHeaderStylingAttributes

            HeaderView(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(
                            topStart = headerStyling.cornerRadius,
                            topEnd = headerStyling.cornerRadius,
                            bottomStart = if (data.isExpanded) 0.dp else headerStyling.cornerRadius,
                            bottomEnd = if (data.isExpanded) 0.dp else headerStyling.cornerRadius
                        )
                    )
                    .background(color = headerStyling.backgroundColor)
                    .clickable { onStateChanged(index, !data.isExpanded) },
                text = data.headerText,
                textStyle = headerStyling.textStyle,
                headerPaddings = headerStyling.headerPaddings,
                isExpanded = data.isExpanded,
                expandedIcon = expandedIcon,
                collapseIcon = collapseIcon,
                categoryIcon = data.headerCategoryIcon
            )
        }


        itemsIndexed(
            items = data.listItems,
            key = { _, item ->
                "${data.headerText} - ${item.annotatedText ?: item.name}"
            }
        ) { itemIndex, itemData ->
            // If not provide the listItemStylingAttributes, we use defaultListItemStylingAttributes
            val listItemStyling = listItemStylingAttributes ?: defaultListItemStylingAttributes

            AnimatedVisibility(
                visible = data.isExpanded,
                enter = contentAnimation.expandAnimation,
                exit = contentAnimation.collapseAnimation
            ) {
                Column {
                    itemData.annotatedText?.let {
                        ListItemView(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(
                                    RoundedCornerShape(
                                        bottomStart = if (itemData == data.listItems.last()) dimensionResource(
                                            id = R.dimen.header_corner_radius
                                        ) else 0.dp,
                                        bottomEnd = if (itemData == data.listItems.last()) dimensionResource(
                                            id = R.dimen.header_corner_radius
                                        ) else 0.dp
                                    )
                                )
                                .background(
                                    color = if (itemData.isSelected) {
                                        // Set color based on selection state.
                                        listItemStyling.selectedBackgroundColor
                                    } else {
                                        listItemStyling.backgroundColor
                                    }
                                )
                                .combinedClickable(
                                    onClick = {
                                        onListItemClicked(index, itemIndex, !itemData.isSelected)
                                    },
                                    onLongClick = {
                                        onListItemLongClicked(
                                            index,
                                            itemIndex,
                                            !itemData.isSelected
                                        )
                                    }
                                ),
                            text = it,
                            textStyle = if (itemData.isSelected) {
                                // Set text style based on selection state.
                                listItemStyling.selectedTextStyle
                            } else {
                                listItemStyling.textStyle
                            },
                            contentPadding = listItemStyling.contentPadding,
                            itemSelectedIcon = itemSelectedIcon,
                            isSelected = itemData.isSelected
                        )
                    } ?: run {
                        itemData.name?.let {
                            ListItemView(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(
                                        RoundedCornerShape(
                                            bottomStart = if (itemData == data.listItems.last()) dimensionResource(
                                                id = R.dimen.header_corner_radius
                                            ) else 0.dp,
                                            bottomEnd = if (itemData == data.listItems.last()) dimensionResource(
                                                id = R.dimen.header_corner_radius
                                            ) else 0.dp
                                        )
                                    )
                                    .background(
                                        color = if (itemData.isSelected) {
                                            // Set color based on selection state.
                                            listItemStyling.selectedBackgroundColor
                                        } else {
                                            listItemStyling.backgroundColor
                                        }
                                    )
                                    .combinedClickable(
                                        onClick = {
                                            onListItemClicked(
                                                index,
                                                itemIndex,
                                                !itemData.isSelected
                                            )
                                        },
                                        onLongClick = {
                                            onListItemLongClicked(
                                                index,
                                                itemIndex,
                                                !itemData.isSelected
                                            )
                                        }
                                    ),
                                text = itemData.name,
                                textStyle = if (itemData.isSelected) {
                                    // Set text style based on selection state.
                                    listItemStyling.selectedTextStyle
                                } else {
                                    listItemStyling.textStyle
                                },
                                contentPadding = listItemStyling.contentPadding,
                                itemSelectedIcon = itemSelectedIcon,
                                isSelected = itemData.isSelected
                            )
                        }
                    }
                    if (itemData != data.listItems.last()) {
                        HorizontalDivider(
                            modifier = Modifier
                                .padding(horizontal = dimensionResource(id = R.dimen.dimen_4dp)),
                            thickness = dimensionResource(id = R.dimen.dimen_1dp),
                            color = Color.DarkGray
                        )
                    }
                }

            }
        }


        item {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_4dp)))
        }
    }
}

/**
 * Displays a header view that can be expanded or collapsed, with a text label and an icon.
 *
 * @param modifier Modifier for the header container, allowing customization like padding or click events.
 * @param text The text to display in the header.
 * @param textStyle The style for the header text (e.g., font size, color). Default is [TextStyle.Default].
 * @param headerPaddings Paddings for icon and text. (e.g., categoryIcon, text). Default is [defaultHeaderPaddings].
 * @param isExpanded Whether the header is currently expanded or collapsed.
 * @param expandedIcon for the icon when the header is expanded (default: up arrow).
 * @param collapseIcon for the icon when the header is collapsed (default: down arrow).
 * @param categoryIcon for the category icon.
 */
@Composable
private fun HeaderView(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = defaultHeaderStylingAttributes.textStyle,
    headerPaddings: HeaderPaddings = defaultHeaderStylingAttributes.headerPaddings,
    isExpanded: Boolean,
    @DrawableRes expandedIcon: Int = R.drawable.ic_arrow_right,
    @DrawableRes collapseIcon: Int = R.drawable.ic_arrow_drop_down,
    @DrawableRes categoryIcon: Int? = null,
) {
    Row(
        modifier = modifier.defaultMinSize(minHeight = dimensionResource(id = R.dimen.min_height)),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        categoryIcon?.let {
            Icon(
                modifier = Modifier
                    .weight(0.15f)
                    .padding(headerPaddings.categoryIconPadding),
                painter = painterResource(id = it),
                contentDescription = "Category Icon",
                tint = Color.Unspecified
            )
        }
        Text(
            modifier = Modifier
                .weight(0.9f)
                .padding(headerPaddings.textPadding),
            text = text,
            style = textStyle
        )
        Icon(
            modifier = Modifier
                .weight(0.15f)
                .padding(headerPaddings.actionIconPadding),
            painter = painterResource(
                id = if (isExpanded) collapseIcon else expandedIcon
            ),
            contentDescription = stringResource(id = R.string.txtListIndicatorDescription),
            tint = if (expandedIcon == R.drawable.ic_arrow_right && collapseIcon == R.drawable.ic_arrow_drop_down) MaterialTheme.colorScheme.onPrimaryContainer else Color.Unspecified
        )
    }
}

/**
 * Displays an individual item in a list view with text styling and selection support.
 *
 * @param modifier Modifier for the list item container, allowing customization like padding or click events.
 * @param text The text to display in the list item.
 * @param textStyle The style for the text (e.g., font weight, color). Default is [TextStyle.Default].
 * @param contentPadding Padding for text and icon (e.g., text, itemSelectedIcon). Default is [defaultListContentPadding].
 * @param itemSelectedIcon Drawable resource ID for the icon when a item is selected (default: check mark).
 * @param isSelected Whether the list item is currently selected. Default is `false`.
 */
@Composable
private fun ListItemView(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = defaultListItemStylingAttributes.textStyle,
    contentPadding: PaddingValues = defaultListItemStylingAttributes.contentPadding,
    @DrawableRes itemSelectedIcon: Int = R.drawable.ic_check,
    isSelected: Boolean = false
) {
    Row(
        modifier = modifier.defaultMinSize(minHeight = dimensionResource(id = R.dimen.min_height)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(0.9f)
                .padding(contentPadding),
            text = text,
            style = textStyle,
        )
        if (isSelected) {
            Icon(
                painter = painterResource(id = itemSelectedIcon),
                contentDescription = stringResource(R.string.txtSelectedListItemIcon),
                modifier = Modifier
                    .weight(0.1f)
                    .padding(contentPadding),
                tint = if (itemSelectedIcon == R.drawable.ic_check) MaterialTheme.colorScheme.onSurfaceVariant else Color.Unspecified
            )
        }
    }
}

/**
 * Displays an individual item in a list view with text styling and selection support.
 *
 * @param modifier Modifier for the list item container, allowing customization like padding or click events.
 * @param text The annotated text to display in the list item.
 * @param textStyle The style for the text (e.g., font weight, color). Default is [TextStyle.Default].
 * @param contentPadding Padding for text and icon (e.g., text, itemSelectedIcon). Default is [defaultListContentPadding].
 * @param itemSelectedIcon Drawable resource ID for the icon when a item is selected (default: check mark).
 * @param isSelected Whether the list item is currently selected. Default is `false`.
 */
@Composable
private fun ListItemView(
    modifier: Modifier = Modifier,
    text: AnnotatedString,
    textStyle: TextStyle = defaultListItemStylingAttributes.textStyle,
    contentPadding: PaddingValues = defaultListItemStylingAttributes.contentPadding,
    @DrawableRes itemSelectedIcon: Int = R.drawable.ic_check,
    isSelected: Boolean = false
) {
    Row(
        modifier = modifier.defaultMinSize(minHeight = dimensionResource(id = R.dimen.min_height)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(0.9f)
                .padding(contentPadding),
            text = text,
            style = textStyle,
        )
        if (isSelected) {
            Icon(
                painter = painterResource(id = itemSelectedIcon),
                contentDescription = stringResource(R.string.txtSelectedListItemIcon),
                modifier = Modifier
                    .weight(0.1f)
                    .padding(contentPadding),
                tint = if (itemSelectedIcon == R.drawable.ic_check) MaterialTheme.colorScheme.onSurfaceVariant else Color.Unspecified
            )
        }
    }
}

object ExpandableListViewDefaults {
    private val defaultHeaderTextPadding = PaddingValues(4.dp)
    private val defaultCategoryIconPadding = PaddingValues(4.dp)
    private val defaultActionIconPadding = PaddingValues(4.dp)
    val defaultListContentPadding = PaddingValues(8.dp)

    val defaultHeaderPaddings = HeaderPaddings(
        categoryIconPadding = defaultCategoryIconPadding,
        textPadding = defaultHeaderTextPadding,
        actionIconPadding = defaultActionIconPadding
    )

    val defaultHeaderStylingAttributes: HeaderStylingAttributes
        @Composable get() = HeaderStylingAttributes(
            backgroundColor = MaterialTheme.colorScheme.primaryContainer,
            cornerRadius = dimensionResource(id = R.dimen.header_corner_radius),
            textStyle = MaterialTheme.typography.titleMedium,
            headerPaddings = defaultHeaderPaddings
        )


    val defaultListItemStylingAttributes: ListItemStylingAttributes
        @Composable get() = ListItemStylingAttributes(
            backgroundColor = MaterialTheme.colorScheme.surfaceContainer,
            selectedBackgroundColor = MaterialTheme.colorScheme.surfaceVariant,
            textStyle = MaterialTheme.typography.bodyMedium,
            selectedTextStyle = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
            contentPadding = defaultListContentPadding
        )

    val defaultContentAnimation
        get() = ContentAnimation(
            expandAnimation = expandVertically(animationSpec = tween()),
            collapseAnimation = shrinkVertically(animationSpec = tween())
        )
}

@Composable
@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun ComposeExpandableListViewPreview() {
    ComposeExpandableListView(
        expandableListData = listOf(
            ExpandableListData(
                stringResource(id = R.string.txtHeader),
                listItems = listOf(
                    ListItemData(
                        name = stringResource(id = R.string.txtListItem),
                        isSelected = false
                    )
                ),
                isExpanded = false
            )
        )
    )
}

@Composable
@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun HeaderViewPreview() {
    HeaderView(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(color = MaterialTheme.colorScheme.primaryContainer),
        text = stringResource(id = R.string.txtHeader),
        categoryIcon = R.drawable.ic_audiotrack,
        isExpanded = false
    )
}

@Composable
@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun ListViewItemPreview() {
    ListItemView(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {}
            .background(color = MaterialTheme.colorScheme.background),
        isSelected = true,
        text = stringResource(id = R.string.txtListItem)
    )
}
