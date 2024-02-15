package com.umc.ttoklip.presentation.honeytip.read

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.umc.ttoklip.R
import com.umc.ttoklip.data.model.honeytip.ImageUrl
import com.umc.ttoklip.databinding.ActivityReadHoneyTipBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.honeytip.BOARD
import com.umc.ttoklip.presentation.honeytip.HONEY_TIP
import com.umc.ttoklip.presentation.honeytip.adapter.OnReadImageClickListener
import com.umc.ttoklip.presentation.honeytip.adapter.ReadImageRVA
import com.umc.ttoklip.presentation.honeytip.dialog.DeleteDialogFragment
import com.umc.ttoklip.presentation.honeytip.dialog.ReportDialogFragment
import com.umc.ttoklip.presentation.news.adapter.Comment
import com.umc.ttoklip.presentation.news.adapter.CommentRVA
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReadHoneyTipActivity : BaseActivity<ActivityReadHoneyTipBinding>(R.layout.activity_read_honey_tip),
    OnReadImageClickListener {
    private val viewModel: ReadHoneyTipViewModel by viewModels()

    private val commentRVA by lazy {
        CommentRVA()
    }
    private val board: String by lazy {
        intent.getStringExtra(BOARD)!!
    }
    private val imageAdapter: ReadImageRVA by lazy {
        ReadImageRVA(this, this@ReadHoneyTipActivity)
    }
    private var images: MutableList<Uri> = mutableListOf()
    private var isShowMenu = false
    private var category = ""

    override fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.readEvent.collect{
                    /*Log.d("read honeyTip", it.toString())
                    binding.titleTv.text = it.title
                    binding.contentT.text = it.content
                    binding.linkT.text = it.urlResponses.firstOrNull()
                    imageAdapter.submitList(it.imageUrls)*/
                    handleEvent(it)
                }
            }
        }
    }

    private fun handleEvent(event: ReadHoneyTipViewModel.ReadEvent){
        when(event){
            is ReadHoneyTipViewModel.ReadEvent.ReadHoneyTipEvent -> {
                binding.titleTv.text = event.inquireHoneyTipResponse.title
                binding.contentT.text = event.inquireHoneyTipResponse.content
                binding.linkT.text = event.inquireHoneyTipResponse?.urlResponses?.firstOrNull()?.urls
                imageAdapter.submitList(event.inquireHoneyTipResponse.imageUrls)
            }
            is ReadHoneyTipViewModel.ReadEvent.ReadQuestionEvent -> {
                binding.titleTv.text = event.inquireQuestionResponse.title
                binding.contentT.text = event.inquireQuestionResponse.content
                binding.linkT.text = event.inquireQuestionResponse.urlResponses?.firstOrNull()
                imageAdapter.submitList(event.inquireQuestionResponse.imageUrls)
            }
        }
    }
    override fun initView() {
        binding.view = this
        val postId = intent.getIntExtra("postId", 0)
        Log.d("read postid", postId.toString())
        if (board == HONEY_TIP) {
            binding.boardTitleTv.text = "꿀팁 공유하기"
            viewModel.inquireHoneyTip(postId)
        } else {
            binding.boardTitleTv.text = "질문하기"
            binding.linkLayout.visibility = View.GONE
            viewModel.inquireQuestion(postId)
        }

        binding.backBtn.setOnClickListener {
            finish()
        }

        initImageRVA()
        //checkHoneyTipOrQuestion()
        showMenu()
        showDeleteDialog()

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

    /*private fun checkHoneyTipOrQuestion() {
        //board = intent.getStringExtra(BOARD)!!

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
    }*/

    /*private fun checkHoneyTip() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            honeyTip = intent.getSerializableExtra("honeyTip", HoneyTip::class.java)
        } else{
            honeyTip = intent.getSerializableExtra("honeyTip") as HoneyTip
        }
        if (honeyTip != null) {
            with(binding) {
                titleTv.text = honeyTip?.title
                contentT.text = honeyTip?.content
                linkT.text = honeyTip?.uri
                category = honeyTip?.category!!
                val temp = (honeyTip?.images ?: emptyArray()).map { uriString ->
                    Uri.parse(uriString)
                }
                images.addAll(temp)
                updateImages(images.toList())
            }
        }
    }*/

    /*private fun checkQuestion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            question = intent.getSerializableExtra("question", Question::class.java)
        } else {
            question = intent.getSerializableExtra("question") as Question
        }
        if (question != null) {
            with(binding) {
                titleTv.text = question?.title
                contentT.text = question?.content
                category = question?.category!!
                val temp = (question?.images ?: emptyArray()).map { uriString ->
                    Uri.parse(uriString)
                }
                images.addAll(temp)
                updateImages(images.toList())
            }
        }
    }*/

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

    private fun editDone(intent: Intent){
        intent.putExtra(BOARD, board)
        intent.putExtra("isEdit", true)
        startActivity(intent)
        finish()
    }

    /*private fun updateImages(uriList: List<Uri>) {
        val images = uriList.map { Image(it) }
        val updatedImages = imageAdapter.currentList.toMutableList().apply { addAll(images) }
        imageAdapter.submitList(updatedImages)
    }*/

    /*override fun onClick(image: Image) {
        val images = imageAdapter.currentList.filterIsInstance<Image>().map { it.uri.toString() }
            .toTypedArray()
        Log.d("images", images.toString())
        val intent = Intent(this, ImageViewActivity::class.java)
        intent.putExtra("images", images)
        startActivity(intent)
    }*/

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
        //Log.d("event", isTouchInside(binding.dotBtn, ev?.x!!, ev?.y!!).toString())
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

    override fun onClick(imageUrl: ImageUrl) {

    }
}