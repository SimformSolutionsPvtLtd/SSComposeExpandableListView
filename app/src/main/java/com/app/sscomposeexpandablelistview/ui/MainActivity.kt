package com.app.sscomposeexpandablelistview.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.ArrayRes
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.sscomposeexpandablelistview.R
import com.app.sscomposeexpandablelistview.ui.theme.SSComposeExpandableListViewTheme
import com.simform.expandablelistview.ComposeExpandableListView
import com.simform.expandablelistview.ExpandableListData
import com.simform.expandablelistview.HeaderStylingAttributes
import com.simform.expandablelistview.ListItemData

class MainActivity : ComponentActivity() {

    private lateinit var mainViewModel: MainViewModel

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = MainViewModel(getExpandableListData())

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

                    val uiState by mainViewModel.uiState.collectAsStateWithLifecycle()

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 12.dp)
                            .padding(innerPadding)
                    ) {
                        Spacer(modifier = Modifier.height(24.dp))
                        ComposeExpandableListView(
                            modifier = Modifier
                                .fillMaxWidth(),
                            expandableListData = uiState.expandableListData,
                            headerStylingAttributes = HeaderStylingAttributes(
                                backgroundColor = Color.LightGray,
                                cornerRadius = 8.dp,
                                textStyle = MaterialTheme.typography.titleMedium
                            ),
                            onStateChanged = mainViewModel::updateExpandStatus,
                            onListItemClicked = mainViewModel::listItemSelected
                        )
                    }
                }
            }
        }
    }

    private fun getExpandableListData(): List<ExpandableListData> {
        val dataList = ArrayList<ExpandableListData>()
        dataList.add(ExpandableListData("Vegetables", getListItemData(R.array.vegetables), false))
        dataList.add(ExpandableListData("Fruits", getListItemData(R.array.fruits), false))
        dataList.add(
            ExpandableListData(
                "Milk Products",
                getListItemData(R.array.milk_products),
                false
            )
        )
        return dataList
    }

    private fun getListItemData(@ArrayRes resId: Int): ArrayList<ListItemData> {
        return ArrayList<ListItemData>().apply {
            resources.getStringArray(resId).forEach {
                add(ListItemData(it, false))
            }
        }
    }
}


