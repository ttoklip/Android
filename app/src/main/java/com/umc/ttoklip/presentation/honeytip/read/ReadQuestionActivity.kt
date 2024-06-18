package com.umc.ttoklip.presentation.honeytip.read

import android.content.Context
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
import com.umc.ttoklip.data.model.honeytip.ImageUrl
import com.umc.ttoklip.data.model.honeytip.request.ReportRequest
import com.umc.ttoklip.data.model.news.comment.NewsCommentResponse
import com.umc.ttoklip.databinding.ActivityReadQuestionBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.honeytip.ImageViewActivity
import com.umc.ttoklip.presentation.honeytip.adapter.OnReadImageClickListener
import com.umc.ttoklip.presentation.honeytip.adapter.QuestionCommentRVA
import com.umc.ttoklip.presentation.honeytip.adapter.ReadImageRVA
import com.umc.ttoklip.presentation.dialog.DeleteDialogFragment
import com.umc.ttoklip.presentation.dialog.ReportDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReadQuestionActivity : BaseActivity<ActivityReadQuestionBinding>(R.layout.activity_read_question),
    OnReadImageClickListener {
    private val viewModel: ReadHoneyTipViewModel by viewModels()

    private val commentRVA by lazy {
        QuestionCommentRVA(this, { id ->
            viewModel.replyCommentParentId.value = id
        }, { id, myComment ->
            if (myComment) {
                val deleteDialog = DeleteDialogFragment()
                deleteDialog.setDialogClickListener(object :
                    DeleteDialogFragment.DialogClickListener {
                    override fun onClick() {
                        viewModel.deleteQuestionComment(
                            id,
                            postId
                        )
                    }
                })
                deleteDialog.show(supportFragmentManager, deleteDialog.tag)
            } else {
                val reportDialog = ReportDialogFragment()
                reportDialog.setDialogClickListener(object :
                    ReportDialogFragment.DialogClickListener {
                    override fun onClick(type: String, content: String) {
                        viewModel.postReportQuestionComment(
                            postId,
                            ReportRequest(
                                content = content,
                                reportType = type
                            )
                        )
                    }
                })
                reportDialog.show(supportFragmentManager, reportDialog.tag)
            }
        },
            {id, myComment ->
                if (myComment){
                    viewModel.likeQuestionComment(id)
                }
            })
    }

    private val imageAdapter: ReadImageRVA by lazy {
        ReadImageRVA(this, this@ReadQuestionActivity)
    }
    private var isShowMenu = false
    private var postId = 0

    override fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.readEvent.collect{
                    handleEvent(it)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.menuEvent.collect{
                    handleMenuEvent(it)
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.comments.collect {
                    val list = it.map { it -> NewsCommentResponse(it.commentContent?:"", it.commentId, it.parentId, it.writer, it.writtenTime) }
                    commentRVA.submitList(list)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.replyCommentParentId.collect { id ->
                    if (id == 0) {
                        binding.replyT.text = ""
                    } else {
                        binding.replyT.text = "@${id}"
                    }
                }
            }
        }
    }

    private fun handleEvent(event: ReadHoneyTipViewModel.ReadEvent){
        when(event){
            is ReadHoneyTipViewModel.ReadEvent.ReadQuestionEvent -> {
                val question = event.inquireQuestionResponse
                with(binding) {
                    titleTv.text = question.title
                    writerTv.text = question.writer
                    contentT.text = question.content
                    commitT.text = question.commentCount.toString()
                }
                if (question.imageUrls.isNotEmpty()){
                    binding.imageRv.visibility = View.VISIBLE
                    imageAdapter.submitList(question.imageUrls)
                }
                val writer = TtoklipApplication.prefs.getString("nickname", "")
                if(question.writer != writer){
                    showReportBtn()
                }
            }
           else -> {}
        }
    }

    private fun handleMenuEvent(event: ReadHoneyTipViewModel.MenuEvent){
        when(event){
            ReadHoneyTipViewModel.MenuEvent.ReportQuestion ->
            Toast.makeText(this, "해당 게시글에 대한 신고가 접수되었습니다.", Toast.LENGTH_SHORT).show()
            ReadHoneyTipViewModel.MenuEvent.DeleteQuestionComment -> "댓글이 삭제되었습니다."
            ReadHoneyTipViewModel.MenuEvent.ReportQuestion -> "해당 댓글에 대한 신고가 접수되었습니다."
            else -> {}
        }
    }
    override fun initView() {
        binding.vm = viewModel
        postId = intent.getIntExtra("postId", 0)
        Log.d("read postid", postId.toString())
        binding.replyT.setOnClickListener {
            viewModel.replyCommentParentId.value = 0
        }
        binding.commentRv.adapter = commentRVA
        binding.SendCardView.setOnClickListener {
            viewModel.postQuestionComment(postId)
            binding.commentEt.setText("")
            viewModel.replyCommentParentId.value = 0
        }
        viewModel.inquireQuestion(postId)

        binding.backBtn.setOnClickListener {
            finish()
        }

        initImageRVA()
        //showDeleteDialog()
        showReportDialog()
    }
    private fun initImageRVA() {
        binding.imageRv.adapter = imageAdapter
    }
    private fun showReportBtn(){
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
    private fun showReportDialog() {
        binding.reportBtn.setOnClickListener {
            val reportDialog = ReportDialogFragment()
            reportDialog.setDialogClickListener(object : ReportDialogFragment.DialogClickListener {


                override fun onClick(type: String, content: String) {
                    Log.d("report request", content.toString())
                    viewModel.reportQuestion(postId, ReportRequest(content = content, reportType = type))
                }
            })
            reportDialog.show(supportFragmentManager, reportDialog.tag)
        }
    }

 /*   private fun showDeleteDialog() {
        binding.deleteBtn.setOnClickListener {
            val deleteDialog = DeleteDialogFragment()
            deleteDialog.setDialogClickListener(object : DeleteDialogFragment.DialogClickListener {
                override fun onClick() {
                    finish()
                }
            })
            deleteDialog.show(supportFragmentManager, deleteDialog.tag)
        }
    }*/

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
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

    companion object {
        const val QUESTION = "postId"
        fun newIntent(context: Context, id: Int) =
            Intent(context, ReadHoneyTipActivity::class.java).apply {
                putExtra(QUESTION, id)
            }
    }
}