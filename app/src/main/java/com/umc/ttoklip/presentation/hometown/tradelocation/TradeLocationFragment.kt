package com.umc.ttoklip.presentation.hometown.tradelocation

import android.content.Intent
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.FragmentTradeLocationBinding
import com.umc.ttoklip.presentation.base.BaseFragment
import com.umc.ttoklip.presentation.hometown.PlaceActivity
import com.umc.ttoklip.presentation.hometown.adapter.OnRecentPlaceClickListener
import com.umc.ttoklip.presentation.hometown.adapter.RecentPlace
import com.umc.ttoklip.presentation.hometown.adapter.RecentlyUsedPlaceAdapter
import com.umc.ttoklip.presentation.hometown.together.write.WriteTogetherActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TradeLocationFragment: BaseFragment<FragmentTradeLocationBinding>(R.layout.fragment_trade_location),
    OnRecentPlaceClickListener {
    private val adapter by lazy {
        RecentlyUsedPlaceAdapter(this)
    }
    private val navigator by lazy {
        findNavController()
    }
    override fun initObserver() {

    }

    override fun initView() {
        binding.backBtn.setOnClickListener {
            val intent = Intent(requireContext(), WriteTogetherActivity::class.java)
            intent.putExtra("address", binding.tradeLocationTv.text.toString())
            intent.putExtra("addressDetail", binding.tradeLocationDetailTv.text.toString())
            //setResult(1, intent)
            navigator.navigateUp()
        }
        val places = listOf(
            RecentPlace("부산광역시 동래구 금강공원로 2", "SK HUB Olive 1203호"),
            RecentPlace("부산광역시 동래구 금강공원로 2", "SK HUB Olive 1203호"),
            RecentPlace("부산광역시 동래구 금강공원로 2", "SK HUB Olive 1203호")
        )
        binding.recentlyUsedPlacesRv.adapter = adapter
        binding.recentlyUsedPlacesRv.layoutManager = LinearLayoutManager(requireContext())
        adapter.submitList(places)

        binding.inputTradeLocationTv.setOnClickListener {
            navigator.navigate(R.id.action_tradeLocationFragment_to_placeFragment)
            //val intent = Intent(this, PlaceActivity::class.java)
            //activityResultLauncher.launch(intent)
        }
    }

    override fun onClick(items: RecentPlace) {

    }
}