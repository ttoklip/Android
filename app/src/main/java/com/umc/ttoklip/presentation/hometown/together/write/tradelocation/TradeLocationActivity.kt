package com.umc.ttoklip.presentation.hometown.together.write.tradelocation

import android.content.Intent
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivityTradeLocationBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.hometown.PlaceActivity
import com.umc.ttoklip.presentation.hometown.adapter.OnRecentPlaceClickListener
import com.umc.ttoklip.presentation.hometown.adapter.RecentPlace
import com.umc.ttoklip.presentation.hometown.adapter.RecentlyUsedPlaceAdapter
import com.umc.ttoklip.presentation.hometown.together.write.WriteTogetherActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TradeLocationActivity :
    BaseActivity<ActivityTradeLocationBinding>(R.layout.activity_trade_location),
    OnRecentPlaceClickListener {
        private lateinit var navController: NavController
    /*private val adapter by lazy {
        RecentlyUsedPlaceAdapter(this)
    }
    private val activityResultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val addressIntent = it.data
            addressIntent?.let { aIntent ->
                val address = aIntent.getStringExtra("address")
                val addressDetail = aIntent.getStringExtra("addressDetail")
                address?.let { place ->
                    binding.tradeLocationFrame.visibility = View.VISIBLE
                    binding.tradeLocationTv.text = place
                    addressDetail?.let { detail ->
                        binding.tradeLocationDetailTv.text = detail
                    }
                }

            }
        }*/

    override fun initView() {
        initNavigator()
    }

    private fun initNavigator(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    override fun initObserver() {

    }

    override fun onClick(items: RecentPlace) {

    }
}