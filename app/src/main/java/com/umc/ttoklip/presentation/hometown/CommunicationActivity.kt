package com.umc.ttoklip.presentation.hometown

import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.ttoklip.R
import com.umc.ttoklip.data.model.town.Communities
import com.umc.ttoklip.databinding.ActivityCommunicationBinding
import com.umc.ttoklip.presentation.alarm.AlarmActivity
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.hometown.adapter.CommunicationAdapter
import com.umc.ttoklip.presentation.hometown.adapter.OnItemClickListener
import com.umc.ttoklip.presentation.mypage.SortSpinnerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CommunicationActivity :
    BaseActivity<ActivityCommunicationBinding>(R.layout.activity_communication),
    OnItemClickListener {
    private val adapter by lazy {
        CommunicationAdapter(this)
    }
    private val viewModel: CommunicationViewModel by viewModels<CommunicationViewModelImpl>()
    override fun initView() {
        binding.vm = viewModel as CommunicationViewModelImpl
        binding.writeFab.setOnClickListener {
            val intent = Intent(this, WriteCommunicationActivity::class.java)
            startActivity(intent)
        }
        val sortFilters = listOf(
            getString(R.string.sort_most_recent),
            getString(R.string.sort_popularity),
            getString(R.string.sort_most_comments),
            getString(R.string.sort_most_scrap)
        )
        binding.bellBtn.setOnClickListener {
            startActivity(AlarmActivity.newIntent(this))
        }
        binding.honeyTipFilterSpinner.adapter =
            SortSpinnerAdapter(this, sortFilters)
        binding.honeyTipFilterSpinner.setSelection(0)



        binding.communicationRv.layoutManager = LinearLayoutManager(this)
        binding.communicationRv.adapter = adapter

        binding.backBtn.setOnClickListener {
            finish()
        }
        viewModel.getCommunities()
    }

    override fun initObserver() {
        with(lifecycleScope) {
            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.communities.collect {
                        adapter.submitList(it)
                    }
                }
            }
        }
    }

    override fun onClick(communication: Communities) {
        val intent = Intent(this, ReadCommunicationActivity::class.java)
        intent.putExtra("postId", communication.id)
        startActivity(intent)
    }

}