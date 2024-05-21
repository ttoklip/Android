package com.umc.ttoklip.presentation.mypage

import android.content.Intent
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.umc.ttoklip.R
import com.umc.ttoklip.TtoklipApplication
import com.umc.ttoklip.databinding.FragmentMyPageBinding
import com.umc.ttoklip.presentation.MainActivity
import com.umc.ttoklip.presentation.alarm.AlarmActivity
import com.umc.ttoklip.presentation.base.BaseFragment
import com.umc.ttoklip.presentation.login.LoginActivity
import com.umc.ttoklip.presentation.mypage.dialog.LogoutDialog
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
                    Log.d("mypage", it.toString())
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
        viewModel.getMyPageInfo()
    }

    override fun initView() {
        //viewModel.getMyPageInfo()
        binding.userExpBar.isEnabled = false
        binding.noticeBtn.setOnClickListener {
            startActivity(AlarmActivity.newIntent(requireContext()))
        }
        binding.updateProfileBtn.setOnClickListener { //카테고리 받아와야 제대로 업데이트 가능
            val intent = Intent(requireContext(), ManageMyInfoActivity::class.java)
            startActivity(intent)
        }

        binding.manageAccountFrame.setOnClickListener { //계정정보 화면 새로 만들 필요 있음
            val intent = Intent(requireContext(), ManageMyInfoActivity::class.java)
            startActivity(intent)
        }

        binding.announcementFrame.setOnClickListener { //공지사항 두 개만 넣어달라, api 연결은 해놨음
            val intent = Intent(requireContext(), SetAnnouncementActivity::class.java)
            startActivity(intent)
        }

        binding.usageManageFrame.setOnClickListener { //api 연결은 해놨음
            val intent = Intent(requireContext(), ManageUsageActivity::class.java)
            startActivity(intent)
        }

        binding.customerServiceCenterFrame.setOnClickListener { //고객센터-faq 불러오기 연동 필요, 1:1 문의 보내기 필요
            val intent = Intent(requireContext(), CustomerServiceCenterActivity::class.java)
            startActivity(intent)
        }

        binding.noticeFrame.setOnClickListener {//알림 설정-알림이 되어야함
            val intent = Intent(requireContext(), NoticeSettingActivity::class.java)
            startActivity(intent)
        }

        binding.termsPolicesFrame.setOnClickListener {//약관 및 정책
            val intent = Intent(requireContext(), TermsPolicesActivity::class.java)
            startActivity(intent)
        }

        binding.logoutFrame.setOnClickListener {
            //로그아웃 다이얼로그 체크
            val dialog = LogoutDialog(logout = {
                TtoklipApplication.prefs.removeString("jwt")
                startActivity(Intent(activity,LoginActivity::class.java))
                activity?.finish()
            })
            dialog.show(parentFragmentManager, dialog.tag)
        }

        binding.transactionHistoryBtn.setOnClickListener { //거래내역
            startActivity(TransactionHistoryActivity.newIntent(requireContext(),1,""))
        }

        binding.scrapBtn.setOnClickListener { //스크랩
            val intent = Intent(requireContext(), SavedHoneyTipActivity::class.java)
            startActivity(intent)
        }

        binding.myHoneyTipBtn.setOnClickListener { //꿀팁
            val intent = Intent(requireContext(), MyHoneyTipActivity::class.java)
            startActivity(intent)
        }
    }
}