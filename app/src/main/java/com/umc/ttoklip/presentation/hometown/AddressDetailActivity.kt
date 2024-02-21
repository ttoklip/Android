package com.umc.ttoklip.presentation.hometown

import android.content.Intent
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivityAddressDetailBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddressDetailActivity :
    BaseActivity<ActivityAddressDetailBinding>(R.layout.activity_address_detail) {
    override fun initView() {
        val type = intent.getStringExtra("place")
        val address = intent.getStringExtra("address")
        address?.let {
            binding.dealPlaceTv.text = address
        }
        type?.let {
            binding.titleTv.text = getString(R.string.my_hometown_address_title)
        }
        binding.locationNextBtn.setOnClickListener {
            val intent = Intent(this, PlaceActivity::class.java)
            intent.putExtra("address", address)
            intent.putExtra("addressDetail", binding.inputTradeLocationTv.text.toString())
            setResult(1, intent)
            finish()
        }
        binding.gpsBaseSettingFrame.setOnClickListener {
            finish()
        }
    }

    override fun initObserver() {

    }
}