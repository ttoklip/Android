package com.umc.ttoklip.presentation.honeytip.write

import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
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
import androidx.core.widget.doAfterTextChanged
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
import com.umc.ttoklip.presentation.honeytip.HONEY_TIP
import com.umc.ttoklip.presentation.honeytip.ImageViewActivity
import com.umc.ttoklip.presentation.honeytip.adapter.Image
import com.umc.ttoklip.presentation.honeytip.adapter.ImageRVA
import com.umc.ttoklip.presentation.honeytip.adapter.OnImageClickListener
import com.umc.ttoklip.presentation.honeytip.dialog.ImageDialogFragment
import com.umc.ttoklip.presentation.honeytip.read.ReadHoneyTipActivity
import com.umc.ttoklip.presentation.mypage.adapter.HoneyTip
import com.umc.ttoklip.util.WriteHoneyTipUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class WriteHoneyTipActivity :
    BaseActivity<ActivityWriteHoneyTipBinding>(R.layout.activity_write_honey_tip),
    OnImageClickListener {
    private val imageAdapter: ImageRVA by lazy {
        ImageRVA(this, this)
    }
    private val board: String by lazy {
        intent.getStringExtra(BOARD)!!
    }
    private val viewModel: WriteHoneyTipViewModel by viewModels()
    private var category = Category.HOUSEWORK.toString()
    private var isEdit = false
    private var images = mutableListOf<Uri>()
    private var postId = 0

    private val pickMultipleMedia = registerForActivityResult(
        ActivityResultContracts.PickMultipleVisualMedia(
            100
        )
    ) { uris ->
        if (uris.isNotEmpty()) {
            updateImages(uris)
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }

    override fun initView() {
        binding.viewModel = viewModel
        initTabLayout()
        initImageRVA()
        checkHoneyTipOrQuestion()
        edit()
        addLink()
        showAddImageDialog()
        enableWriteDoneButton()
        writeDone()
        binding.backBtn.setOnClickListener {
            finish()
        }
    }

    override fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.writeDoneEvent.collect {
                    handleEvent(it)
                }
            }
        }

        viewModel.isBodyNull.observe(this) {
            Log.d("body", it.toString())
            if(!isEdit)
            binding.writeDoneBtn.isEnabled = viewModel.isBodyNull.value == false && viewModel.isTitleNull.value == false
        }
        viewModel.isTitleNull.observe(this) {
            Log.d("title", it.toString())
            if(!isEdit)
            binding.writeDoneBtn.isEnabled = viewModel.isBodyNull.value == false && viewModel.isTitleNull.value == false
        }
    }

    private fun handleEvent(event: WriteHoneyTipViewModel.WriteDoneEvent) {
        val intent = Intent(this@WriteHoneyTipActivity, ReadHoneyTipActivity::class.java)
        when (event) {
            is WriteHoneyTipViewModel.WriteDoneEvent.WriteDoneHoneyTip -> {
                intent.putExtra("postId", event.postId)
            }

            is WriteHoneyTipViewModel.WriteDoneEvent.WriteDoneQuestion -> {
                intent.putExtra("postId", event.postId)
            }
        }
        intent.putExtra(BOARD, board)
        startActivity(intent)
        finish()
    }

    private fun edit() {
        isEdit = intent.getBooleanExtra("isEdit", false)
        if (!isEdit){
            return
        } else {
            val editHoneyTip = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getSerializableExtra("honeyTip", EditHoneyTip::class.java)
            } else {
                intent.getSerializableExtra("honeyTip") as EditHoneyTip
            }
            with(binding) {
                writeDoneBtn.isEnabled = true
                writeDoneBtn.text = "수정완료"
                titleEt.setText(editHoneyTip?.title)
                bodyEt.setText(editHoneyTip?.content)
                inputUrlEt.setText(editHoneyTip?.url)
                imageRv.visibility = View.VISIBLE
                addLinkBtn.visibility = View.GONE
                inputUrlEt.visibility = View.VISIBLE
            }
            val images = editHoneyTip?.image?.map { it -> Image(Uri.parse(it)) }
            imageAdapter.submitList(images)
            postId = editHoneyTip?.postId ?: 0
            category = editHoneyTip?.category?:""
            binding.tabLayout.selectTab(binding.tabLayout.getTabAt(stringToNum(category)))
        }
    }

    /*private fun edit() {
        isEdit = intent.getBooleanExtra("isEdit", false)
        if (isEdit) {
            //val images: MutableList<Uri> = mutableListOf()
            if (board == HONEY_TIP) {
                val honeyTip = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    intent.getSerializableExtra("honeyTip", HoneyTip::class.java)
                } else {
                    intent.getSerializableExtra("honeyTip") as HoneyTip
                }
                Log.d("honeytip image", honeyTip.toString())
                with(binding) {
                    titleEt.setText(honeyTip?.title)
                    bodyEt.setText(honeyTip?.content)
                    inputUrlEt.setText(honeyTip?.uri)
                    binding.inputUrlBtn.isVisible = true
                    binding.addLinkBtn.isVisible = false
                    binding.imageRv.isVisible = true
                    images.addAll((honeyTip?.images ?: emptyArray()).map { uriString ->
                        Uri.parse(uriString)
                    })
                }
            } else {
                val question = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    intent.getSerializableExtra("question", Question::class.java)
                } else {
                    intent.getSerializableExtra("question") as Question
                }
                with(binding) {
                    titleEt.setText(question?.title)
                    bodyEt.setText(question?.content)
                    binding.imageRv.isVisible = true
                    images.addAll((question?.images ?: emptyArray()).map { uriString ->
                        Uri.parse(uriString)
                    })
                }
            }
            binding.writeDoneBtn.text = "수정완료"
            binding.writeDoneBtn.isEnabled = true
            updateImages(images)
            //Log.d("image", images.toString())
            binding.writeDoneBtn.setOnClickListener {
                Toast.makeText(this@WriteHoneyTipActivity, "수정완료", Toast.LENGTH_SHORT)
                    .show()
                //goReadActivity()
            }
        }
    }*/

    private fun checkHoneyTipOrQuestion() {
        if (board == HONEY_TIP) {
            binding.titleTv.text = "꿀팁 공유하기"
            //editHoneyTip()

        } else {
            binding.titleTv.text = "질문하기"
            binding.addLinkBtn.visibility = View.GONE
            //editQuestion()
        }
    }

    private fun enableWriteDoneButton() {
        /*binding.titleEt.doAfterTextChanged {
            if (it.toString().isNotBlank()) {
                viewModel.setTitle(false)
            } else {
                viewModel.setTitle(true)
            }
        }*/

        binding.titleEt.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if(s.toString().isNotBlank()){
                    viewModel.setTitle(false)
                } else {
                    viewModel.setTitle(true)
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s.toString().isNotBlank()){
                    viewModel.setTitle(false)
                } else {
                    viewModel.setTitle(true)
                }
            }

            override fun afterTextChanged(s: Editable?) {
                if(s.toString().isNotBlank()){
                    viewModel.setTitle(false)
                } else {
                    viewModel.setTitle(true)
                }
            }

        })

        binding.bodyEt.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if(s.toString().isNotBlank()){
                    viewModel.setBody(false)
                } else {
                    viewModel.setBody(true)
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s.toString().isNotBlank()){
                    viewModel.setBody(false)
                } else {
                    viewModel.setBody(true)
                }
            }

            override fun afterTextChanged(s: Editable?) {
                if(s.toString().isNotBlank()){
                    viewModel.setBody(false)
                } else {
                    viewModel.setBody(true)
                }
            }

        })

        /*binding.bodyEt.doAfterTextChanged {
            if (it.toString().isNotBlank()) {
                viewModel.setBody(false)
            } else {
                viewModel.setBody(true)
            }
        }*/
    }

    private fun writeDone() {
        binding.writeDoneBtn.setOnClickListener {
            val images =
                imageAdapter.currentList.filterIsInstance<Image>().map { it.uri }.toList()
            val imageParts = WriteHoneyTipUtil(this).convertUriToMultiBody(images)

            val title = binding.titleEt.text.toString()
            val content = binding.bodyEt.text.toString()
            val category = category.toString()
            Log.d("write done category", category)
            val url = binding.inputUrlEt.text.toString()

            if(isEdit){
                Log.d("it Edit", isEdit.toString())
                viewModel.editHoneyTip(postId, title, content, category, imageParts, url)
            } else {
                if (board == HONEY_TIP) {
                    viewModel.createHoneyTip(title, content, category, imageParts, url)
                } else {
                    viewModel.createQuestion(title, content, category, imageParts)
                }
            }
        }
    }


    /*private fun convertResizeImage(imageUri: Uri): Uri {
        val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
        val resizedBitmap =
            Bitmap.createScaledBitmap(bitmap, bitmap.width / 2, bitmap.height / 2, true)

        val byteArrayOutputStream = ByteArrayOutputStream()
        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream)

        val tempFile = File.createTempFile("resized_image", ".jpg", this.cacheDir)
        val fileOutputStream = FileOutputStream(tempFile)
        fileOutputStream.write(byteArrayOutputStream.toByteArray())
        fileOutputStream.close()

        return Uri.fromFile(tempFile)
    }

    private fun convertUriToJpegFile(context: Context, uri: Uri, targetFilename: kotlin.String): File? {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val outputFile = File(context.cacheDir, "$targetFilename.jpeg")

        inputStream?.use { input ->
            FileOutputStream(outputFile).use { output ->
                val buffer = ByteArray(4 * 1024) // 4KB buffer size
                while (true) {
                    val byteCount = input.read(buffer)
                    if (byteCount < 0) break
                    output.write(buffer, 0, byteCount)
                }
                output.flush()
            }
        }

        return if (outputFile.exists()) outputFile else null
    }*/

    private fun initImageRVA() {
        //imageAdapter = ImageRVA(this)
        binding.imageRv.adapter = imageAdapter
    }

    /*private fun editHoneyTip() {
        isEdit = intent.getBooleanExtra("isEdit", false)
        if (isEdit) {
            //val images: MutableList<Uri> = mutableListOf()
            val honeyTip = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getSerializableExtra("honeyTip", HoneyTip::class.java)
            } else {
                intent.getSerializableExtra("honeyTip") as HoneyTip
            }
            initEditHoneyTipView(honeyTip)
            editDone()
        }
    }*/

    /*private fun initEditHoneyTipView(honeyTip: HoneyTip?) {
        with(binding) {
            titleEt.setText(honeyTip?.title)
            bodyEt.setText(honeyTip?.content)
            inputUrlEt.setText(honeyTip?.uri)
            inputUrlBtn.isVisible = true
            addLinkBtn.isVisible = false
            imageRv.isVisible = true
            Log.d("write edit category", honeyTip?.category.toString())
            tabLayout.selectTab(binding.tabLayout.getTabAt(stringToNum(honeyTip?.category!!)))

            images.addAll((honeyTip?.images ?: emptyArray()).map { uriString ->
                Uri.parse(uriString)
            })
        }
    }*/

    /*private fun editQuestion() {
        isEdit = intent.getBooleanExtra("isEdit", false)
        if (isEdit) {
            val question = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getSerializableExtra("question", Question::class.java)
            } else {
                intent.getSerializableExtra("quesion") as Question
            }
            initEditQuestionView(question)
            editDone()
        }
    }*/

    /*private fun initEditQuestionView(question: Question?) {
        with(binding) {
            titleEt.setText(question?.title)
            bodyEt.setText(question?.content)
            imageRv.isVisible = true
            tabLayout.selectTab(binding.tabLayout.getTabAt(stringToNum(question?.category!!)))

            images.addAll((question?.images ?: emptyArray()).map { uriString ->
                Uri.parse(uriString)
            })
        }
    }*/

    private fun editDone() {
        binding.writeDoneBtn.text = "수정완료"
        binding.writeDoneBtn.isEnabled = true
        updateImages(images)
        //Log.d("image", images.toString())
        binding.writeDoneBtn.setOnClickListener {
            Toast.makeText(this@WriteHoneyTipActivity, "수정완료", Toast.LENGTH_SHORT)
                .show()
            //goReadActivity()
        }
    }

    private fun initTabLayout() {
        val tabTitles = listOf("집안일", "요리", "안전한 생활", "복지 \u00b7 정책")
        for (i in tabTitles.indices) {
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText(tabTitles[i]))
        }

        setTabItemMargin(binding.tabLayout, 40)
        setSelectedTabTextStyleBold(R.font.pretendard_bold, binding.tabLayout.selectedTabPosition)

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                category = tabTextToCategory(tab?.text.toString())
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
        binding.addLinkBtn.setOnClickListener {
            binding.addLinkBtn.visibility = View.GONE
            binding.inputUrlBtn.visibility = View.VISIBLE
        }
    }

    private fun showAddImageDialog() {
        binding.addImageBtn.setOnClickListener {
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

        val images = uriList.map { Image(it) }
        val updatedImages = imageAdapter.currentList.toMutableList().apply { addAll(images) }
        imageAdapter.submitList(updatedImages)
    }

    enum class Category {
        HOUSEWORK,
        RECIPE,
        SAFE_LIVING,
        WELFARE_POLICY
    }

    private fun tabTextToCategory(string: kotlin.String): String {
        return when (string) {
            "집안일" -> Category.HOUSEWORK.toString()
            "요리" -> Category.RECIPE.toString()
            "안전한 생활" -> Category.SAFE_LIVING.toString()
            else -> Category.WELFARE_POLICY.toString()
        }
    }

    private fun stringToNum(category: kotlin.String): Int {
        return when (category) {
            "HOUSEWORK" -> 0
            "RECIPE" -> 1
            "SAFE_LIVING" -> 2
            else -> 3
        }
    }

    override fun onClick(image: Image) {
        val index = imageAdapter.currentList.indexOf(image)
        Log.d("image index", index.toString())
        val images = imageAdapter.currentList.filterIsInstance<Image>().map { it.uri.toString() }
            .toTypedArray()
        //Log.d("images", images.toString())
        val intent = Intent(this, ImageViewActivity::class.java)
        intent.putExtra("images", images)
        intent.putExtra("position", index)
        startActivity(intent)
    }

    override fun deleteImage(position: Int) {
        val imageList = imageAdapter.currentList.toMutableList()
        Log.d("imageList", imageList.toString())
        imageList.removeAt(position)
        Log.d("after imageList", imageList.toString())
        imageAdapter.submitList(imageList)
    }
}