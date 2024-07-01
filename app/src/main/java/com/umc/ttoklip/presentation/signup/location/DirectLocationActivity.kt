package com.umc.ttoklip.presentation.signup.location

import androidx.activity.viewModels
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivityDirectLocationBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DirectLocationActivity :
    BaseActivity<ActivityDirectLocationBinding>(R.layout.activity_direct_location) {

        private val viewModel:DirectLocationViewModel by viewModels()

    override fun initView() {
        binding.directLocationSearchIv.setOnClickListener {
            viewModel.searchAddress(binding.directLocationAddressEt.text.toString())
        }
    }

    override fun initObserver() {
    }
}