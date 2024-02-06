package com.umc.ttoklip.presentation.hometown

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivityTradeLocationBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.hometown.adapter.OnRecentPlaceClickListener
import com.umc.ttoklip.presentation.hometown.adapter.RecentPlace
import com.umc.ttoklip.presentation.hometown.adapter.RecentlyUsedPlaceAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TradeLocationActivity :
    BaseActivity<ActivityTradeLocationBinding>(R.layout.activity_trade_location),
    OnRecentPlaceClickListener {
    private val adapter by lazy {
        RecentlyUsedPlaceAdapter(this)
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
    }

    override fun initObserver() {

    }

    override fun onClick(items: RecentPlace) {

    }
}