package com.app.sscomposeexpandablelistview

import android.content.res.Configuration
import android.os.Bundle
import android.widget.ExpandableListView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.UiMode
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.app.sscomposeexpandablelistview.ui.theme.SSComposeExpandableListViewTheme
import com.simform.expandablelistview.ComposeExpandableListView
import com.simform.expandablelistview.R

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SSComposeExpandableListViewTheme {
                Scaffold { innerPadding ->

                    var expandedIndex by remember {
                        mutableIntStateOf(-1)
                    }

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
                            modifier = Modifier,
                            headerText = stringResource(id = R.string.txt_header),
                            headerTextStyle = MaterialTheme.typography.titleMedium,
                            headerBackGroundColor = Color.LightGray,
                            onStateChange = {
                                expandedIndex = it
                            }
                        )
                    }
                }
            }
        }
    }
}


