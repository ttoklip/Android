package com.umc.ttoklip.presentation.hometown.together.write.tradelocation

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.FragmentTradeLocationBinding
import com.umc.ttoklip.presentation.base.BaseFragment
import com.umc.ttoklip.presentation.hometown.adapter.OnRecentPlaceClickListener
import com.umc.ttoklip.presentation.hometown.adapter.RecentPlace
import com.umc.ttoklip.presentation.hometown.adapter.RecentlyUsedPlaceAdapter
import com.umc.ttoklip.presentation.hometown.together.write.WriteTogetherViewModel
import com.umc.ttoklip.presentation.hometown.together.write.WriteTogetherViewModelImpl
import com.umc.ttoklip.util.setOnSingleClickListener
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
    private val viewModel: WriteTogetherViewModel by activityViewModels<WriteTogetherViewModelImpl>()
    override fun initObserver() {

    }

    override fun initView() {
        binding.viewModel = viewModel
        binding.backBtn.setOnSingleClickListener {
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

        binding.inputTradeLocationTv.setOnSingleClickListener {
            navigator.navigate(R.id.action_tradeLocationFragment_to_placeFragment)
        }

        binding.tradeLocationFrame.setOnSingleClickListener{
            navigator.navigateUp()
        }
    }

    override fun onClick(items: RecentPlace) {

    }
}