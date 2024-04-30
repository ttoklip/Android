package com.umc.ttoklip.presentation.hometown

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivityTradeLocationBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.hometown.adapter.OnRecentPlaceClickListener
import com.umc.ttoklip.presentation.hometown.adapter.RecentPlace
import com.umc.ttoklip.presentation.hometown.adapter.RecentlyUsedPlaceAdapter
import com.umc.ttoklip.presentation.hometown.together.write.WriteTogetherActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TradeLocationActivity :
    BaseActivity<ActivityTradeLocationBinding>(R.layout.activity_trade_location),
    OnRecentPlaceClickListener {
    private val adapter by lazy {
        RecentlyUsedPlaceAdapter(this)
    }
    private val activityResultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val addressIntent = it.data
            addressIntent?.let { aIntent ->
                val address = aIntent.getStringExtra("address")
                val addressDetail = aIntent.getStringExtra("addressDetail")
                address?.let { place ->
                    binding.tradeLocationTv.text = place
                    addressDetail?.let { detail ->
                        binding.tradeLocationDetailTv.text = detail
                    }
                }

            }
        }

    override fun initView() {
        binding.backBtn.setOnClickListener {
            val intent = Intent(applicationContext, WriteTogetherActivity::class.java)
            intent.putExtra("address", binding.tradeLocationTv.text.toString())
            intent.putExtra("addressDetail", binding.tradeLocationDetailTv.text.toString())
            setResult(1, intent)
            finish()
        }
        val places = listOf(
            RecentPlace("부산광역시 동래구 금강공원로 2", "SK HUB Olive 1203호"),
            RecentPlace("부산광역시 동래구 금강공원로 2", "SK HUB Olive 1203호"),
            RecentPlace("부산광역시 동래구 금강공원로 2", "SK HUB Olive 1203호")
        )
        binding.recentlyUsedPlacesRv.adapter = adapter
        binding.recentlyUsedPlacesRv.layoutManager = LinearLayoutManager(this)
        adapter.submitList(places)

        binding.gpsBaseSettingFrame.setOnClickListener {
            val intent = Intent(this, PlaceActivity::class.java)
            activityResultLauncher.launch(intent)
        }
    }

    override fun initObserver() {

    }

    override fun onClick(items: RecentPlace) {

    }
}