package com.umc.ttoklip.presentation.honeytip.write

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Typeface
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.umc.ttoklip.R
import com.umc.ttoklip.data.model.CreateHoneyTipRequest
import com.umc.ttoklip.databinding.ActivityHoneyTipBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.honeytip.BOARD
import com.umc.ttoklip.presentation.honeytip.HONEY_TIP
import com.umc.ttoklip.presentation.honeytip.HoneyTipViewModel
import com.umc.ttoklip.presentation.honeytip.ImageViewActivity
import com.umc.ttoklip.presentation.honeytip.adapter.Image
import com.umc.ttoklip.presentation.honeytip.adapter.ImageRVA
import com.umc.ttoklip.presentation.honeytip.adapter.OnImageClickListener
import com.umc.ttoklip.presentation.honeytip.dialog.ImageDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType
import okhttp3.MediaType.Companion.parse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.Collections.addAll


@AndroidEntryPoint
class WriteHoneyTipActivity : BaseActivity<ActivityHoneyTipBinding>(R.layout.activity_honey_tip),
    OnImageClickListener {
    private val viewModel: HoneyTipViewModel by viewModels()
    private lateinit var imageAdapter: ImageRVA
    private var category: Category = Category.HOUSEWORK

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
        /*supportFragmentManager.beginTransaction()
            .replace(R.id.container, WriteHoneyTipFragment(intent.getStringExtra(BOARD) ?: ""))
            .commit()*/
        binding.viewModel = viewModel
        setTitle()
        initTabLayout()
        initImageRVA()
        addLink()
        addImage()
        writeDone()
        binding.backBtn.setOnClickListener {
            finish()
        }
    }

    override fun initObserver() {

    }

    private fun setTitle() {
        when (intent.getStringExtra(BOARD)) {
            HONEY_TIP -> {
                binding.titleTv.text = "꿀팁 공유하기"
            }

            else -> {
                binding.titleTv.text = "질문하기"
                binding.addLinkBtn.visibility = View.GONE
            }
        }
    }

    private fun writeDone() {
        binding.titleEt.doAfterTextChanged {
            if (!it.toString().isNullOrBlank()) {
                viewModel.setTitle(false)
            } else {
                viewModel.setTitle(true)
            }
        }

        binding.bodyEt.doAfterTextChanged {
            if (!it.toString().isNullOrBlank()) {
                viewModel.setBody(false)
            } else {
                viewModel.setBody(true)
            }
        }

        binding.writeDoneBtn.setOnClickListener {
            val images = imageAdapter.currentList.filterIsInstance<Image>().map { it.uri }.toList()
            val imageParts: MutableList<MultipartBody.Part> = mutableListOf()
            for (i in images.indices) {
                val imagePart: MultipartBody.Part? = if (images[i] != null) {
                    val imagePath = images[i]
                    val imageFile = convertUriToJpegFile(this, imagePath, "${imagePath.port + i}")
                    if (imageFile == null) {
                        null
                    } else {
                        //val imageFile = File(absolutelyPath(images[i], this))
                        val imageRequestBody =
                            imageFile.asRequestBody("image/*".toMediaTypeOrNull())
                        Log.d("imagePart", imageRequestBody.toString())
                        MultipartBody.Part.createFormData(
                            "images",
                            imageFile.name,
                            imageRequestBody
                        )
                    }
                } else {
                    null
                }
                if (imagePart != null) {
                    imageParts?.add(imagePart)
                }
            }

            val honeyTip = CreateHoneyTipRequest(
                binding.titleEt.text.toString(), binding.bodyEt.text.toString(),
                category.toString()
            ) //,listOf( binding.inputUrlEt.text.toString()))
            Log.d("createHoneytip", honeyTip.toString())
            Log.d("imageParts", imageParts?.toString() ?: "")

            val gson = Gson()
            val tipJson = gson.toJson(honeyTip)
            val tipRequestBody = tipJson.toRequestBody("application/json".toMediaTypeOrNull())
            val title =
                binding.titleEt.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val content =
                binding.bodyEt.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val category = category.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            viewModel.createHoneyTip(title, content, category, imageParts?.toTypedArray()!!)
            //finish()
        }

    }

    fun convertUriToJpegFile(context: Context, uri: Uri, targetFilename: String): File? {
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
    }

    fun absolutelyPath(path: Uri?, context: Context): String {
        var proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        var c: Cursor? = context.contentResolver.query(path!!, proj, null, null, null)
        var index = c?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c?.moveToFirst()

        var result = c?.getString(index!!)

        return result!!
    }

    private fun initImageRVA() {
        imageAdapter = ImageRVA(this)
        /*val images = imageAdapter.currentList.filterIsInstance<Image>().map{it.uri.toString()}.toTypedArray()
        val intent = Intent(this, ImageViewActivity::class.java)
        intent.putExtra("images", images)
        startActivity(intent)*/

        binding.imageRv.adapter = imageAdapter
    }

    private fun initTabLayout() {
        val tabTitles = listOf("집안일", "요리", "안전한 생활", "사기", "복지 \u00b7 정책")
        for (i in tabTitles.indices) {
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText(tabTitles[i]))
        }

        setTabItemMargin(binding.tabLayout, 40)
        setSelectedTabTextStyleBold(R.font.pretendard_bold, binding.tabLayout.selectedTabPosition)

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
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

    private fun addImage() {
        binding.addImageBtn.setOnClickListener {
            val imageDialog = ImageDialogFragment()
            imageDialog.setDialogClickListener(object : ImageDialogFragment.DialogClickListener {
                override fun onClick() {
                    binding.imageRv.visibility = View.VISIBLE
                    pickMultipleMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }
            })
            imageDialog.show(supportFragmentManager, imageDialog.toString())
        }
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

    enum class Category {
        HOUSEWORK,
        COOKING,
        SAFE_LIVING,
        WELFARE_POLICY
    }
}