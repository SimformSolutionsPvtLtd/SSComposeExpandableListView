package com.app.sscomposeexpandablelistview.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.ArrayRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.LinkInteractionListener
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.withLink
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.sscomposeexpandablelistview.R
import com.app.sscomposeexpandablelistview.ui.theme.SSComposeExpandableListViewTheme
import com.simform.expandablelistview.ExpandableListData
import com.simform.expandablelistview.ExpandableListViewDefaults
import com.simform.expandablelistview.ListItemData
import com.simform.expandablelistview.composeExpandableListView

class MainActivity : ComponentActivity() {

    private lateinit var mainViewModel: MainViewModel

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = MainViewModel(getExpandableListData(), getQnAListItem())

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
                    val headerPaddings = ExpandableListViewDefaults.defaultHeaderPaddings.copy(
                        textPadding = PaddingValues(
                            horizontal = dimensionResource(id = R.dimen.dimen_12_dp),
                            vertical = dimensionResource(id = R.dimen.dimen_8_dp)
                        )
                    )
                    val headerStylingAttributes =
                        ExpandableListViewDefaults.defaultHeaderStylingAttributes.copy(
                            headerPaddings = headerPaddings
                        )

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = dimensionResource(id = R.dimen.dimen_12_dp))
                            .padding(innerPadding)
                    ) {
                        item {
                            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_24_dp)))
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(dimensionResource(id = R.dimen.dimen_8_dp)),
                                text = "Simple",
                                style = MaterialTheme.typography.headlineMedium
                            )
                        }
                        composeExpandableListView(
                            expandableListData = uiState.simpleExpandableListData,
                            onStateChanged = mainViewModel::updateExpandStatus,
                            onListItemClicked = mainViewModel::listItemSelected,
                            onListItemLongClicked = this@MainActivity::listItemLongClicked
                        )
                        item {
                            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_12_dp)))
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(dimensionResource(id = R.dimen.dimen_8_dp)),
                                text = "FAQ",
                                style = MaterialTheme.typography.headlineMedium
                            )
                        }
                        composeExpandableListView(
                            expandableListData = uiState.faqExpandableListData,
                            headerStylingAttributes = headerStylingAttributes,
                            onStateChanged = mainViewModel::updateFAQExpandedState,
                            onListItemLongClicked = this@MainActivity::listItemFAQLongClicked
                        )
                    }
                }
            }
        }
    }

    private fun getExpandableListData(): List<ExpandableListData> {
        val dataList = ArrayList<ExpandableListData>()
        dataList.add(
            ExpandableListData(
                headerText = "Vegetables",
                listItems = getListItemData(R.array.vegetables),
                headerCategoryIcon = R.drawable.ic_vegitable
            )
        )
        dataList.add(
            ExpandableListData(
                headerText = "Fruits",
                listItems = getListItemData(R.array.fruits),
                headerCategoryIcon = R.drawable.ic_fruits
            )
        )
        dataList.add(
            ExpandableListData(
                headerText = "Milk Products",
                listItems = getListItemData(R.array.milk_products),
                headerCategoryIcon = R.drawable.ic_milk_products
            )
        )
        return dataList
    }

    private fun getListItemData(@ArrayRes resId: Int): ArrayList<ListItemData> {
        return ArrayList<ListItemData>().apply {
            resources.getStringArray(resId).forEach {
                add(ListItemData(it, isSelected = false))
            }
        }
    }

    private fun getQnAListItem(): ArrayList<ExpandableListData> {
        val items = ArrayList<ExpandableListData>()
        items.add(
            ExpandableListData(
                headerText = getString(R.string.question_first),
                listItems = listOf(
                    ListItemData(
                        name = getString(R.string.answer_first),
                    )
                ),
            )
        )
        items.add(
            ExpandableListData(
                headerText = getString(R.string.question_second),
                listItems = listOf(
                    ListItemData(
                        annotatedText = buildAnnotatedString {
                            append(getString(R.string.answer_second))
                            withLink(
                                LinkAnnotation.Clickable(
                                    tag = "Sign in",
                                    styles = TextLinkStyles(
                                        style = SpanStyle(
                                            color = Color.Blue
                                        )
                                    ),
                                    linkInteractionListener = LinkInteractionListener {
                                        Toast.makeText(
                                            this@MainActivity,
                                            "Link clicked",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                )
                            ) {
                                append(" Click here")
                            }
                        },
                    )
                ),
            )
        )
        items.add(
            ExpandableListData(
                headerText = getString(R.string.question_third),
                listItems = listOf(
                    ListItemData(
                        annotatedText = AnnotatedString.fromHtml(getString(R.string.answer_third)),
                    )
                ),
            )
        )
        return items
    }

    private fun listItemLongClicked(headerIndex: Int, listItemIndex: Int, isSelected: Boolean) {
        val listItem =
            mainViewModel.uiState.value.simpleExpandableListData.get(headerIndex).listItems.get(
                listItemIndex
            )
        Toast.makeText(this, "${listItem.name} Long clicked", Toast.LENGTH_SHORT).show()
    }

    private fun listItemFAQLongClicked(headerIndex: Int, listItemIndex: Int, isSelected: Boolean) {
        val listItem =
            mainViewModel.uiState.value.faqExpandableListData.get(headerIndex).listItems.get(
                listItemIndex
            )
        Toast.makeText(
            this,
            "${listItem.name ?: listItem.annotatedText} Long clicked",
            Toast.LENGTH_SHORT
        ).show()
    }
}


