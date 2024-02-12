package com.umc.ttoklip.presentation.honeytip

import android.net.Uri
import android.util.Log
import android.widget.Toolbar
import androidx.viewpager2.widget.ViewPager2
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivityImageViewBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.honeytip.adapter.Image
import com.umc.ttoklip.presentation.honeytip.adapter.ImageVPA

class ImageViewActivity: BaseActivity<ActivityImageViewBinding>(R.layout.activity_image_view) {
    override fun initView() {
        val images = (intent.getStringArrayExtra("images") ?: emptyArray()).map{ uriString ->
            Image(Uri.parse(uriString)) }
        val index = intent.getIntExtra("position", 0)
        val adapter = ImageVPA()
        binding.vp.adapter = adapter
        adapter.submitList(images)
        binding.totalTv.text = images.size.toString()
        binding.closeBtn.setOnClickListener {
            finish()
        }
        binding.vp.currentItem = index
        binding.currentTv.text = "${index+1}/"
        binding.vp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                binding.currentTv.text = "${position+1}/"
            }
        })
    }

    override fun initObserver() {

    }
}