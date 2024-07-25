package com.umc.ttoklip.presentation.news.detail

import androidx.viewpager2.widget.ViewPager2
import com.umc.ttoklip.R
import com.umc.ttoklip.data.model.news.detail.ImageUrl
import com.umc.ttoklip.databinding.ActivityImageViewBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.news.adapter.PostImageVPA

class PostImageActivity : BaseActivity<ActivityImageViewBinding>(R.layout.activity_image_view) {

    override fun initView() {
        val images = (intent.getStringArrayExtra("images") ?: emptyArray()).map { uriString ->
            ImageUrl(uriString)
        }
        val adapter = PostImageVPA()
        binding.vp.adapter = adapter
        adapter.submitList(images)
        binding.totalTv.text = images.size.toString()
        binding.closeBtn.setOnClickListener {
            finish()
        }

        binding.vp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.currentTv.text = "${position + 1}/"
            }
        })
    }

    override fun initObserver() {

    }
}