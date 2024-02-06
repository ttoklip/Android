package com.umc.ttoklip.presentation.honeytip

import android.widget.Toolbar
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivityImageViewBinding
import com.umc.ttoklip.presentation.base.BaseActivity

class ImageViewActivity: BaseActivity<ActivityImageViewBinding>(R.layout.activity_image_view) {
    override fun initView() {
        //setSupportActionBar()
        binding.root.setOnClickListener {
            if(supportActionBar?.isShowing == true){
                supportActionBar?.hide()
            }
            else{
                supportActionBar?.show()
            }
        }
    }

    override fun initObserver() {

    }
}