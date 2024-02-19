package com.umc.ttoklip.presentation.mypage

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.ttoklip.R
import com.umc.ttoklip.data.model.mypage.ScrapResponse
import com.umc.ttoklip.databinding.ActivitySavedHoneyTipBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.honeytip.read.ReadHoneyTipActivity
import com.umc.ttoklip.presentation.mypage.adapter.HoneyTip
import com.umc.ttoklip.presentation.mypage.adapter.OnSpinnerItemClickListener
import com.umc.ttoklip.presentation.mypage.adapter.SavedHoneyTipAdapter
import com.umc.ttoklip.presentation.mypage.vm.ScrapViewModel
import com.umc.ttoklip.presentation.news.detail.ArticleActivity
import com.umc.ttoklip.presentation.search.adapter.HistoryRVA
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SavedHoneyTipActivity :
    BaseActivity<ActivitySavedHoneyTipBinding>(R.layout.activity_saved_honey_tip),
    OnSpinnerItemClickListener {

    private val viewModel: ScrapViewModel by viewModels<ScrapViewModel>()

    private val spinnerA by lazy {
        val boardFilters = listOf(
            "꿀팁 공유",
            "뉴스레터",
            "소통해요"
        )
        SortSpinnerAdapter(applicationContext, boardFilters)
    }

    private val scrapRVA by lazy {
        SavedHoneyTipAdapter(this)
    }
    override fun initView() {

        binding.boardFilterSpinner.adapter = spinnerA
        binding.boardFilterSpinner.setSelection(0)
        binding.boardFilterSpinner.selectedItem.toString()
        Log.d("왜안","${spinnerA.item}")

        binding.boardFilterSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                    viewModel.reset()
                    Log.d("왜안","${spinnerA.item}")

                    when (binding.boardFilterSpinner.selectedItem.toString()) {
                        "소통해요" -> {
                        viewModel.getTownScrap()
                        }
                        "꿀팁 공유" -> {
                            viewModel.getTipScrap()
                        }
                        "뉴스레터" -> {
                            viewModel.getNewsScrap()
                        }
                        else -> {}
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }

        binding.savedHoneyTipRv.layoutManager = LinearLayoutManager(this@SavedHoneyTipActivity)
        binding.savedHoneyTipRv.adapter = scrapRVA
       
        binding.savedHoneyTipBackBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.scrapList.collect {
                    scrapRVA.submitList(it)
                }
            }
        }

    }

    override fun onClick(honeyTip: ScrapResponse) {
        when (binding.boardFilterSpinner.selectedItem.toString()) {
            "소통해요" -> {

            }
            "꿀팁 공유" -> {

            }
            "뉴스레터" -> {
                startActivity(ArticleActivity.newIntent(this, honeyTip.id))
            }
            else -> {}
        }
    }

}