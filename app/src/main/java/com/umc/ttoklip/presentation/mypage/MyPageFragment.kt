package com.umc.ttoklip.presentation.mypage

import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.umc.ttoklip.R
import com.umc.ttoklip.TtoklipApplication
import com.umc.ttoklip.databinding.FragmentMyPageBinding
import com.umc.ttoklip.presentation.alarm.AlarmActivity
import com.umc.ttoklip.presentation.base.BaseFragment
import com.umc.ttoklip.presentation.login.LoginActivity
import com.umc.ttoklip.presentation.mypage.dialog.LogoutDialog
import com.umc.ttoklip.presentation.mypage.manageinfo.ManageMyInfoActivity
import com.umc.ttoklip.presentation.mypage.manageinfo.ManageMyInfoViewModel
import com.umc.ttoklip.util.setOnSingleClickListener
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
                    Log.d("profile image", it.profileImgUrl.toString())
                    Glide.with(this@MyPageFragment)
                        .load(it.profileImgUrl)
                        .placeholder(R.drawable.ic_defeault_logo)
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
        //binding.userExpBar.isEnabled = false
        binding.noticeBtn.setOnSingleClickListener {
            startActivity(AlarmActivity.newIntent(requireContext()))
        }
        binding.updateProfileBtn.setOnSingleClickListener { //카테고리 받아와야 제대로 업데이트 가능
            val intent = Intent(requireContext(), ManageMyInfoActivity::class.java)
            startActivity(intent)
        }

//        binding.manageAccountFrame.setOnClickListener { //계정정보 화면 새로 만들 필요 있음
//            val intent = Intent(requireContext(), AccountInfoActivity::class.java)
//            startActivity(intent)
//        }

        binding.announcementFrame.setOnSingleClickListener { //공지사항 두 개만 넣어달라, api 연결은 해놨음
            val intent = Intent(requireContext(), SetAnnouncementActivity::class.java)
            startActivity(intent)
        }

//        binding.usageManageFrame.setOnClickListener { //api 연결은 해놨음
//            val intent = Intent(requireContext(), ManageUsageActivity::class.java)
//            startActivity(intent)
//        }

        binding.customerServiceCenterFrame.setOnSingleClickListener { //고객센터-faq 불러오기 연동 필요, 1:1 문의 보내기 필요
            val intent = Intent(requireContext(), CustomerServiceCenterActivity::class.java)
            startActivity(intent)
        }

        binding.noticeFrame.setOnSingleClickListener {//알림 설정-알림이 되어야함
            goNotificationIntent()

            //1.0.7
//            val intent = Intent(requireContext(), NoticeSettingActivity::class.java)
//            startActivity(intent)
        }

        binding.termsPolicesFrame.setOnSingleClickListener {//약관 및 정책
            val intent = Intent(requireContext(), TermsPolicesActivity::class.java)
            startActivity(intent)
        }

        binding.logoutFrame.setOnSingleClickListener {
            //로그아웃 다이얼로그 체크
            val dialog = LogoutDialog(logout = {
                TtoklipApplication.prefs.removeString("jwt")
                startActivity(Intent(activity,LoginActivity::class.java))
                activity?.finish()
            })
            dialog.show(parentFragmentManager, dialog.tag)
        }

        binding.transactionHistoryBtn.setOnSingleClickListener { //거래내역
            startActivity(TransactionHistoryActivity.newIntent(requireContext(),1,""))
        }

        binding.scrapBtn.setOnSingleClickListener { //스크랩
            val intent = Intent(requireContext(), SavedHoneyTipActivity::class.java)
            startActivity(intent)
        }

        binding.myHoneyTipBtn.setOnSingleClickListener { //꿀팁
            val intent = Intent(requireContext(), MyHoneyTipActivity::class.java)
            startActivity(intent)
        }
    }


    private fun goNotificationIntent() {
        val notificationIntent = Intent()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationIntent.action = android.provider.Settings.ACTION_APP_NOTIFICATION_SETTINGS
            notificationIntent.putExtra(android.provider.Settings.EXTRA_APP_PACKAGE, requireContext().packageName)
        } else {
            notificationIntent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
            notificationIntent.putExtra("app_package", requireContext().packageName)
            notificationIntent.putExtra("app_uid", requireContext().applicationInfo.uid)
        }
        startActivity(notificationIntent)
    }
}