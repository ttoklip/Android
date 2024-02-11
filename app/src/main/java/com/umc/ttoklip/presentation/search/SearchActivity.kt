package com.umc.ttoklip.presentation.search

import android.content.Context
import android.content.Intent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivitySearchBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.news.adapter.Dummy
import com.umc.ttoklip.presentation.news.detail.ArticleActivity
import com.umc.ttoklip.presentation.search.adapter.HistoryModel
import com.umc.ttoklip.presentation.search.adapter.HistoryRVA
import com.umc.ttoklip.presentation.search.adapter.SearchRVA
import com.umc.ttoklip.presentation.search.dialog.BottomDialogSearchFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchActivity : BaseActivity<ActivitySearchBinding>(R.layout.activity_search) {

    private val viewModel: SearchViewModel by viewModels<SearchViewModelImpl>()

    private val historyRVA by lazy {
        HistoryRVA()
    }

    private val searchRVA by lazy {
        SearchRVA { category, id ->
            when (category) {
                1 -> {
                    startActivity(ArticleActivity.newIntent(this, id))
                }
                2 -> {}
                3 -> {}
                4 -> {}
                5 -> {}
                else -> {}
            }
        }
    }

    override fun initView() {
        binding.vm = viewModel
        binding.backBtn.setOnClickListener {
            finish()
        }

        //flexLayout -> RV 연동
        val flexboxLayoutManager = FlexboxLayoutManager(this).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            alignItems = AlignItems.STRETCH
        }
        binding.historyRV.run {
            layoutManager = flexboxLayoutManager
            adapter = historyRVA
            setHasFixedSize(false)
        }

        //editText 다시 검색
        binding.appBarTitleT.addTextChangedListener {
            viewModel.goSearchBefore()
        }

        //editText 키보드 닫기
        binding.appBarTitleT.setOnEditorActionListener { _, i, _ ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                if (!binding.appBarTitleT.text.isNullOrEmpty()) {
                    viewModel.clickSearchAfter()
                }
                true
            }
            false
        }

        binding.searchBtn.setOnClickListener {
            if (!binding.appBarTitleT.text.isNullOrEmpty()) {
                viewModel.clickSearchAfter()
            }
            if (!viewModel.searchAfter.value) {
                binding.appBarTitleT.setText("")
            }
        }
        binding.searchRV.adapter = searchRVA

        historyRVA.submitList(
            listOf(
                HistoryModel("keepGoingBro"),
                HistoryModel("keepGoingBro"),
                HistoryModel("keep"),
                HistoryModel("Going"),
                HistoryModel("Bro"),
                HistoryModel("haha"),
                HistoryModel("굿"),
                HistoryModel("하하"),
            )
        )

    }

    override fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.showDialog.collect {
                    if (it) {
                        val sheet = BottomDialogSearchFragment { filter ->
                            viewModel.filter(filter[0], filter[1], filter[2])
                        }
                        sheet.show(supportFragmentManager, sheet.tag)
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.filterSort.collect {
                    when (it) {
                        1 -> {
                            binding.sortfilterT.text = "최신순"
                        }

                        2 -> {
                            binding.sortfilterT.text = "인기순"
                        }

                        3 -> {
                            binding.sortfilterT.text = "댓글많은순"
                        }

                        else -> {}
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.filterBoard.collect {
                    when (it) {
                        1 -> {
                            binding.boardFilterT.text = "뉴스레터"
                        }

                        2 -> {
                            binding.boardFilterT.text = "질문해요"
                        }

                        3 -> {
                            binding.boardFilterT.text = "꿀팁 공유해요"
                        }

                        4 -> {
                            binding.boardFilterT.text = "함께해요"
                        }

                        5 -> {
                            binding.boardFilterT.text = "소통해요"
                        }

                        else -> {}
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.filterCategory.collect {
                    when (it) {
                        1 -> {
                            if (viewModel.filterBoard.value == 1) {
                                binding.categoryFilterT.text = "집안일"
                            } else {
                                binding.categoryFilterT.text = "집안일"
                            }

                        }

                        2 -> {
                            if (viewModel.filterBoard.value == 1) {
                                binding.categoryFilterT.text = "레시피"
                            } else {
                                binding.categoryFilterT.text = "요리"
                            }
                        }

                        3 -> {
                            if (viewModel.filterBoard.value == 1) {
                                binding.categoryFilterT.text = "안전한 생활"
                            } else {
                                binding.categoryFilterT.text = "안전한 생활"
                            }
                        }

                        4 -> {
                            if (viewModel.filterBoard.value == 1) {
                                binding.categoryFilterT.text = "복지•정책"
                            } else {
                                binding.categoryFilterT.text = "복지•정책"
                            }
                        }

                        else -> {}
                    }
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.searchAfter.collect {
                    if (it) {
                        //키보드 내리기
                        val manager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                        manager.hideSoftInputFromWindow(
                            binding.appBarTitleT.windowToken,
                            InputMethodManager.HIDE_NOT_ALWAYS
                        )
                        viewModel.filter(1, 0, 0)
                    } else {
                        viewModel.filter(0, 0, 0)
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.searchList.collect {
                    searchRVA.submitList(it)
                }
            }
        }
    }

    companion object {
        const val SEARCH_ACTIVITY = "search"
        fun newIntent(context: Context) = Intent(context, SearchActivity::class.java)
    }
}