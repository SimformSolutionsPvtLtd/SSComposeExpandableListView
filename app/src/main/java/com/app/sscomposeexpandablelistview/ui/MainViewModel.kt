package com.app.sscomposeexpandablelistview.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simform.expandablelistview.ExpandableListData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class MainViewModel(private val expandableListData: List<ExpandableListData>) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState(expandableListData = expandableListData))
    val uiState = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = MainUiState(expandableListData = expandableListData)
    )

    fun updateExpandStatus(index: Int, isExpanded: Boolean) {
        _uiState.update { state ->
            state.copy(expandableListData = state.expandableListData.mapIndexed { indexOfData, expandableListData ->
                if (indexOfData == index) expandableListData.copy(isExpanded = isExpanded) else expandableListData
            })
        }
    }

    fun listItemSelected(headerIndex: Int, listItemIndex: Int, isSelected: Boolean) {
        val listItems =
            _uiState.value.expandableListData[headerIndex].listItems.mapIndexed { index, listItemData ->
                if (index == listItemIndex) listItemData.copy(isSelected = isSelected) else listItemData
            }
        val expandableListData =
            _uiState.value.expandableListData.mapIndexed { indexOfData: Int, expandableListData: ExpandableListData ->
                if (indexOfData == headerIndex) expandableListData.copy(listItems = listItems) else expandableListData
            }

        _uiState.update { state ->
            state.copy(expandableListData = expandableListData)
        }
    }
}