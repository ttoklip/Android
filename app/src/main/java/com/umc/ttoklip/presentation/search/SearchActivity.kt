package com.umc.ttoklip.presentation.search

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivitySearchBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchActivity : BaseActivity<ActivitySearchBinding>(R.layout.activity_search) {

    private val viewModel: SearchViewModel by viewModels<SearchViewModelImpl>()

    override fun initView() {
        binding.vm = viewModel
        binding.backBtn.setOnClickListener {
            finish()
        }
        binding.filterBtn.setOnClickListener {

        }
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
                                binding.categoryFilterT.text = "사기"
                            }
                        }

                        5 -> {
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
    }

    companion object {
        const val SEARCH_ACTIVITY = "search"
        fun newIntent(context: Context) = Intent(context, SearchActivity::class.java)
    }
}