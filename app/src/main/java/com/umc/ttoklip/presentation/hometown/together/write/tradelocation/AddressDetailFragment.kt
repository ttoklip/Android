package com.umc.ttoklip.presentation.hometown.together.write.tradelocation

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.FragmentAddresDetailBinding
import com.umc.ttoklip.presentation.base.BaseFragment
import com.umc.ttoklip.presentation.hometown.together.write.WriteTogetherViewModel
import com.umc.ttoklip.presentation.hometown.together.write.WriteTogetherViewModelImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddressDetailFragment: BaseFragment<FragmentAddresDetailBinding>(R.layout.fragment_addres_detail) {
    private val navigator by lazy {
        findNavController()
    }
    private val viewModel: WriteTogetherViewModel by activityViewModels<WriteTogetherViewModelImpl>()
    override fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.address.collect{
                    Log.d("address", it.toString())
                }
            }
        }
    }

    override fun initView() {
        binding.viewModel = viewModel

        binding.locationNextBtn.setOnClickListener {
            navigator.popBackStack(R.id.writeTogetherFragment, false)
        }
        binding.gpsBaseSettingFrame.setOnClickListener {
            navigator.navigateUp()
        }
    }
}