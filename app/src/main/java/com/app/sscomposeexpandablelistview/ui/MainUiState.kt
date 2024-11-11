package com.app.sscomposeexpandablelistview.ui

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.simform.expandablelistview.ExpandableListData

@Immutable
@Stable
data class MainUiState(
    val expandableListData: List<ExpandableListData>
)