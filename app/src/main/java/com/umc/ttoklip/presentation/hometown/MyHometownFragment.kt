package com.umc.ttoklip.presentation.hometown

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.ttoklip.R
import com.umc.ttoklip.TtoklipApplication
import com.umc.ttoklip.databinding.FragmentMyHometownBinding
import com.umc.ttoklip.presentation.alarm.AlarmActivity
import com.umc.ttoklip.presentation.base.BaseFragment
import com.umc.ttoklip.presentation.hometown.adapter.OnTogetherClickListener
import com.umc.ttoklip.presentation.hometown.adapter.TogetherAdapter
import com.umc.ttoklip.presentation.hometown.communication.CommunicationActivity
import com.umc.ttoklip.presentation.hometown.communication.read.ReadCommunicationActivity
import com.umc.ttoklip.presentation.hometown.communication.write.WriteCommunicationActivity
import com.umc.ttoklip.presentation.hometown.together.read.ReadTogetherActivity
import com.umc.ttoklip.presentation.hometown.together.TogetherActivity
import com.umc.ttoklip.presentation.hometown.together.write.WriteTogetherActivity
import com.umc.ttoklip.presentation.mypage.manageinfo.MyHometownAddressActivity
import com.umc.ttoklip.presentation.search.SearchActivity
import com.umc.ttoklip.util.UiState
import com.umc.ttoklip.util.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyHometownFragment : BaseFragment<FragmentMyHometownBinding>(R.layout.fragment_my_hometown),
    OnTogetherClickListener {
    private val viewModel: MyHometownViewModel by viewModels<MyHometownViewModelImpl>()

    private val activityResultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val addressIntent = it.data
            addressIntent?.let { aIntent ->
                val address = aIntent.getStringExtra("address")
                address?.let { place ->
                    binding.myHometownFilterTv.text = address
                }
            }
        }
    private val togetherAdapter by lazy {
        TogetherAdapter(
            this,
            getString(R.string.together_title)
        )
    }
    private val communicationAdapter by lazy {
        TogetherAdapter(
            this,
            getString(R.string.communication_title)
        )
    }

    override fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.mainData.collect { state ->
                    when(state){
                        is UiState.Success -> {
                            with(state.data){
                                binding.errorLayout.visibility = View.GONE
                                binding.sv.visibility = View.VISIBLE
                                communityRecent3.forEach {
                                    if(it.street.isNullOrEmpty()){
                                        it.street = "수도권"
                                    }
                                }
                                togetherAdapter.submitList(cartRecent3.map { it.toModel() })
                                communicationAdapter.submitList(communityRecent3.map { it.toModel() })
                            }
                        }
                        else -> Unit
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.errorData.collect { message ->
                    if(message.isNotEmpty()){
                        binding.errorLayout.visibility = View.VISIBLE
                        binding.sv.visibility = View.GONE
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.streetInfo.collect { info ->
                    if (info.isNotEmpty()) {
                        binding.myHometownFilterTv.text = info
                    }
                }
            }
        }
    }

    override fun initView() {
        viewModel.getMemberStreetInfo()
        binding.seeDetailTogetherBtn.setOnSingleClickListener {
            val intent = Intent(requireContext(), TogetherActivity::class.java)
            startActivity(intent)
        }

        binding.seeDetailCommunicationBtn.setOnSingleClickListener {
            val intent = Intent(requireContext(), CommunicationActivity::class.java)
            startActivity(intent)
        }
        binding.bellBtn.setOnSingleClickListener {
            startActivity(AlarmActivity.newIntent(requireContext()))
        }
        binding.searchBtn.setOnSingleClickListener {
            startActivity(SearchActivity.newIntent(requireContext()))
        }
        binding.writeTogetherBtn.setOnSingleClickListener {
            val intent = Intent(requireContext(), WriteTogetherActivity::class.java)
            startActivity(intent)
        }
        binding.writeTogetherPlus.setOnSingleClickListener {
            val intent = Intent(requireContext(), WriteTogetherActivity::class.java)
            startActivity(intent)
        }
        binding.writeCommunicationBtn.setOnSingleClickListener {
            val intent = Intent(requireContext(), WriteCommunicationActivity::class.java)
            startActivity(intent)
        }
        binding.writeCommunicationPlus.setOnSingleClickListener {
            val intent = Intent(requireContext(), WriteCommunicationActivity::class.java)
            startActivity(intent)
        }
        initTogetherRv()
        initCommunicationRv()
    }

    private fun initCommunicationRv() {


        binding.communicationRv.apply {
            adapter = communicationAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

    }

    private fun initTogetherRv() {


        binding.togetherRv.apply {
            adapter = togetherAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

    }

    override fun onClick(items: Long, type: String) {
        if (type == getString(R.string.together_title)) {
            val intent = Intent(requireContext(), ReadTogetherActivity::class.java)
            intent.putExtra("postId", items)
            startActivity(intent)
        } else {
            val intent = Intent(requireContext(), ReadCommunicationActivity::class.java)
            intent.putExtra("postId", items)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getM()
    }
}