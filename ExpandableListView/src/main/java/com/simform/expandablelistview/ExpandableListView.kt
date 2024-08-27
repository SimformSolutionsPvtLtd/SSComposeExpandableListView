package com.simform.expandablelistview

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.FrameMetricsAggregator.ANIMATION_DURATION

/**
 * Displays an expandable list view with headers and child items.
 * Each header can be expanded to reveal items, and users can select them.
 *
 * @param modifier Modifier for the root container, allowing customization like padding or size.
 * @param data List of [ExpandableListData] representing the expandable list's headers and child items.
 * @param headerStylingAttributes Defines styling for headers, including appearance and layout.
 * @param listItemStylingAttributes Defines styling for list items, including appearance and layout.
 * @param expandedIcon Drawable resource ID for the icon when a header is expanded (default: up arrow).
 * @param collapseIcon Drawable resource ID for the icon when a header is collapsed (default: down arrow).
 * @param onStateChanged Callback for when a header's expand/collapse state changes, providing the header index and expanded state.
 * @param onListItemClicked Callback for when a list item is clicked, providing the header index, item index, and selection state.
 */
@Composable
fun ComposeExpandableListView(
    modifier: Modifier = Modifier,
    data: List<ExpandableListData>,
    headerStylingAttributes: HeaderStylingAttributes = HeaderStylingAttributes(),
    listItemStylingAttributes: ListItemStylingAttributes = ListItemStylingAttributes(),
    @DrawableRes expandedIcon: Int = R.drawable.ic_arrow_drop_up,
    @DrawableRes collapseIcon: Int = R.drawable.ic_arrow_drop_down,
    onStateChanged: (headerIndex: Int, isExpanded: Boolean) -> Unit = { _, _ -> },
    onListItemClicked: (headerIndex: Int, itemIndex: Int, isSelected: Boolean) -> Unit = { _, _, _ -> }
) {

    LazyColumn(modifier = modifier) {
        data.forEachIndexed { index, data ->
            item {
                HeaderView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(
                                topStart = headerStylingAttributes.cornerRadius,
                                topEnd = headerStylingAttributes.cornerRadius,
                                bottomStart = if (data.isExpanded) 0.dp else headerStylingAttributes.cornerRadius,
                                bottomEnd = if (data.isExpanded) 0.dp else headerStylingAttributes.cornerRadius
                            )
                        )
                        .background(color = headerStylingAttributes.backgroundColor)
                        .clickable { onStateChanged(index, !data.isExpanded) },
                    text = data.headerText,
                    textStyle = headerStylingAttributes.textStyle,
                    isExpanded = data.isExpanded,
                    expandedIcon = expandedIcon,
                    collapseIcon = collapseIcon,
                ) {
                    onStateChanged(index, !data.isExpanded)
                }
            }

            itemsIndexed(data.listItems) { itemIndex, itemData ->
                AnimatedVisibility(
                    // Apply animation when list expands or collapse.
                    visible = data.isExpanded,
                    // Fade in + expand animation
                    enter = fadeIn(animationSpec = tween(ANIMATION_DURATION)) + expandVertically(
                        animationSpec = tween(ANIMATION_DURATION)
                    ),
                    //Fade out + shrink animation
                    exit = fadeOut(animationSpec = tween(ANIMATION_DURATION)) + shrinkVertically(
                        animationSpec = tween(ANIMATION_DURATION)
                    )
                ) {
                    ListItemView(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = if (itemData.isSelected) {
                                    // Set color based on selection state.
                                    listItemStylingAttributes.selectedBackgroundColor
                                } else {
                                    listItemStylingAttributes.backgroundColor
                                }
                            )
                            .clickable {
                                onListItemClicked(index, itemIndex, !itemData.isSelected)
                            },
                        text = itemData.name,
                        textStyle = if (itemData.isSelected) {
                            // Set text style based on selection state.
                            listItemStylingAttributes.selectedTextStyle
                        } else {
                            listItemStylingAttributes.textStyle
                        },
                        isSelected = itemData.isSelected
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

/**
 * Displays a header view that can be expanded or collapsed, with a text label and an icon.
 *
 * @param modifier Modifier for the header container, allowing customization like padding or click events.
 * @param text The text to display in the header.
 * @param textStyle The style for the header text (e.g., font size, color). Default is [TextStyle.Default].
 * @param isExpanded Whether the header is currently expanded or collapsed.
 * @param expandedIcon Drawable resource ID for the icon when the header is expanded (default: up arrow).
 * @param collapseIcon Drawable resource ID for the icon when the header is collapsed (default: down arrow).
 * @param onStateChanged Callback for when the header's expand/collapse state changes, providing the new state.
 */
@Composable
fun HeaderView(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = TextStyle.Default,
    isExpanded: Boolean,
    @DrawableRes expandedIcon: Int = R.drawable.ic_arrow_drop_up,
    @DrawableRes collapseIcon: Int = R.drawable.ic_arrow_drop_down,
    onStateChanged: () -> Unit = {},
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier
                .weight(0.9f)
                .padding(8.dp),
            text = text,
            style = textStyle
        )
        IconButton(onClick = onStateChanged) {
            Icon(
                painter = painterResource(
                    id = if (isExpanded) expandedIcon else collapseIcon
                ),
                contentDescription = stringResource(id = R.string.txtListIndicatorDescription)
            )
        }
    }
}

/**
 * Displays an individual item in a list view with text styling and selection support.
 *
 * @param modifier Modifier for the list item container, allowing customization like padding or click events.
 * @param text The text to display in the list item.
 * @param textStyle The style for the text (e.g., font weight, color). Default is [TextStyle.Default].
 * @param isSelected Whether the list item is currently selected. Default is `false`.
 */
@Composable
fun ListItemView(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = TextStyle.Default,
    isSelected: Boolean = false
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(0.9f)
                .padding(all = 12.dp),
            text = text,
            style = textStyle,
        )
        AnimatedVisibility(visible = isSelected) {
            Icon(
                painter = painterResource(id = R.drawable.ic_check),
                contentDescription = stringResource(R.string.txtSelectedListItemIcon),
                modifier = Modifier
                    .weight(0.1f)
                    .padding(end = 12.dp),
                tint = Color.Green
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun ComposeExpandableListViewPreview() {
    ComposeExpandableListView(
        data = listOf(
            ExpandableListData(
                stringResource(id = R.string.txtHeader),
                listOf(ListItemData(stringResource(id = R.string.txtListItem), false)),
            false
            )
        ),
        headerStylingAttributes = HeaderStylingAttributes(backgroundColor = Color.LightGray),
        listItemStylingAttributes = ListItemStylingAttributes(
            backgroundColor = Color.White,
            selectedBackgroundColor = Color.LightGray,
        ),
    )
}

@Composable
@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun HeaderViewPreview() {
    HeaderView(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(color = Color.LightGray),
        text = stringResource(id = R.string.txtHeader),
        isExpanded = false
    )
}

@Composable
@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun ListViewItemPreview() {
    ListItemView(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {}
            .background(color = Color.White),
        isSelected = true,
        text = stringResource(id = R.string.txtListItem)
    )
}
