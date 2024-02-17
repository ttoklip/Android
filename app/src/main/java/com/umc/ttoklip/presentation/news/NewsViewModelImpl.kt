package com.umc.ttoklip.presentation.news

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc.ttoklip.data.model.news.News
import com.umc.ttoklip.data.repository.news.NewsRepository
import com.umc.ttoklip.module.onException
import com.umc.ttoklip.module.onFail
import com.umc.ttoklip.module.onSuccess
import com.umc.ttoklip.presentation.news.adapter.NewsCard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Random
import javax.inject.Inject

@HiltViewModel
class NewsViewModelImpl @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel(), NewsViewModel {

    private val _isExpanded = MutableStateFlow<Boolean>(false)
    override val isExpanded: StateFlow<Boolean>
        get() = _isExpanded

    private val _houseWorkList = MutableStateFlow<List<News>>(listOf())
    override val houseWorkList: StateFlow<List<News>>
        get() = _houseWorkList

    private val _recipeList = MutableStateFlow<List<News>>(listOf())

    override val recipeList: StateFlow<List<News>>
        get() = _recipeList

    private val _safeLivingList = MutableStateFlow<List<News>>(listOf())

    override val safeLivingList: StateFlow<List<News>>
        get() = _safeLivingList

    private val _welfarePolicyList = MutableStateFlow<List<News>>(listOf())

    override val welfarePolicyList: StateFlow<List<News>>
        get() = _welfarePolicyList

    private val _randomNews = MutableStateFlow(listOf<NewsCard>())
    override val randomNews: StateFlow<List<NewsCard>>
        get() = _randomNews


    override fun expandedAppBar() {
        viewModelScope.launch {
            _isExpanded.emit(true)
        }
    }

    override fun collapsedAppBar() {
        viewModelScope.launch {
            _isExpanded.emit(false)
        }
    }

    override fun getMainNews() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                newsRepository.getNewsMain()
                    .onSuccess {
                        _houseWorkList.emit(it.categoryResponses.houseWork)
                        _recipeList.emit(it.categoryResponses.recipe)
                        _safeLivingList.emit(it.categoryResponses.safeLiving)
                        _welfarePolicyList.emit(it.categoryResponses.welfarePolicy)

                        _randomNews.value =
                        listOf(
                            NewsCard("집안일", _houseWorkList.value.shuffled().take(4)),
                            NewsCard("레시피", _recipeList.value.shuffled().take(4)),
                            NewsCard("안전한생활", _safeLivingList.value.shuffled().take(4)),
                            NewsCard("복지•정책", _welfarePolicyList.value.shuffled().take(4))
                        )
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


}