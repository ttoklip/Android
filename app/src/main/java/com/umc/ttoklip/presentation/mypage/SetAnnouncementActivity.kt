package com.umc.ttoklip.presentation.mypage

import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivitySetAnnouncementBinding
import com.umc.ttoklip.presentation.base.BaseActivity

import com.umc.ttoklip.presentation.mypage.adapter.Announcement
import com.umc.ttoklip.presentation.mypage.adapter.AnnouncementAdapter
import com.umc.ttoklip.presentation.mypage.adapter.AnnouncementContent
import com.umc.ttoklip.presentation.mypage.adapter.AnnouncementContentDetail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SetAnnouncementActivity :
    BaseActivity<ActivitySetAnnouncementBinding>(R.layout.activity_set_announcement) {
    override fun initObserver() = Unit

    private val adapter by lazy {
        AnnouncementAdapter()
    }

    override fun initView() {
        val dummy = listOf(
            Announcement(
                "12/12 ~ 12/16 ", "똑리비들을 위한 새로운 버전 업데이트 1.0.1", listOf(
                    AnnouncementContent(
                        "일부 오류를 수정하였어요",
                        listOf(
                            AnnouncementContentDetail("일부 기기에서 댓글 달기를 하면 어플이 비정상적으로 종료되는 오류를 수정하였습니다."),
                            AnnouncementContentDetail("일부 기사를 로딩할 때, 걸리는 로딩 시간을 단축시켰습니다.")
                        )
                    )
                )
            )
        )
        binding.announcementsRv.adapter = adapter
        binding.announcementsRv.layoutManager = LinearLayoutManager(this)
        adapter.submitList(dummy)
        binding.setAnnouncementBackBtn.setOnClickListener {
            this.onBackPressedDispatcher.onBackPressed()
        }
    }
}