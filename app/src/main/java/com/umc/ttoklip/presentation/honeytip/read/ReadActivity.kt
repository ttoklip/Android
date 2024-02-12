package com.umc.ttoklip.presentation.honeytip.read

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import com.umc.ttoklip.R
import com.umc.ttoklip.data.model.honeytip.HoneyTip
import com.umc.ttoklip.data.model.honeytip.Question
import com.umc.ttoklip.databinding.ActivityReadBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.honeytip.ASK
import com.umc.ttoklip.presentation.honeytip.BOARD
import com.umc.ttoklip.presentation.honeytip.HONEY_TIP
import com.umc.ttoklip.presentation.honeytip.ImageViewActivity
import com.umc.ttoklip.presentation.honeytip.adapter.Image
import com.umc.ttoklip.presentation.honeytip.adapter.ImageRVA
import com.umc.ttoklip.presentation.honeytip.adapter.OnImageClickListener
import com.umc.ttoklip.presentation.honeytip.dialog.DeleteDialogFragment
import com.umc.ttoklip.presentation.honeytip.dialog.ReportDialogFragment
import com.umc.ttoklip.presentation.honeytip.write.WriteHoneyTipActivity
import com.umc.ttoklip.presentation.news.adapter.Comment
import com.umc.ttoklip.presentation.news.adapter.CommentRVA
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.internal.cache2.Relay.Companion.edit
import java.util.Collections.addAll

@AndroidEntryPoint
class ReadActivity : BaseActivity<ActivityReadBinding>(R.layout.activity_read),
    OnImageClickListener {
    private val commentRVA by lazy {
        CommentRVA()
    }
    private lateinit var board: String
    private lateinit var imageAdapter: ImageRVA
    private var images: MutableList<Uri> = mutableListOf()
    private var honeyTip: HoneyTip? = null
    private var question: Question? = null
    private var isShowMenu = false
    override fun initView() {
        binding.view = this
        binding.backBtn.setOnClickListener {
            finish()
        }

        //separateBoard()
        initImageRVA()
        checkHoneyTipOrQuestion()
        showMenu()
        showDeleteDialog()
        // 수정하기
        /*binding.editBtn.setOnClickListener {
            val intent = Intent(this, WriteHoneyTipActivity::class.java)
            val intentImages = imageAdapter.currentList.filterIsInstance<Image>()?.map { it.uri.toString() }
                ?.toTypedArray()
            val title = binding.titleTv.text.toString()
            val content = binding.contentT.text.toString()
            val url = binding.linkT.text.toString()
            if (board == HONEY_TIP) {
                val honeyTip = HoneyTip(
                    title,
                    content,
                    intentImages,
                    url
                )
                intent.putExtra("honeyTip", honeyTip)
            } else{
                val question = Question(
                    title,
                    content,
                    intentImages
                )
                intent.putExtra("question", question)
            }
            intent.putExtra(BOARD, board)
            intent.putExtra("isEdit", true)
            startActivity(intent)
            finish()
        }*/



        binding.commentRv.adapter = commentRVA
        commentRVA.submitList(
            listOf(
                Comment(1, 1, "ㅎㅇ", "ㅎ"),
                Comment(2, 2, "ㅎㅇ", "ㅎ"),
                Comment(3, 1, "ㅎㅇ", "ㅎ")
            )
        )
    }

    override fun initObserver() {

    }
    private fun initImageRVA() {
        imageAdapter = ImageRVA(this@ReadActivity)
        binding.imageRv.adapter = imageAdapter
    }

    private fun checkHoneyTipOrQuestion() {
        board = intent.getStringExtra(BOARD)!!

        if (board == HONEY_TIP) {
            binding.boardTitleTv.text = "꿀팁 공유하기"
            checkHoneyTip()
            editHoneyTip()
        } else {
            binding.boardTitleTv.text = "질문하기"
            binding.linkLayout.visibility = View.GONE
            checkQuestion()
            editQuestion()
        }
    }

    private fun checkHoneyTip() {
        honeyTip = intent.getSerializableExtra("honeyTip", HoneyTip::class.java)
        if (honeyTip != null) {
            with(binding) {
                titleTv.text = honeyTip?.title
                contentT.text = honeyTip?.content
                linkT.text = honeyTip?.uri
                val temp = (honeyTip?.images ?: emptyArray()).map { uriString ->
                    Uri.parse(uriString)
                }
                images.addAll(temp)
                updateImages(images.toList())
            }
        }
    }

    private fun checkQuestion() {
        question = intent.getSerializableExtra("question", Question::class.java)
        if (question != null) {
            with(binding) {
                titleTv.text = question?.title
                contentT.text = question?.content
                val temp = (question?.images ?: emptyArray()).map { uriString ->
                    Uri.parse(uriString)
                }
                images.addAll(temp)
                updateImages(images.toList())
            }
        }
    }

    private fun showMenu() {
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
            val intentImages =
                imageAdapter.currentList.filterIsInstance<Image>()?.map { it.uri.toString() }
                    ?.toTypedArray()
            val title = binding.titleTv.text.toString()
            val content = binding.contentT.text.toString()
            val url = binding.linkT.text.toString()
            val honeyTip = HoneyTip(
                title,
                content,
                intentImages,
                url
            )
            intent.putExtra("honeyTip", honeyTip)
            editDone(intent)
        }
    }

    private fun editQuestion() {
        binding.editBtn.setOnClickListener {
            val intent = Intent(this, WriteHoneyTipActivity::class.java)
            val intentImages =
                imageAdapter.currentList.filterIsInstance<Image>()?.map { it.uri.toString() }
                    ?.toTypedArray()
            val title = binding.titleTv.text.toString()
            val content = binding.contentT.text.toString()
            val question = Question(
                title,
                content,
                intentImages
            )
            intent.putExtra("question", question)
            editDone(intent)
        }
    }

    private fun editDone(intent: Intent){
        intent.putExtra(BOARD, board)
        intent.putExtra("isEdit", true)
        startActivity(intent)
        finish()
    }

    private fun updateImages(uriList: List<Uri>) {
        val images = uriList.map { Image(it) }
        val updatedImages = imageAdapter.currentList.toMutableList().apply { addAll(images) }
        imageAdapter.submitList(updatedImages)
    }

    override fun onClick(image: Image) {
        val images = imageAdapter.currentList.filterIsInstance<Image>().map { it.uri.toString() }
            .toTypedArray()
        Log.d("images", images.toString())
        val intent = Intent(this, ImageViewActivity::class.java)
        intent.putExtra("images", images)
        startActivity(intent)
    }

    private fun showReportDialog() {
        val reportDialog = ReportDialogFragment()
        reportDialog.setDialogClickListener(object : ReportDialogFragment.DialogClickListener {
            override fun onClick() {
            }
        })
        reportDialog.show(supportFragmentManager, reportDialog.tag)
    }

    private fun showDeleteDialog() {
        binding.deleteBtn.setOnClickListener {
            val deleteDialog = DeleteDialogFragment()
            deleteDialog.setDialogClickListener(object : DeleteDialogFragment.DialogClickListener {
                override fun onClick() {
                    finish()
                }
            })
            deleteDialog.show(supportFragmentManager, deleteDialog.tag)
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        Log.d("event", isTouchInside(binding.dotBtn, ev?.x!!, ev?.y!!).toString())
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
}