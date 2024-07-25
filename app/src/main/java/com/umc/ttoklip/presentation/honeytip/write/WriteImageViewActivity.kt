package com.umc.ttoklip.presentation.honeytip.write

import android.content.Context
import android.content.Intent
import androidx.viewpager2.widget.ViewPager2
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivityImageViewBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.honeytip.adapter.ReadImageVPA
import com.umc.ttoklip.presentation.honeytip.adapter.WriteImageVPA
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WriteImageViewActivity: BaseActivity<ActivityImageViewBinding>(R.layout.activity_image_view) {
    private val images by lazy {
        (intent.getStringArrayExtra("images") ?: emptyArray()).toMutableList()
    }

    private val index by lazy {
        intent.getIntExtra("position", 0)
    }
    override fun initView() {
        initVPA()

        binding.currentTv.text = "${index+1}/"
        binding.totalTv.text = images.size.toString()

        binding.closeBtn.setOnClickListener {
            finish()
        }
    }

    private fun initVPA(){
        val writeImageVPA = WriteImageVPA(this)
        writeImageVPA.submitList(images)
        binding.vp.apply {
            adapter = writeImageVPA
            currentItem = index
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    binding.currentTv.text = "${position+1}/"
                }
            })
        }
    }

    override fun initObserver() {

    }

    companion object {
        fun newIntent(context: Context, images: Array<String>, position: Int) =
            Intent(context, WriteImageViewActivity::class.java).apply {
                putExtra("images", images)
                putExtra("position", position)
            }
    }
}