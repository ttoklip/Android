package com.umc.ttoklip.presentation.mypage

import android.app.Activity
import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.chip.Chip
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivityManageMyInfoBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.honeytip.write.WriteHoneyTipActivity
import com.umc.ttoklip.presentation.mypage.vm.ManageMyInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

@AndroidEntryPoint
class ManageMyInfoActivity :
    BaseActivity<ActivityManageMyInfoBinding>(R.layout.activity_manage_my_info) {
    private val viewModel: ManageMyInfoViewModel by viewModels()
    private lateinit var profileImage: Uri
    private lateinit var tempImage: Uri
    private var independentYear = 0
    private var independentMonth = 0
    private val category = mutableListOf<String>()
    private var address=""
    private var locationX=0
    private var locationY=0

    private val pickMultipleMedia = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia(
        )
    ) { uri ->
        if (uri != null) {
            val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
            applicationContext.contentResolver.takePersistableUriPermission(uri, flag)
            profileImage = uri
            Log.d("change image", profileImage.toString())
            updateImages()
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }

    override fun initView() {
        binding.vm = viewModel
        initViewListener()
        viewModel.getMyPageInfo()
    }

    private fun initViewListener() {
        binding.manageMyInfoBackBtn.setOnClickListener {
            this@ManageMyInfoActivity.onBackPressedDispatcher.onBackPressed()
        }


        binding.inputIndependentCareerEt.setOnClickListener {
            val bottomSheet = InputIndependentCareerDialogFragment { year, month ->
                independentYear = year
                independentMonth = month
                if (year != ZERO_CAREER && month != ZERO_CAREER) {
                    binding.inputIndependentCareerEt.text =
                        getString(R.string.my_independent_career_base_format, year, month)
                } else if (year != ZERO_CAREER) {
                    binding.inputIndependentCareerEt.text =
                        getString(R.string.my_independent_career_year_format, year)
                } else if (month != ZERO_CAREER) {
                    binding.inputIndependentCareerEt.text =
                        getString(R.string.my_independent_career_month_format, month)
                } else {
                    binding.inputIndependentCareerEt.text =
                        getString(R.string.my_independent_career_month_format, ZERO_CAREER)
                }
            }
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }

        binding.findAddressBtn.setOnClickListener {
            val intent= Intent(this,MyHometownAddressActivity::class.java)
            val Location_Type=1
            startActivityForResult(intent,Location_Type)
        }

        binding.mainInterestGroup.setOnClickListener {
            val bottomSheet = ChooseMainInterestDialogFragment { interests ->
                category.addAll(interests)
                binding.mainInterestGroup.removeAllViews()
                interests.forEach { interest ->
                    val chip = Chip(this)
                    chip.text = interest
                    chip.setTextAppearance(R.style.TextAppearance_App_12sp_700)
                    chip.setTextColor(ContextCompat.getColor(this, R.color.black))
                    chip.setChipBackgroundColorResource(R.color.yellow)
                    chip.setChipStrokeColorResource(R.color.yellow)
                    binding.mainInterestGroup.addView(chip)
                }
            }
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }

        binding.finishUpdateProfileBtn.setOnClickListener {
            val imageList = listOf(profileImage)
            val image = convertUriListToMultiBody(imageList)
            val imageRequest = image[0]
            val categories = category.map { it -> tabTextToCategory(it) }
            Log.d("categories", categories.toString())
            Log.d("imageRequest", imageRequest.toString())
            viewModel.editMyPageInfo(
                address,
                locationX,
                locationY,
                binding.inputNicknameEt.text.toString(),
                categories,
                imageRequest,
                independentYear, independentMonth
            )
            val delete = deleteImage(tempImage)
            //Log.d("delete", delete.toString())
            //finish()
        }

        binding.manageProfileImg.setOnClickListener {
            pickMultipleMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode!= Activity.RESULT_OK){
            return
        }
        if(requestCode==1){
            if (data != null) {
                binding.inputAddressTv.text= data.getStringExtra("location")
                address=data.getStringExtra("location").toString()
                locationX=data.getIntExtra("locationX",0)
                locationY=data.getIntExtra("locationY",0)
            }
        }
    }

    override fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.myPageInfo.collect {
                    with(binding) {
                        inputNicknameEt.setText(it.nickname)
                        inputIndependentCareerEt.text = "${it.independentYear}년 ${it.independentMonth}개월"
                        addressTitleTv.text = it.street
                        independentYear = it.independentYear
                        independentMonth = it.independentMonth
                        convertURLtoURI(it.profileImage)
                        Glide.with(this@ManageMyInfoActivity)
                            .load(it.profileImage)
                            .into(binding.manageProfileImg)
                    }
                }
            }
        }
    }

    companion object {
        private const val ZERO_CAREER = 0
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm: InputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

    private fun updateImages() {
        binding.manageProfileImg.setImageURI(profileImage)
    }

    private fun convertURLtoURI(photos: String?) {
        Glide.with(this).asBitmap().load(photos)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    Log.d("bittmap", resource.toString())
                    profileImage = getImageUri(this@ManageMyInfoActivity, resource)
                    tempImage = profileImage
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })

    }

    private fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            inContext.contentResolver,
            inImage,
            "Title",
            null
        )
        val uri = Uri.parse(path)
        Log.d("uri", uri.toString())
        return Uri.parse(path)
    }

    private fun deleteImage(imageUri: Uri): Boolean {
        val contentResolver: ContentResolver = this.contentResolver

        // 이미지의 ID를 추출합니다.
        val id = ContentUris.parseId(imageUri)

        // ContentResolver를 사용하여 이미지를 삭제합니다.
        val deleteUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI.buildUpon()
            .appendPath(id.toString()).build()

        Log.d("delete uri", deleteUri.toString())

        val deletedRows = contentResolver.delete(deleteUri, null, null)

        // 삭제 성공 여부를 반환합니다.
        return deletedRows > 0

        //불러오면 파일 저장
    }

    fun convertUriListToMultiBody(images: List<Uri>): Array<MultipartBody.Part> {
        val imageParts: MutableList<MultipartBody.Part> = mutableListOf()
        if (images.isNotEmpty()) {
            for (i in images.indices) {
                //Log.d("images", images[i].toString())
                val imagePath = images[i]
                Log.d("path", imagePath.lastPathSegment.toString())
                val imageFile =
                    convertUriToJpegFile(this, imagePath, imagePath.lastPathSegment.toString())
                if (imageFile == null) {
                    null
                } else {
                    val imageRequestBody =
                        imageFile.asRequestBody("image/*".toMediaTypeOrNull())
                    val imagePart = MultipartBody.Part.createFormData(
                        "profileImage",
                        imageFile.name,
                        imageRequestBody
                    )
                    imageParts.add(imagePart)
                }
            }
        }
        return imageParts.toTypedArray()
    }

    private fun convertUriToJpegFile(
        context: Context,
        uri: Uri,
        targetFilename: kotlin.String
    ): File? {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val outputFile = File(context.cacheDir, "$targetFilename.jpeg")
        Log.d("outfile", outputFile.toString())

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

    private fun tabTextToCategory(string: kotlin.String): String {
        return when (string) {
            "집안일" -> WriteHoneyTipActivity.Category.HOUSEWORK.toString()
            "레시피" -> WriteHoneyTipActivity.Category.RECIPE.toString()
            "안전한 생활" -> WriteHoneyTipActivity.Category.SAFE_LIVING.toString()
            else -> WriteHoneyTipActivity.Category.WELFARE_POLICY.toString()
        }
    }
}