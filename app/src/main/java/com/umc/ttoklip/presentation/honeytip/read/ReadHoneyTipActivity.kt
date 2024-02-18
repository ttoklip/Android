package com.umc.ttoklip.presentation.honeytip.read

import android.content.Intent
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.umc.ttoklip.R
import com.umc.ttoklip.TtoklipApplication
import com.umc.ttoklip.data.model.honeytip.EditHoneyTip
import com.umc.ttoklip.data.model.honeytip.ImageUrl
import com.umc.ttoklip.data.model.honeytip.request.ReportRequest
import com.umc.ttoklip.databinding.ActivityReadHoneyTipBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.honeytip.BOARD
import com.umc.ttoklip.presentation.honeytip.HONEY_TIP
import com.umc.ttoklip.presentation.honeytip.ImageViewActivity
import com.umc.ttoklip.presentation.honeytip.adapter.OnReadImageClickListener
import com.umc.ttoklip.presentation.honeytip.adapter.ReadImageRVA
import com.umc.ttoklip.presentation.honeytip.dialog.DeleteDialogFragment
import com.umc.ttoklip.presentation.honeytip.dialog.ReportDialogFragment
import com.umc.ttoklip.presentation.honeytip.write.WriteHoneyTipActivity
import com.umc.ttoklip.presentation.news.adapter.CommentRVA
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReadHoneyTipActivity :
    BaseActivity<ActivityReadHoneyTipBinding>(R.layout.activity_read_honey_tip),
    OnReadImageClickListener {
    private val viewModel: ReadHoneyTipViewModel by viewModels()

    private val commentRVA by lazy {
        CommentRVA({},{_,_->})
    }

    private val imageAdapter: ReadImageRVA by lazy {
        ReadImageRVA(this, this@ReadHoneyTipActivity)
    }
    private var isShowMenu = false

    // 수정 시 필요
    private var postId = 0
    private var category = ""

    override fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.readEvent.collect {
                    handleReadEvent(it)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.menuEvent.collect {
                    handleMenuEvent(it)
                }
            }
        }
    }

    private fun handleReadEvent(event: ReadHoneyTipViewModel.ReadEvent) {
        when (event) {
            is ReadHoneyTipViewModel.ReadEvent.ReadHoneyTipEvent -> {
                val honeyTip = event.inquireHoneyTipResponse
                with(binding){
                    titleTv.text = honeyTip.title
                    writerTv.text = honeyTip.writer
                    contentT.text = honeyTip.content
                    linkT.text = honeyTip.urlResponses?.firstOrNull()?.urls
                    likeT.text = honeyTip.likeCount.toString()
                    bookmarkT.text = honeyTip.scrapCount.toString()
                    commitT.text = honeyTip.commentCount.toString()
                }
                imageAdapter.submitList(honeyTip.imageUrls)
                category = honeyTip.category
                val writer = TtoklipApplication.prefs.getString("nickname", "")
                if (honeyTip.writer == writer) {
                    showHoneyTipWriterMenu()
                } else {
                    showReportBtn()
                }
            }
            else -> {}
        }
    }

    private fun handleMenuEvent(event: ReadHoneyTipViewModel.MenuEvent){
        val toastText =
        when(event) {
            ReadHoneyTipViewModel.MenuEvent.DeleteLike -> "좋아요 취소"
            ReadHoneyTipViewModel.MenuEvent.DeleteScrap -> "스크랩 취소"
            ReadHoneyTipViewModel.MenuEvent.PostLike -> "좋아요"
            ReadHoneyTipViewModel.MenuEvent.PostScrap -> "스크랩"
            ReadHoneyTipViewModel.MenuEvent.ReportHoneyTip -> "해당 게시글에 대한 신고가 접수되었습니다."
            else -> {}
        }
        Toast.makeText(this, "$toastText", Toast.LENGTH_SHORT)
    }

    override fun initView() {
        binding.vm = viewModel
        postId = intent.getIntExtra("postId", 0)
        Log.d("read postid", postId.toString())
        binding.boardTitleTv.text = "꿀팁 공유하기"
        viewModel.inquireHoneyTip(postId)

        binding.backBtn.setOnClickListener {
            finish()
        }

        initImageRVA()
        showDeleteDialog()
        showReportDialog()
        editHoneyTip()

        binding.commentRv.adapter = commentRVA
        commentRVA.submitList(
            listOf(
            )
        )
    }

    private fun initImageRVA() {
        binding.imageRv.adapter = imageAdapter
    }

    private fun showReportBtn() {
        binding.dotBtn.setOnClickListener {
            if (!isShowMenu) {
                binding.reportBtn.bringToFront()
                binding.reportBtn.visibility = View.VISIBLE
                isShowMenu = true
            } else {
                binding.reportBtn.visibility = View.GONE
                isShowMenu = false
            }
        }
    }

    private fun showHoneyTipWriterMenu() {
        binding.dotBtn.setOnClickListener {
            if (!isShowMenu) {
                binding.honeyTipMenu.bringToFront()
                binding.honeyTipMenu.visibility = View.VISIBLE
                isShowMenu = true
            } else {
                binding.honeyTipMenu.visibility = View.GONE
                isShowMenu = false
            }
        }
    }

    private fun editHoneyTip() {
        binding.editBtn.setOnClickListener {
            val intent = Intent(this, WriteHoneyTipActivity::class.java)
            intent.putExtra("isEdit", true)
            intent.putExtra(BOARD, HONEY_TIP)
            val images = imageAdapter.currentList.filterIsInstance<ImageUrl>()?.map { it.imageUrl }
                ?.toTypedArray()
            val editHoneyTip = EditHoneyTip(
                postId,
                binding.titleTv.text.toString(),
                binding.contentT.text.toString(),
                category,
                images ?: emptyArray(),
                binding.linkT.text.toString()
            )
            intent.putExtra("honeyTip", editHoneyTip)
            startActivity(intent)
            finish()
        }
    }

    private fun showReportDialog() {
        binding.reportBtn.setOnClickListener {
            val reportDialog = ReportDialogFragment()
            reportDialog.setDialogClickListener(object : ReportDialogFragment.DialogClickListener {

                override fun onClick(type: String, content: String) {
                    viewModel.reportHoneyTip(postId,ReportRequest( content = content, reportType = type))
                }
            })
            reportDialog.show(supportFragmentManager, reportDialog.tag)
        }
    }

    private fun showDeleteDialog() {
        binding.deleteBtn.setOnClickListener {
            val deleteDialog = DeleteDialogFragment()
            deleteDialog.setDialogClickListener(object : DeleteDialogFragment.DialogClickListener {
                override fun onClick() {
                    viewModel.deleteHoneyTip(postId)
                    finish()
                }
            })
            deleteDialog.show(supportFragmentManager, deleteDialog.tag)
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (isShowMenu && !isTouchInside(binding.dotBtn, ev?.x!!, ev?.y!!)) {
            if (!isTouchInside(binding.honeyTipMenu, ev?.x!!, ev?.y!!)) {
                binding.honeyTipMenu.visibility = View.GONE
                isShowMenu = false
            }
        }

        if (isShowMenu && !isTouchInside(binding.dotBtn, ev?.x!!, ev?.y!!)) {
            if (!isTouchInside(binding.reportBtn, ev?.x!!, ev?.y!!)) {
                binding.reportBtn.visibility = View.GONE
                isShowMenu = false
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun isTouchInside(view: View, x: Float, y: Float): Boolean {
        val location = IntArray(2)
        view.getLocationOnScreen(location)
        val realRight = location[0] + view.width
        val realBottom = location[1] + view.height
        return x >= location[0] && x <= realRight && y >= location[1] && y <= realBottom
    }

    override fun onClick(imageUrl: String) {
        val images = imageAdapter.currentList.filterIsInstance<ImageUrl>().map { it.imageUrl }
            .toTypedArray()
        Log.d("images", images.toString())
        val intent = Intent(this, ImageViewActivity::class.java)
        intent.putExtra("images", images)
        startActivity(intent)
    }
}