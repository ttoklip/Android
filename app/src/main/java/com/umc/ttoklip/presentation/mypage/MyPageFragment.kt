package com.umc.ttoklip.presentation.mypage

import android.content.Intent
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.FragmentMyPageBinding
import com.umc.ttoklip.presentation.alarm.AlarmActivity
import com.umc.ttoklip.presentation.base.BaseFragment
import com.umc.ttoklip.presentation.mypage.vm.ManageMyInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {
    private val viewModel: ManageMyInfoViewModel by viewModels()
    override fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.myPageInfo.collect{
                    binding.addressTv.text = it.street ?: "주소"
                    binding.nicknameTv.text = it.nickname
                    Glide.with(this@MyPageFragment)
                        .load(it.profileImage)
                        .into(binding.profileImg)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d("resume", "resume")
    }

    override fun initView() {
        viewModel.getMyPageInfo()
        binding.userExpBar.isEnabled = false
        binding.noticeBtn.setOnClickListener {
            startActivity(AlarmActivity.newIntent(requireContext()))
        }
        binding.updateProfileBtn.setOnClickListener {
            val intent = Intent(requireContext(), ManageMyInfoActivity::class.java)
            startActivity(intent)
        }

        binding.announcementFrame.setOnClickListener {
            val intent = Intent(requireContext(), SetAnnouncementActivity::class.java)
            startActivity(intent)
        }

        binding.usageManageFrame.setOnClickListener {
            val intent = Intent(requireContext(), ManageUsageActivity::class.java)
            startActivity(intent)
        }

        binding.manageAccountFrame.setOnClickListener {
            val intent = Intent(requireContext(), ManageAccountActivity::class.java)
            startActivity(intent)
        }

        binding.noticeFrame.setOnClickListener {
            val intent = Intent(requireContext(), NoticeSettingActivity::class.java)
            startActivity(intent)
        }

        binding.customerServiceCenterFrame.setOnClickListener {
            val intent = Intent(requireContext(), CustomerServiceCenterActivity::class.java)
            startActivity(intent)
        }

        binding.termsPolicesFrame.setOnClickListener {
            val intent = Intent(requireContext(), TermsPolicesActivity::class.java)
            startActivity(intent)
        }

        binding.logoutFrame.setOnClickListener {
//            val dialog = LogoutDialog()
//            dialog.show(parentFragmentManager, dialog.tag)
        }

        binding.transactionHistoryBtn.setOnClickListener {
            val intent = Intent(requireContext(), TransactionHistoryActivity::class.java)
            startActivity(intent)
        }

        binding.scrapBtn.setOnClickListener {
            val intent = Intent(requireContext(), SavedHoneyTipActivity::class.java)
            startActivity(intent)
        }

        binding.myHoneyTipBtn.setOnClickListener {
            val intent = Intent(requireContext(), MyHoneyTipActivity::class.java)
            startActivity(intent)
        }
    }
}