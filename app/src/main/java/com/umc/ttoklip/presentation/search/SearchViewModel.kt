package com.umc.ttoklip.presentation.search

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface SearchViewModel {

    val filterSort : StateFlow<Int>
    val filterBoard : StateFlow<Int>
    val filterCategory: StateFlow<Int>
    val showDialog : SharedFlow<Boolean>

    fun clickFilter()
    fun filter(sort: Int, board: Int, category:Int)
}