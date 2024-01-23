package com.umc.ttoklip.presentation.mypage

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivityManageUsageBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.mypage.adapter.BlockUser
import com.umc.ttoklip.presentation.mypage.adapter.BlockUserAdapter
import com.umc.ttoklip.presentation.mypage.adapter.Suspension
import com.umc.ttoklip.presentation.mypage.adapter.SuspensionAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManageUsageActivity :
    BaseActivity<ActivityManageUsageBinding>(R.layout.activity_manage_usage) {
    private val suspensionAdapter by lazy {
        SuspensionAdapter()
    }

    private val blockAdapter by lazy {
        BlockUserAdapter()
    }

    override fun initView() {
        val dummy = listOf(Suspension("이용 정지 5일", "부적절한 공구 참여로 이용이 정지되었습니다.", "12/12 ~ 12/16"))
        binding.suspensionRv.adapter = suspensionAdapter
        binding.suspensionRv.layoutManager = LinearLayoutManager(this)
        suspensionAdapter.submitList(dummy)

        val dummy2 = listOf(BlockUser(null, "똑리비"))
        binding.blockUserRv.adapter = blockAdapter
        binding.blockUserRv.layoutManager = LinearLayoutManager(this)
        blockAdapter.submitList(dummy2)

        binding.manageUsageBackBtn.setOnClickListener {
            this.onBackPressedDispatcher.onBackPressed()
        }

        binding.suspensionBtn.setOnClickListener {
            if (binding.suspensionRv.visibility == View.GONE) {
                binding.suspensionRv.visibility = View.VISIBLE
                binding.suspensionBtn.setImageResource(R.drawable.ic_arrow_up_24)
            } else {
                binding.suspensionRv.visibility = View.GONE
                binding.suspensionBtn.setImageResource(R.drawable.ic_arrow_down_24)
            }
        }

        binding.blockUserBtn.setOnClickListener {
            if (binding.blockUserRv.visibility == View.GONE) {
                binding.blockUserRv.visibility = View.VISIBLE
                binding.blockUserBtn.setImageResource(R.drawable.ic_arrow_up_24)
            } else {
                binding.blockUserRv.visibility = View.GONE
                binding.blockUserBtn.setImageResource(R.drawable.ic_arrow_down_24)
            }
        }
    }

    override fun initObserver() = Unit
}