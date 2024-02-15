package com.umc.ttoklip.presentation.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc.ttoklip.data.db.HistoryDao
import com.umc.ttoklip.data.db.HistoryEntity
import com.umc.ttoklip.data.model.search.SearchModel
import com.umc.ttoklip.data.repository.search.SearchRepository
import com.umc.ttoklip.data.repository.search.SearchRepositoryImpl
import com.umc.ttoklip.module.onException
import com.umc.ttoklip.module.onFail
import com.umc.ttoklip.module.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.annotation.meta.When
import javax.inject.Inject

@HiltViewModel
class SearchViewModelImpl @Inject constructor(
    private val historyDao: HistoryDao,
    private val searchRepository: SearchRepository
) : ViewModel(), SearchViewModel {

    override val searchText: MutableStateFlow<String> = MutableStateFlow("")
    private val _searchAfter = MutableStateFlow(false)
    override val searchAfter: StateFlow<Boolean>
        get() = _searchAfter
    private val _showDialog = MutableSharedFlow<Boolean>()
    private val _filterSort = MutableStateFlow(1)
    override val filterSort: StateFlow<Int>
        get() = _filterSort
    private val _filterBoard = MutableStateFlow(0)
    override val filterBoard: StateFlow<Int>
        get() = _filterBoard

    private val _filterCategory = MutableStateFlow(0)
    override val filterCategory: StateFlow<Int>
        get() = _filterCategory
    override val showDialog: SharedFlow<Boolean>
        get() = _showDialog
    private val _searchList = MutableStateFlow(listOf<SearchModel>())
    override val searchList: StateFlow<List<SearchModel>>
        get() = _searchList
    private val _historyList = MutableStateFlow(listOf<HistoryEntity>())
    override val historyList: StateFlow<List<HistoryEntity>>
        get() = _historyList

    private val fSearchList = MutableStateFlow(mutableListOf<SearchModel>())

    override fun goSearchAfter() {
        viewModelScope.launch {
            _searchAfter.emit(true)
            try {
                searchRepository.getNewsSearch(title = searchText.value).onSuccess {
                    _searchList.emit(it)
                    fSearchList.emit(it.toMutableList())
                    addHistory()
                    searchTip()
                    searchTown()
                }.onFail {

                }.onException {
                    throw it
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("예외", "$e")
            }

        }
    }

    override fun goSearchBefore() {
        viewModelScope.launch {
            _searchAfter.emit(false)
        }
    }

    override fun clickSearchAfter() {
        viewModelScope.launch {
            if (!searchAfter.value) {
                _searchAfter.emit(true)
                goSearchAfter()
            } else {
                _searchAfter.emit(false)
            }
        }
    }

    override fun clickFilter() {
        viewModelScope.launch {
            _showDialog.emit(true)
        }
    }

    override fun clickHistory(title: String) {
        viewModelScope.launch {
            _searchAfter.emit(true)
            try {
                searchRepository.getNewsSearch(title = title).onSuccess {
                    _searchList.emit(it)
                    fSearchList.emit(it.toMutableList())
                    searchTip()
                    searchTown()
                }.onFail {

                }.onException {
                    throw it
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("예외", "$e")
            }

        }
    }

    override fun filter(sort: Int, board: Int, category: Int) {
        viewModelScope.launch {
            _filterSort.value = sort
            _filterBoard.value = board
            _filterCategory.value = category
            filterCategory(filterSort.value, filterBoard.value, filterCategory.value)
        }
    }

    private fun searchTip() {
        viewModelScope.launch {
            try {
                searchRepository.getTipSearch(title = searchText.value).onSuccess {
                    _searchList.emit(searchList.value + it)
                    fSearchList.emit((fSearchList.value + it.toMutableList()) as MutableList<SearchModel>)
                }.onFail {

                }.onException {
                    throw it
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("예외", "$e")
            }
        }
    }

    private fun searchTown() {
        viewModelScope.launch {
            try {
                searchRepository.getTownSearch(title = searchText.value).onSuccess {
                    _searchList.emit(searchList.value + it)
                    fSearchList.emit((fSearchList.value + it.toMutableList()) as MutableList<SearchModel>)
                }.onFail {

                }.onException {
                    throw it
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("예외", "$e")
            }
        }
    }

    suspend fun addHistory() {
        historyDao.addHistory(HistoryEntity(historyList.value.size, searchText.value))
        viewModelScope.launch {
            _historyList.emit(listOf( HistoryEntity(historyList.value.size, searchText.value)) + historyList.value)
        }
    }

    override fun getAllHistory() {
        viewModelScope.launch {
            val history = historyDao.getAll().sortedBy { it.id }
            _historyList.emit(history)
        }
    }

    override fun deleteAllHistory(){
        viewModelScope.launch {
            historyDao.deleteAll()
            _historyList.emit(listOf())
        }
    }
    private suspend fun filterSort(sort: Int) {
        when (sort) {
            1 -> {
                _searchList.emit(fSearchList.value)
            }

            2 -> {
                _searchList.emit(fSearchList.value.sortedBy { it.commentCount }.reversed())
            }

            3 -> {
                _searchList.emit(fSearchList.value.sortedBy { it.commentCount }.reversed())
            }
        }
    }

    private suspend fun filterCategory(sort: Int, board: Int, category: Int) {
        val copy: MutableList<SearchModel> = mutableListOf()
        copy.addAll(fSearchList.value)
        when (sort) {
            1 -> {
                when (board) {
                    1 -> {
                        when (category) {
                            1 -> {
                                copy.removeIf { it.bigCategory != 1 || it.category != "HOUSEWORK" }
                                _searchList.emit(copy)
                            }

                            2 -> {
                                copy.removeIf { it.bigCategory != 1 || it.category != "RECIPE" }
                                _searchList.emit(copy)
                            }

                            3 -> {
                                copy.removeIf { it.bigCategory != 1 || it.category != "SAFE_LIVING" }
                                _searchList.emit(copy)
                            }

                            4 -> {
                                copy.removeIf { it.bigCategory != 1 || it.category != "WELFARE_POLICY" }
                                _searchList.emit(copy)
                            }

                            else -> {
                                copy.removeIf { it.bigCategory != 1 }
                                _searchList.emit(copy)
                            }
                        }
                    }

                    2 -> {
                        _searchList.emit(listOf())
                    }

                    3 -> {
                        when (category) {
                            1 -> {
                                copy.removeIf { it.bigCategory != 3 || it.category != "HOUSEWORK" }
                                _searchList.emit(copy)
                            }

                            2 -> {
                                copy.removeIf { it.bigCategory != 3 || it.category != "RECIPE" }
                                _searchList.emit(copy)
                            }

                            3 -> {
                                copy.removeIf { it.bigCategory != 3 || it.category != "SAFE_LIVING" }
                                _searchList.emit(copy)
                            }

                            4 -> {
                                copy.removeIf { it.bigCategory != 3 || it.category != "WELFARE_POLICY" }
                                _searchList.emit(copy)
                            }

                            else -> {
                                copy.removeIf { it.bigCategory != 3 }
                                _searchList.emit(copy)
                            }
                        }
                    }

                    4 -> {
                        _searchList.emit(listOf())
                    }

                    5 -> {
                        copy.removeIf { it.bigCategory != 5 }
                        _searchList.emit(copy)
                    }

                    else -> {
                        _searchList.emit(copy)
                    }
                }
            }

            else -> {

                when (board) {
                    1 -> {
                        when (category) {
                            1 -> {
                                copy.removeIf { it.bigCategory != 1 || it.category != "HOUSEWORK" }
                                _searchList.emit(copy.sortedBy { it.commentCount }.reversed())
                            }

                            2 -> {
                                copy.removeIf { it.bigCategory != 1 || it.category != "RECIPE" }
                                _searchList.emit(copy.sortedBy { it.commentCount }.reversed())
                            }

                            3 -> {
                                copy.removeIf { it.bigCategory != 1 || it.category != "SAFE_LIVING" }
                                _searchList.emit(copy.sortedBy { it.commentCount }.reversed())
                            }

                            4 -> {
                                copy.removeIf { it.bigCategory != 1 || it.category != "WELFARE_POLICY" }
                                _searchList.emit(copy.sortedBy { it.commentCount }.reversed())
                            }

                            else -> {
                                copy.removeIf { it.bigCategory != 1 }
                                _searchList.emit(copy.sortedBy { it.commentCount }.reversed())
                            }
                        }

                    }

                    2 -> {
                        _searchList.emit(listOf())
                    }

                    3 -> {
                        when (category) {
                            1 -> {
                                copy.removeIf { it.bigCategory != 3 || it.category != "HOUSEWORK" }
                                _searchList.emit(copy)
                            }

                            2 -> {
                                copy.removeIf { it.bigCategory != 3 || it.category != "RECIPE" }
                                _searchList.emit(copy)
                            }

                            3 -> {
                                copy.removeIf { it.bigCategory != 3 || it.category != "SAFE_LIVING" }
                                _searchList.emit(copy)
                            }

                            4 -> {
                                copy.removeIf { it.bigCategory != 3 || it.category != "WELFARE_POLICY" }
                                _searchList.emit(copy)
                            }

                            else -> {
                                copy.removeIf { it.bigCategory != 3 }
                                _searchList.emit(copy)
                            }
                        }
                    }

                    4 -> {
                        _searchList.emit(listOf())
                    }

                    5 -> {
                        copy.removeIf { it.bigCategory != 5 }
                        _searchList.emit(copy)
                    }

                    else -> {
                        _searchList.emit(copy.sortedBy { it.commentCount }.reversed())
                    }
                }
            }
        }
    }
}