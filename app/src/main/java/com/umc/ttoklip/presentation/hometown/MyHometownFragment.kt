package com.umc.ttoklip.presentation.hometown

import android.content.Intent
import android.util.Log
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
import com.umc.ttoklip.presentation.hometown.adapter.Together
import com.umc.ttoklip.presentation.hometown.adapter.TogetherAdapter
import com.umc.ttoklip.presentation.mypage.MyHometownAddressActivity
import com.umc.ttoklip.presentation.search.SearchActivity
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
                viewModel.mainData.collect { re ->
                    togetherAdapter.submitList(re.cartRecent3.map { it.toModel() })
                    communicationAdapter.submitList(re.communityRecent3.map { it.toModel() })
                    binding.myHometownFilterTv.text = re.street
                }
            }
        }
    }

    override fun initView() {
        Log.d("jwt", TtoklipApplication.prefs.getString("jwt", ""))
        binding.seeDetailTogetherBtn.setOnClickListener {
            val intent = Intent(requireContext(), TogetherActivity::class.java)
            startActivity(intent)
        }
        viewModel.getM()

        binding.seeDetailCommunicationBtn.setOnClickListener {
            val intent = Intent(requireContext(), CommunicationActivity::class.java)
            startActivity(intent)
        }
        binding.bellBtn.setOnClickListener {
            startActivity(AlarmActivity.newIntent(requireContext()))
        }
        binding.searchBtn.setOnClickListener {
            startActivity(SearchActivity.newIntent(requireContext()))
        }
        binding.myHometownFilterTv.setOnClickListener {
            val intent = Intent(requireContext(), MyHometownAddressActivity::class.java)
            activityResultLauncher.launch(intent)
        }
        binding.writeTogetherBtn.setOnClickListener {
            val intent = Intent(requireContext(), WriteTogetherActivity::class.java)
            startActivity(intent)
        }
        binding.writeTogetherPlus.setOnClickListener {
            val intent = Intent(requireContext(), WriteTogetherActivity::class.java)
            startActivity(intent)
        }
        binding.writeCommunicationBtn.setOnClickListener {
            val intent = Intent(requireContext(), WriteCommunicationActivity::class.java)
            startActivity(intent)
        }
        binding.writeCommunicationPlus.setOnClickListener {
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

    override fun onClick(items: Together, type: String) {
        if (type == getString(R.string.together_title)) {
            val intent = Intent(requireContext(), ReadTogetherActivity::class.java)
            intent.putExtra("postId", items.id.toLong())
            startActivity(intent)
        } else {
            val intent = Intent(requireContext(), ReadCommunicationActivity::class.java)
            intent.putExtra("postId", items.id.toLong())
            startActivity(intent)
        }
    }
}