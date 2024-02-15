package com.umc.ttoklip.presentation.honeytip.write


import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
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
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.FragmentWriteHoneyTipBinding
import com.umc.ttoklip.presentation.base.BaseFragment
import com.umc.ttoklip.presentation.honeytip.BOARD
import com.umc.ttoklip.presentation.honeytip.HONEY_TIP
import com.umc.ttoklip.presentation.honeytip.ImageViewActivity
import com.umc.ttoklip.presentation.honeytip.adapter.Image
import com.umc.ttoklip.presentation.honeytip.adapter.ImageRVA
import com.umc.ttoklip.presentation.honeytip.adapter.OnImageClickListener
import com.umc.ttoklip.presentation.honeytip.dialog.ImageDialogFragment
import com.umc.ttoklip.presentation.honeytip.read.ReadHoneyTipActivity
import com.umc.ttoklip.presentation.mypage.adapter.HoneyTip
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream


class WriteHoneyTipFragment() : BaseFragment<FragmentWriteHoneyTipBinding>(R.layout.fragment_write_honey_tip),
    OnImageClickListener{
    /*private val imageLoadLauncher = registerForActivityResult(
        ActivityResultContracts.GetMultipleContents()
    ) { uriList ->
        updateImages(uriList)
    }*/
    private lateinit var imageAdapter: ImageRVA
    private lateinit var honeyTip: HoneyTip
    private var category: Category = Category.HOUSEWORK
    private val viewModel: WriteHoneyTipViewModel by activityViewModels()
    //private val navigator = findNavController()

    private val pickMultipleMedia = registerForActivityResult(
        ActivityResultContracts.PickMultipleVisualMedia(
            100
        )
    ) { uris ->
        if (uris.isNotEmpty()) {
            updateImages(uris)
            Log.d("images", uris.toString())
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }

    override fun initView() {
        initTabLayout()
        initImageRVA()
        addLink()
        addImage()
        writeDone()
        binding.backBtn.setOnClickListener {
            //navigator.popBackStack()
        }
    }

    override fun initObserver() {
        binding.viewModel = viewModel
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.writeDoneHoneyTipEvent.collect{
                    val intent = Intent(requireContext(), ReadHoneyTipActivity::class.java)
                    ///intent.putExtra("honeyTip", honeyTip)
                    intent.putExtra(BOARD, HONEY_TIP)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
            }
        }


    }
    private fun enableWriteDoneButton(){
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
    }


    private fun writeDone(){
        enableWriteDoneButton()
        binding.writeDoneBtn.setOnClickListener {
            val images = imageAdapter.currentList.filterIsInstance<Image>().map{it.uri}.toList()
            Log.d("images", images.toString())
            val imageParts: MutableList<MultipartBody.Part> = mutableListOf()
            for(i in images.indices) {
                val imagePart: MultipartBody.Part? = if(images[i] != null){
                    val imagePath = images[i]
                    val path = convertResizeImage(imagePath)
                    val imageFile = convertUriToJpegFile(requireContext(), imagePath, "$i+1")
                    if (imageFile == null){
                        null
                    }else {
                        //val imageFile = File(absolutelyPath(images[i], this))
                        val imageRequestBody =
                            imageFile.asRequestBody("image/*".toMediaTypeOrNull())
                        Log.d("imagePart", imageRequestBody.toString())
                        MultipartBody.Part.createFormData("images", imageFile.name, imageRequestBody)
                    }
                }
                else{
                    null
                }
                if (imagePart != null) {
                    imageParts?.add(imagePart)
                }
            }

            //,listOf( binding.inputUrlEt.text.toString()))
            // Log.d("createHoneytip", honeyTip.toString())
            Log.d("imageParts", imageParts?.toString()?:"")

            val gson = Gson()
            //val tipJson = gson.toJson(honeyTip)
            // val tipRequestBody = tipJson.toRequestBody("application/json".toMediaTypeOrNull())
            val title = binding.titleEt.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val content = binding.bodyEt.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val category = category.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val uri = binding.inputUrlEt.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val intentImages = imageAdapter.currentList.filterIsInstance<Image>().map{it.uri.toString()}.toTypedArray()
            //honeyTip = HoneyTip(binding.titleEt.text.toString(), binding.bodyEt.text.toString(), intentImages, binding.inputUrlEt.text.toString())
            //viewModel.createHoneyTip(title, content, category, imageParts.toTypedArray(), uri)
            //viewModel.createQuestion(title, content, category, imageParts?.toTypedArray()!!)
            //finish()
            //navigator.navigate(R.id.action_writeHoneyTipFragment2_to_readHoneyTipFragment2)
        }

    }

    private fun convertResizeImage(imageUri: Uri): Uri {
        val bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, imageUri)
        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.width / 2, bitmap.height / 2, true)

        val byteArrayOutputStream = ByteArrayOutputStream()
        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream)

        val tempFile = File.createTempFile("resized_image", ".jpg", requireContext().cacheDir)
        val fileOutputStream = FileOutputStream(tempFile)
        fileOutputStream.write(byteArrayOutputStream.toByteArray())
        fileOutputStream.close()

        return Uri.fromFile(tempFile)
    }


    fun uri2path(context: Context, contentUri: Uri?): String? {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(contentUri!!, proj, null, null, null)
        cursor!!.moveToNext()
        val path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA))
        val uri = Uri.fromFile(File(path))
        cursor.close()
        return path
    }

    private fun convertUriToJpegFile(context: Context, uri: Uri, targetFilename: String): File? {
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
    private fun initImageRVA() {
        imageAdapter = ImageRVA(this)
        /*val images = imageAdapter.currentList.filterIsInstance<Image>().map{it.uri.toString()}.toTypedArray()
        val intent = Intent(this, ImageViewActivity::class.java)
        intent.putExtra("images", images)
        startActivity(intent)*/

        binding.imageRv.adapter = imageAdapter
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
                category = stringToEnum(tab?.text.toString())
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
                requireContext(),
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
            imageDialog.show(parentFragmentManager, imageDialog.toString())
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
        val intent = Intent(requireContext(), ImageViewActivity::class.java)
        intent.putExtra("images", images)
        startActivity(intent)
    }

    enum class Category {
        HOUSEWORK,
        RECIPE,
        SAFE_LIVING,
        WELFARE_POLICY
    }

    private fun stringToEnum(string: String): Category{
        return when(string){
            "집안일" -> Category.HOUSEWORK
            "요리" -> Category.RECIPE
            "안전한 생활" -> Category.SAFE_LIVING
            else -> Category.WELFARE_POLICY
        }
    }

    /*private fun enableEditTextScroll(){
        binding.bodyEt.setOnTouchListener { v, event ->
            if (v.id == com.umc.ttoklip.R.id.body_et) {
                v.parent.requestDisallowInterceptTouchEvent(true)
                when (event.action and MotionEvent.ACTION_MASK) {
                    MotionEvent.ACTION_UP -> v.parent.requestDisallowInterceptTouchEvent(false)
                }
            }
            false
        }
    }*/
}


    /*
    private fun checkPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                loadImage()
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) -> {
                showPermissionInfoDialog()
            }

            else -> {
                requestReadExternalStorage()
            }
        }
    }

    private fun showPermissionInfoDialog() {
        AlertDialog.Builder(requireContext()).apply {
            setMessage("이미지를 가져오기 위해서, 외부 저장소 읽기 권한이 필요합니다.")
            setPositiveButton("동의") { _, _ ->
                requestReadExternalStorage()
            }
            setNegativeButton("취소", null)
        }.show()
    }

    private fun requestReadExternalStorage() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
            REQUEST_READ_EXTERNAL_STORAGE
        )
    }

    private fun loadImage() {
        imageLoadLauncher.launch("image/*")
    }

    companion object {
        const val REQUEST_READ_EXTERNAL_STORAGE = 100
    }*/
    */