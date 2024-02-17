package com.umc.ttoklip.presentation.honeytip.read

import android.content.Intent
import android.media.Image
import android.net.Uri
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
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
import com.umc.ttoklip.presentation.news.adapter.Comment
import com.umc.ttoklip.presentation.news.adapter.CommentRVA
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReadHoneyTipActivity :
    BaseActivity<ActivityReadHoneyTipBinding>(R.layout.activity_read_honey_tip),
    OnReadImageClickListener {
    private val viewModel: ReadHoneyTipViewModel by viewModels()

    private val commentRVA by lazy {
        CommentRVA()
    }

    private val imageAdapter: ReadImageRVA by lazy {
        ReadImageRVA(this, this@ReadHoneyTipActivity)
    }
    private var isShowMenu = false
    private var postId = 0
    private var category = ""

    override fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.readEvent.collect {
                    handleEvent(it)
                }
            }
        }
    }

    private fun handleEvent(event: ReadHoneyTipViewModel.ReadEvent) {
        when (event) {
            is ReadHoneyTipViewModel.ReadEvent.ReadHoneyTipEvent -> {
                binding.titleTv.text = event.inquireHoneyTipResponse.title
                binding.writerTv.text = event.inquireHoneyTipResponse.writer
                binding.contentT.text = event.inquireHoneyTipResponse.content
                binding.linkT.text = event.inquireHoneyTipResponse.urlResponses?.firstOrNull()?.urls
                imageAdapter.submitList(event.inquireHoneyTipResponse.imageUrls)
                category = event.inquireHoneyTipResponse.category
                val writer = TtoklipApplication.prefs.getString("nickname", "")
                if (event.inquireHoneyTipResponse.writer == writer) {
                    showHoneyTipWriterMenu()
                } else {
                    showReportBtn()
                }
            }

            else -> {
                Toast.makeText(this, "신고가 완료되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun initView() {
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
                Comment(1, 1, "ㅎㅇ", "ㅎ"),
                Comment(2, 2, "ㅎㅇ", "ㅎ"),
                Comment(3, 1, "ㅎㅇ", "ㅎ")
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

    /*private fun editHoneyTip() {
        binding.editBtn.setOnClickListener {
            val intent = Intent(this, WriteHoneyTipActivity::class.java)
            val intentImages =
                imageAdapter.currentList.filterIsInstance<Image>()?.map { it.uri.toString() }
                    ?.toTypedArray()
            val title = binding.titleTv.text.toString()
            val content = binding.contentT.text.toString()
            val url = binding.linkT.text.toString()
            val honeyTip = HoneyTip(title, content, intentImages, url, category)

            intent.putExtra("honeyTip", honeyTip)
            editDone(intent)
        }
    }*/

    /*private fun editQuestion() {
        binding.editBtn.setOnClickListener {
            val intent = Intent(this, WriteHoneyTipActivity::class.java)
            val intentImages =
                imageAdapter.currentList.filterIsInstance<Image>()?.map { it.uri.toString() }
                    ?.toTypedArray()
            val title = binding.titleTv.text.toString()
            val content = binding.contentT.text.toString()
            val question = Question(title, content, intentImages, category)
            intent.putExtra("question", question)
            editDone(intent)
        }
    }*/

    private fun showReportDialog() {
        binding.reportBtn.setOnClickListener {
            val reportDialog = ReportDialogFragment()
            reportDialog.setDialogClickListener(object : ReportDialogFragment.DialogClickListener {
                override fun onClick(request: ReportRequest) {
                    Log.d("report request", request.toString())
                    viewModel.reportHoneyTip(postId, request)
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