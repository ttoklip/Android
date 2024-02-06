package com.umc.ttoklip.presentation.honeytip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.FragmentShareHoneyTipBinding
import com.umc.ttoklip.presentation.base.BaseFragment
import com.umc.ttoklip.presentation.honeytip.adapter.CategoryVPA
import com.umc.ttoklip.presentation.honeytip.adapter.DailyPopularHoneyTipsVPA

class ShareHoneyTipFragment: BaseFragment<FragmentShareHoneyTipBinding>(R.layout.fragment_share_honey_tip) {
    override fun initObserver() {
    }

    override fun initView() {
        initPopularHoneyTipsViewPager(65, 30)
        initCategoryViewPager()
    }
    private fun initPopularHoneyTipsViewPager(previewWidth: Int, itemMargin: Int) {
        val decoMargin = previewWidth + itemMargin
        val pageTransX = decoMargin + previewWidth
        val decoration = PageDecoration(decoMargin)
        binding.popularHoneyTipsVp.apply {
            offscreenPageLimit = 1
            addItemDecoration(decoration)
            adapter = DailyPopularHoneyTipsVPA()
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

    private fun initCategoryViewPager(){
        val tabTitles = listOf("집안일", "레시피", "안전한 생활", "복지 \u00b7 정책")
        binding.categoryVp.adapter = CategoryVPA(this, tabTitles.size)

        TabLayoutMediator(binding.categoryTablayout, binding.categoryVp){ tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }
}