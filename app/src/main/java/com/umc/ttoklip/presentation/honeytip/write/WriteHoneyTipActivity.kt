package com.umc.ttoklip.presentation.honeytip.write

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.tabs.TabLayout
import com.umc.ttoklip.R
import com.umc.ttoklip.TtoklipApplication
import com.umc.ttoklip.data.model.honeytip.EditHoneyTip
import com.umc.ttoklip.databinding.ActivityWriteHoneyTipBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.honeytip.BOARD
import com.umc.ttoklip.presentation.honeytip.HONEY_TIPS
import com.umc.ttoklip.presentation.honeytip.read.ReadImageViewActivity
import com.umc.ttoklip.presentation.honeytip.adapter.Image
import com.umc.ttoklip.presentation.honeytip.adapter.ImageRVA
import com.umc.ttoklip.presentation.honeytip.adapter.OnImageClickListener
import com.umc.ttoklip.presentation.dialog.ImageDialogFragment
import com.umc.ttoklip.presentation.honeytip.read.ReadHoneyTipActivity
import com.umc.ttoklip.presentation.honeytip.read.ReadQuestionActivity
import com.umc.ttoklip.util.isValidUri
import com.umc.ttoklip.util.setOnSingleClickListener
import com.umc.ttoklip.util.showToast
import com.umc.ttoklip.util.tabTextToCategory
import com.umc.ttoklip.util.uriToFile
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody


@AndroidEntryPoint
class WriteHoneyTipActivity :
    BaseActivity<ActivityWriteHoneyTipBinding>(R.layout.activity_write_honey_tip),
    OnImageClickListener {
    private val imageAdapter: ImageRVA by lazy {
        ImageRVA(this, this)
    }
    private val board: String by lazy {
        Log.d("board", intent.getStringExtra(BOARD)!!)
        intent.getStringExtra(BOARD)!!
    }
    private val viewModel: WriteHoneyTipViewModel by viewModels()
    private var category = Category.HOUSEWORK.toString()
    private var isEdit = false
    private var postId = 0

    // 꿀팁 공유해요 수정시 전송할 삭제된 이미지 index
    private var editDeleteImages = mutableListOf<Int>()

    // 이미지 불러오기
    private val pickMultipleMedia = registerForActivityResult(
        ActivityResultContracts.PickMultipleVisualMedia(
            100
        )
    ) { uriList ->
        if (uriList.isNotEmpty()) {
            updateImages(uriList)
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }

    override fun initView() {
        binding.viewModel = viewModel
        initTabLayout()
        initImageRVA()
        checkHoneyTipOrQuestion()
        initEditView()
        addLink()
        showAddImageDialog()
        enableWriteDoneButton()
        writeDone()
        binding.backBtn.setOnSingleClickListener {
            finish()
        }
    }

    override fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.writeDoneEvent.collect {
                    handleWriteDoneEvent(it)
                }
            }
        }
    }

    // 글 작성완료 시 글 확인 화면으로 이동
    private fun handleWriteDoneEvent(event: WriteHoneyTipViewModel.WriteDoneEvent) {
        when (event) {
            is WriteHoneyTipViewModel.WriteDoneEvent.WriteDoneHoneyTip -> {
                startActivity(ReadHoneyTipActivity.newIntent(this@WriteHoneyTipActivity, event.postId))
                finish()
            }

            is WriteHoneyTipViewModel.WriteDoneEvent.WriteDoneQuestion -> {
                startActivity(ReadQuestionActivity.newIntent(this@WriteHoneyTipActivity, event.postId))
                finish()
            }

            is WriteHoneyTipViewModel.WriteDoneEvent.IncludeSwear -> showToast(
//                event.message
                getString(R.string.post_fail)
            )
        }
    }

    // 글 수정시 원본 글 Data 반영
    private fun initEditView() {
        isEdit = intent.getBooleanExtra("isEdit", false)
        if (isEdit) {
            viewModel.setIsEdit(true)
            viewModel.setIsWriteDoneBtnEnable(true)

            val editHoneyTip = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getSerializableExtra("honeyTip", EditHoneyTip::class.java)
            } else {
                intent.getSerializableExtra("honeyTip") as EditHoneyTip
            }

            with(binding) {
                binding.tabLayout.visibility=View.GONE //수정시 카테고리 변경 불가함=>아예 안 보이도록 함
                titleEt.setText(editHoneyTip?.title)
                bodyEt.setText(editHoneyTip?.content)
                inputUrlEt.setText(editHoneyTip?.url)
                imageRv.visibility = View.VISIBLE
                addLinkBtn.visibility = View.GONE
                inputUrlBtn.visibility = View.VISIBLE
                category = editHoneyTip?.category ?: ""
                tabLayout.selectTab(tabLayout.getTabAt(stringToTabPosition(category)))
            }

            val images = editHoneyTip?.image?.toList()

            imageAdapter.submitList(images?.map { Image(it.imageId, it.imageUrl) })
            postId = editHoneyTip?.postId ?: 0
        }
    }

    // 게시판 종류에 따른 Title 변경
    private fun checkHoneyTipOrQuestion() {
        if (board == HONEY_TIPS) {
            binding.titleTv.text = "꿀팁 공유하기"

        } else {
            binding.titleTv.text = "질문하기"
            binding.addLinkBtn.visibility = View.GONE
            binding.bodyEt.hint = "똑리비들에게 어려운 점을 물어보세요.\n" +
                    "(최대 1000자) "
        }
    }

    // 작성완료 버튼 활성화
    private fun enableWriteDoneButton() {
        binding.titleEt.addTextChangedListener(
            afterTextChanged = { text ->
                if (text.toString().isNotBlank()) {
                    viewModel.setTitle(false)
                } else {
                    viewModel.setTitle(true)
                }
            }
        )

        binding.bodyEt.addTextChangedListener(
            afterTextChanged = { text ->
                if (text.toString().isNotBlank()) {
                    viewModel.setContent(false)
                } else {
                    viewModel.setContent(true)
                }
            }
        )
    }

    // 글 작성완료시 로직
    private fun writeDone() {
        binding.writeDoneBtn.setOnSingleClickListener {
            val imageParts = mutableListOf<MultipartBody.Part?>()
            val images = imageAdapter.currentList.filterIsInstance<Image>().map { it.src }
                .filter { it.isValidUri() }.toList()

            images.forEach { uri ->
                val file = uriToFile(Uri.parse(uri))
                val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                val body = if(isEdit){
                    MultipartBody.Part.createFormData("addImages", file.name, requestFile)
                } else {
                    MultipartBody.Part.createFormData("images", file.name, requestFile)
                }
                imageParts.add(body)
            }

            imageParts.forEach {
                if (it != null) {
                    Log.d("용량", "${it.body.contentLength().toDouble() / (1024 * 1024)}")
                    if (it.body.contentLength().toDouble() / (1024 * 1024) > 10) {
                        Toast.makeText(this, "사진 용량은 10MB로 제한되어있습니다.", Toast.LENGTH_SHORT).show()
                        return@setOnSingleClickListener
                    }
                }
            }

            val title = binding.titleEt.text.toString()
            val content = binding.bodyEt.text.toString()
            val category = category
            val url = binding.inputUrlEt.text.toString()

            if (isEdit) {
                Log.d("it Edit", isEdit.toString())
                Log.d("imageParts", imageParts.toString())
                viewModel.editHoneyTip(
                    postId,
                    title,
                    content,
                    category,
                    editDeleteImages,
                    imageParts,
                    url
                )
                Log.d("edit image imagepart", imageParts.toString())
            } else {
                if (board == HONEY_TIPS) {
                    viewModel.createHoneyTip(title, content, category, imageParts, url)
                } else {
                    viewModel.createQuestion(title, content, category, imageParts)
                }
            }
        }
    }

    private fun initImageRVA() {
        binding.imageRv.adapter = imageAdapter
    }

    private fun initTabLayout() {
        val tabTitles = listOf("집안일", "레시피", "안전한 생활", "복지 \u00b7 정책")
        for (i in tabTitles.indices) {
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText(tabTitles[i]))
        }

        setTabItemMargin(binding.tabLayout, 40)
        setSelectedTabTextStyleBold(R.font.pretendard_bold, binding.tabLayout.selectedTabPosition)

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                category = tab?.text.toString().tabTextToCategory()
                Log.d("category", category)
                setSelectedTabTextStyleBold(
                    R.font.pretendard_bold,
                    binding.tabLayout.selectedTabPosition
                )
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                setSelectedTabTextStyleBold(R.font.pretendard_medium, tab?.position!!)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    private fun setTabItemMargin(tabLayout: TabLayout, marginEnd: Int = 20) {
        for (i in 0 until 3) {
            val tabs = tabLayout.getChildAt(0) as ViewGroup
            for (i in 0 until tabs.childCount) {
                val tab = tabs.getChildAt(i)
                val lp = tab.layoutParams as LinearLayout.LayoutParams
                lp.marginEnd = marginEnd
                // -1: wrap_content, -2: match_parent
                lp.height = -2
                tab.layoutParams = lp
                tabLayout.requestLayout()
            }
        }
    }

    private fun setSelectedTabTextStyleBold(typeFace: Int, position: Int) {
        val typeface =
            ResourcesCompat.getFont(
                this,
                typeFace
            )
        val tabLayout =
            (binding.tabLayout.getChildAt(0) as ViewGroup)
        val tabTextView = (tabLayout.getChildAt(position) as LinearLayout).getChildAt(1) as TextView
        tabTextView.setTypeface(typeface, Typeface.NORMAL)
    }

    private fun addLink() {
        binding.addLinkBtn.setOnSingleClickListener {
            binding.addLinkBtn.visibility = View.GONE
            binding.inputUrlBtn.visibility = View.VISIBLE
        }
    }

    private fun showAddImageDialog() {
        binding.addImageBtn.setOnSingleClickListener {
            // 이미지 권한 여부 확인
            val imagePermission = TtoklipApplication.prefs.getString("getImagePermission", "")
            if (imagePermission != "true") {
                val imageDialog = ImageDialogFragment()
                imageDialog.setDialogClickListener(object :
                    ImageDialogFragment.DialogClickListener {
                    override fun onClick() {
                        TtoklipApplication.prefs.setString("getImagePermission", "true")
                        binding.imageRv.visibility = View.VISIBLE
                        pickMultipleMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    }
                })
                imageDialog.show(supportFragmentManager, imageDialog.toString())
            } else {
                binding.imageRv.visibility = View.VISIBLE
                pickMultipleMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
        }
    }

    private fun updateImages(uriList: List<Uri>) {
        // uri 권한 확장
        val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
        uriList.forEach {
            applicationContext.contentResolver.takePersistableUriPermission(it, flag)
        }

        val imageList = uriList.map { Image(0, it.toString()) }
        imageAdapter.submitList(imageAdapter.currentList.toMutableList().apply { addAll(imageList) })
    }

    enum class Category {
        HOUSEWORK,
        RECIPE,
        SAFE_LIVING,
        WELFARE_POLICY
    }

    private fun stringToTabPosition(category: String): Int {
        return when (category) {
            "HOUSEWORK" -> 0
            "RECIPE" -> 1
            "SAFE_LIVING" -> 2
            else -> 3
        }
    }

    override fun onClick(image: Image, position: Int) {
        val images = imageAdapter.currentList.map { it.src }.toTypedArray()
        startActivity(WriteImageViewActivity.newIntent(this, images, position))
    }

    override fun deleteImage(position: Int, id: Int) {
        editDeleteImages.add(id)

        imageAdapter.submitList(imageAdapter.currentList.toMutableList().apply {
            removeAt(position)
        })
    }

    companion object{
        fun newIntent(context: Context, board: String): Intent =
            Intent(context, WriteHoneyTipActivity::class.java).apply {
                putExtra("board", board)
            }
        fun editIntent(context: Context, isEdit: Boolean, board: String, editHoneyTip: EditHoneyTip): Intent =
            Intent(context, WriteHoneyTipActivity::class.java).apply {
                putExtra("isEdit", isEdit)
                putExtra("board", board)
                putExtra("honeyTip", editHoneyTip)
            }
    }
}