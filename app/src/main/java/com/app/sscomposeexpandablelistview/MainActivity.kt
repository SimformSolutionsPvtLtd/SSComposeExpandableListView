package com.app.sscomposeexpandablelistview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.app.sscomposeexpandablelistview.ui.theme.SSComposeExpandableListViewTheme
import com.simform.expandablelistview.ComposeExpandableListView
import com.simform.expandablelistview.ExpandableListData
import com.simform.expandablelistview.HeaderStylingAttributes
import com.simform.expandablelistview.ListItemData

class MainActivity : ComponentActivity() {

    private val expandableListData = mutableStateListOf<ExpandableListData>()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        expandableListData.addAll(getExpandableListData())

        enableEdgeToEdge()
        setContent {
            SSComposeExpandableListViewTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                titleContentColor = MaterialTheme.colorScheme.primary,
                            ),
                            title = {
                                Text(stringResource(R.string.txtAppTitle))
                            }
                        )
                    }
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                PaddingValues(
                                    top = innerPadding.calculateTopPadding(),
                                    bottom = innerPadding.calculateBottomPadding(),
                                    start = 12.dp,
                                    end = 12.dp
                                )
                            )
                    ) {
                        Spacer(modifier = Modifier.height(24.dp))
                        ComposeExpandableListView(
                            modifier = Modifier
                                .fillMaxWidth(),
                            data = expandableListData,
                            headerStylingAttributes = HeaderStylingAttributes(
                                backgroundColor = Color.LightGray,
                                cornerRadius = 8.dp,
                                textStyle = MaterialTheme.typography.titleMedium
                            ),
                            onStateChanged = { index, data ->
                                updateExpandStatus(index, data)
                            },
                            onListItemClicked = { headerIndex, itemIndex, isSelected ->
                                listItemSelected(headerIndex, itemIndex, isSelected)
                            }
                        )
                    }
                }
            }
        }
    }

    private fun getExpandableListData(): List<ExpandableListData> {
        val dataList = ArrayList<ExpandableListData>()
        val listItems = ArrayList<ListItemData>().apply {
            repeat(5) {
                add(ListItemData("List item $it", false))
            }
        }
        repeat(5) {
            val data = ExpandableListData("header $it", listItems, false)
            dataList.add(data)
        }
        return dataList
    }

    private fun updateExpandStatus(index: Int, isExpanded: Boolean) {
        expandableListData[index] = expandableListData[index].copy(isExpanded = isExpanded)
    }

    private fun listItemSelected(headerIndex: Int, listItemIndex: Int, isSelected: Boolean) {
        val listItems = ArrayList(expandableListData[headerIndex].listItems)
        listItems[listItemIndex] = listItems[listItemIndex].copy(isSelected = isSelected)
        expandableListData[headerIndex] = expandableListData[headerIndex].copy(listItems = listItems)
    }
}


