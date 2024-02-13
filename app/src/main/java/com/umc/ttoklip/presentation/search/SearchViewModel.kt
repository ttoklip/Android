package com.umc.ttoklip.presentation.search

import com.umc.ttoklip.data.db.HistoryEntity
import com.umc.ttoklip.data.model.search.SearchModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface SearchViewModel {
    val searchText: MutableStateFlow<String>
    val searchAfter : StateFlow<Boolean>
    val filterSort : StateFlow<Int>
    val filterBoard : StateFlow<Int>
    val filterCategory: StateFlow<Int>
    val showDialog : SharedFlow<Boolean>
    val searchList : StateFlow<List<SearchModel>>
    val historyList : StateFlow<List<HistoryEntity>>

    fun goSearchAfter()
    fun goSearchBefore()
    fun clickSearchAfter()
    fun clickFilter()
    fun clickHistory(title: String)
    fun getAllHistory()
    fun deleteAllHistory()
    fun filter(sort: Int, board: Int, category:Int)
}