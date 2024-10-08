package com.umc.ttoklip.presentation.hometown.communication

import android.content.Intent
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.widget.AdapterView
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.umc.ttoklip.R
import com.umc.ttoklip.data.model.town.Communities
import com.umc.ttoklip.databinding.ActivityCommunicationBinding
import com.umc.ttoklip.presentation.alarm.AlarmActivity
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.hometown.adapter.CommunicationAdapter
import com.umc.ttoklip.presentation.hometown.adapter.OnItemClickListener
import com.umc.ttoklip.presentation.hometown.communication.read.ReadCommunicationActivity
import com.umc.ttoklip.presentation.hometown.communication.write.WriteCommunicationActivity
import com.umc.ttoklip.presentation.hometown.together.TogetherActivity
import com.umc.ttoklip.presentation.mypage.SortSpinnerAdapter
import com.umc.ttoklip.util.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CommunicationActivity :
    BaseActivity<ActivityCommunicationBinding>(R.layout.activity_communication),
    OnItemClickListener {
    private val rva by lazy {
        CommunicationAdapter(this,this)
    }
    private val viewModel: CommunicationViewModel by viewModels<CommunicationViewModelImpl>()
    override fun initView() {
        binding.vm = viewModel as CommunicationViewModelImpl
        binding.writeFab.setOnSingleClickListener {
            val intent = Intent(this, WriteCommunicationActivity::class.java)
            startActivity(intent)
        }
        val sortFilters = listOf(
            getString(R.string.sort_most_recent),
            getString(R.string.sort_popularity),
            getString(R.string.sort_most_comments),
            getString(R.string.sort_most_scrap)
        )
        binding.bellBtn.setOnSingleClickListener {
            startActivity(AlarmActivity.newIntent(this))
        }

        binding.honeyTipFilterSpinner.adapter =
            SortSpinnerAdapter(this, sortFilters)
        binding.honeyTipFilterSpinner.setSelection(0)


        binding.communicationRv.apply {
            adapter = adapter
            layoutManager = LinearLayoutManager(this@CommunicationActivity)
            adapter = rva
        }
        binding.communicationRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == RecyclerView.SCROLL_STATE_IDLE || newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val totalItemCount = layoutManager.itemCount
                    val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

                    if (totalItemCount - lastVisibleItemPosition <= 2) {
                        viewModel.getCommunities()
                    }
                }
            }
        })

        binding.backBtn.setOnSingleClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getMemberStreetInfo()
    }

    private fun initSpinner(list: List<String>){
        binding.honeyTipStreetSpinner.adapter=SortSpinnerAdapter(this, list)
        binding.honeyTipStreetSpinner.setSelection(0)
        binding.honeyTipStreetSpinner.onItemSelectedListener = object:
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.setCriteria(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }


    override fun initObserver() {
        with(lifecycleScope) {
            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.communities.collect {
                        rva.submitList(it)
                    }
                }
            }
            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.streetInfo.collect{ info ->
                        if (info.isNotEmpty()){
                            initSpinner(info)
                            Log.d("streetFilters", info.toString())
                        }
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