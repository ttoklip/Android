package com.umc.ttoklip.presentation.honeytip

import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.tabs.TabLayoutMediator
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.FragmentShareHoneyTipBinding
import com.umc.ttoklip.presentation.base.BaseFragment
import com.umc.ttoklip.presentation.honeytip.adapter.CategoryVPA
import com.umc.ttoklip.presentation.honeytip.adapter.DailyPopularHoneyTipsVPA
import com.umc.ttoklip.presentation.honeytip.read.ReadHoneyTipActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShareHoneyTipFragment(
) : BaseFragment<FragmentShareHoneyTipBinding>(R.layout.fragment_share_honey_tip) {
    private val viewModel: HoneyTipViewModel by viewModels(
        ownerProducer = {requireParentFragment()}
    )
    private val popularHoneyTipsVPA by lazy {
        DailyPopularHoneyTipsVPA{
            val intent = Intent(requireContext(), ReadHoneyTipActivity::class.java)
            intent.putExtra("honeyTipId", it.id)
            intent.putExtra(BOARD, HONEY_TIPS)
            startActivity(intent)
        }
    }
    override fun initObserver() {
        /*lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.honeyTipMain.collect{
                    honeyTipMainResponse = it
                }
            }
        }*/
        /*viewModel.boardLiveData.observe(viewLifecycleOwner){
            changeCategory(it)
        }*/
        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.topFiveQuestions.collect{
                    //Log.d("housework honeytip", it.toString())
                    popularHoneyTipsVPA.submitList(it)
                }
            }
        }
    }

    override fun initView() {
        initCategoryViewPager()
        initPopularHoneyTipsViewPager(65, 30)
    }

    private fun initPopularHoneyTipsViewPager(previewWidth: Int, itemMargin: Int) {
        val decoMargin = previewWidth + itemMargin
        val pageTransX = decoMargin + previewWidth
        val decoration = PageDecoration(decoMargin)
        binding.popularHoneyTipsVp.apply {
            offscreenPageLimit = 1
            addItemDecoration(decoration)
            adapter = popularHoneyTipsVPA
            binding.indicator.attachTo(this)
            setPageTransformer { page, position ->
                page.translationX = position * -pageTransX
                when {
                    position < -1 -> {
                        page.background =
                            resources.getDrawable(R.drawable.item_daily_popular_honey_tip_external_background)
                    }

                    position <= 0.5 && position >= -0.5 -> {
                        page.background =
                            resources.getDrawable(R.drawable.item_daily_popular_honey_tip_background)
                    }

                    else -> {
                        page.background =
                            resources.getDrawable(R.drawable.item_daily_popular_honey_tip_external_background)
                    }
                }
            }
        }
    }

    private fun initCategoryViewPager() {
        val tabTitles = listOf("집안일", "레시피", "안전한 생활", "복지 \u00b7 정책")
        binding.categoryVp.adapter = CategoryVPA(this, HONEY_TIPS, tabTitles.size)
        TabLayoutMediator(binding.categoryTablayout, binding.categoryVp) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }

    /*private fun changeCategory(board: String){
        binding.categoryTablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if(board == HONEY_TIP) {
                    viewModel.setHoneyTipCategory(tab?.text.toString())
                    Log.d("Honey category", tab?.text.toString())
                } else {
                    viewModel.setQuestionCategory(tab?.text.toString())
                    Log.d("Question category", tab?.text.toString())
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
    }*/
}