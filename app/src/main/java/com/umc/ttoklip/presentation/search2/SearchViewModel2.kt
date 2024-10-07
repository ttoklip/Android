package com.umc.ttoklip.presentation.search2

import com.umc.ttoklip.data.db.HistoryEntity
import com.umc.ttoklip.data.model.search.SearchModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface SearchViewModel2 {
    val searchText: MutableStateFlow<String>
    val searchAfter : StateFlow<Boolean>
    val filterSort : StateFlow<Int>
    val filterBoard : StateFlow<Int>
    val filterCategory: StateFlow<Int>
    val showDialog : SharedFlow<Boolean>
    val searchList : StateFlow<List<SearchModel>>
    val historyList : StateFlow<List<HistoryEntity>>
    val isTownTarget : StateFlow<Boolean>


    //--------new---//

    val searchTipList : StateFlow<List<SearchModel>>
    val searchNewsList : StateFlow<List<SearchModel>>
    val searchTourList : StateFlow<List<SearchModel>>

    fun getTourSearch(sort: String)
    fun getNewsSearch(sort: String)
    fun getTipSearch(sort: String)
    fun reset(id : Int)



    fun goSearchAfter()
    fun goSearchBefore()
    fun clickSearchAfter()
    fun clickFilter()
    fun clickHistory(title: String)
    fun getAllHistory()
    fun deleteAllHistory()
    fun filter(sort: Int, board: Int, category:Int)
}