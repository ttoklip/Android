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
    }

    companion object {
        const val SEARCH_ACTIVITY = "search"
        fun newIntent(context: Context) = Intent(context, SearchActivity::class.java)
    }
}