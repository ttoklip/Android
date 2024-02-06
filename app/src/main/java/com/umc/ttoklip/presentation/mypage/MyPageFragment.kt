package com.umc.ttoklip.presentation.mypage

import android.content.Intent
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.FragmentMyPageBinding
import com.umc.ttoklip.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {
    override fun initObserver() = Unit

    override fun initView() {
        binding.updateProfileBtn.setOnClickListener {
            val intent = Intent(requireContext(), ManageMyInfoActivity::class.java)
            startActivity(intent)
        }

        binding.announcementBtn.setOnClickListener {
            val intent = Intent(requireContext(), SetAnnouncementActivity::class.java)
            startActivity(intent)
        }

        binding.usageManageBtn.setOnClickListener {
            val intent = Intent(requireContext(), ManageUsageActivity::class.java)
            startActivity(intent)
        }

        binding.manageAccountBtn.setOnClickListener {
            val intent = Intent(requireContext(), ManageAccountActivity::class.java)
            startActivity(intent)
        }
    }

}