package com.simform.expandablelistview

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp


@Composable
fun ComposeExpandableListView(
    modifier: Modifier = Modifier,
    headerText: String,
    headerTextStyle: TextStyle = TextStyle.Default,
    headerBackGroundColor: Color = Color.Unspecified,
    @DrawableRes expandedIcon: Int = R.drawable.icon_baseline_arrow_drop_up_24,
    @DrawableRes collapseIcon: Int = R.drawable.icon_baseline_arrow_drop_down_24,
    listItemTextStyle: TextStyle = TextStyle.Default,
    selectedListItemTextStyle: TextStyle = TextStyle.Default,
    listItemBackGroundColor: Color = Color.Unspecified,
    listItemSelectedBackgroundColor: Color = Color.Unspecified,
    selectedItemIndex: Int = -1,
    onStateChange: (index: Int) -> Unit = {},
    onListItemClicked: (Int) -> Unit = {}
) {

    val list = remember {
        mutableStateListOf<Boolean>().apply {
            repeat(5) {
                add(false)
            }
        }
    }

    LazyColumn {
        for (i in 0..4) {
            item {
                HeaderView(
                    modifier = modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(color = headerBackGroundColor),
                    headerText = headerText,
                    headerTextStyle = headerTextStyle,
                    headerBackGroundColor = headerBackGroundColor,
                    isExpanded = list[i],
                    expandedIcon = expandedIcon,
                    collapseIcon = collapseIcon
                ) {
                    list[i] = it
                    onStateChange(i)
                }
            }

            items(5) {
                AnimatedVisibility(
                    visible = list[i]
                ) {
                    ListViewItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = if (selectedItemIndex == it) listItemSelectedBackgroundColor else listItemBackGroundColor)
                            .clickable {
                                onListItemClicked(selectedItemIndex)
                            }
                            .padding(all = 8.dp),
                        text = stringResource(id = R.string.txt_listItem),
                        textStyle = if (selectedItemIndex == it) selectedListItemTextStyle else listItemTextStyle,
                        color = if (selectedItemIndex == it) Color.Black else Color.DarkGray,
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

@Composable
fun HeaderView(
    modifier: Modifier = Modifier,
    headerText: String,
    headerTextStyle: TextStyle = TextStyle.Default,
    headerBackGroundColor: Color = Color.Unspecified,
    isExpanded: Boolean,
    @DrawableRes expandedIcon: Int = R.drawable.icon_baseline_arrow_drop_up_24,
    @DrawableRes collapseIcon: Int = R.drawable.icon_baseline_arrow_drop_down_24,
    onStateChange: (Boolean) -> Unit = {},
) {
    Row(
        modifier = modifier
            .clickable {
                onStateChange(!isExpanded)
            }
            .background(color = headerBackGroundColor)
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.weight(0.9f),
            text = headerText,
            style = headerTextStyle
        )
        IconButton(onClick = {
            onStateChange(!isExpanded)
        }) {
            Icon(
                painter = painterResource(
                    id = if (isExpanded) expandedIcon else collapseIcon
                ),
                contentDescription = stringResource(id = R.string.txt_listIndicatorDescription)
            )
        }
    }
}


@Composable
fun ListViewItem(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = TextStyle.Default,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified
) {
    Text(
        modifier = modifier,
        text = text,
        style = textStyle,
        color = color,
        fontSize = fontSize
    )
}

@Composable
@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun ComposeExpandableListViewPreview() {
    ComposeExpandableListView(
        headerText = stringResource(id = R.string.txt_header),
        headerBackGroundColor = Color.LightGray,
        listItemBackGroundColor = Color.White,
        listItemSelectedBackgroundColor = Color.LightGray
    )
}

@Composable
@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun HeaderViewPreview() {
    HeaderView(
        headerText = stringResource(id = R.string.txt_header),
        isExpanded = false
    )
}

@Composable
@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun ListViewItemPreview() {
    ListViewItem(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {}
            .background(color = Color.White)
            .padding(all = 8.dp),
        text = "Header"
    )
}
