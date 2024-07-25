package com.umc.ttoklip.presentation.hometown.communication.write

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.umc.ttoklip.R
import com.umc.ttoklip.data.model.town.EditCommunication
import com.umc.ttoklip.databinding.ActivityWriteCommunicationBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.honeytip.adapter.Image
import com.umc.ttoklip.presentation.honeytip.adapter.ImageRVA
import com.umc.ttoklip.presentation.dialog.ImageDialogFragment
import com.umc.ttoklip.presentation.hometown.communication.read.ReadCommunicationActivity
import com.umc.ttoklip.util.isValidUri
import com.umc.ttoklip.util.uriToFile
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

@AndroidEntryPoint
class WriteCommunicationActivity :
    BaseActivity<ActivityWriteCommunicationBinding>(R.layout.activity_write_communication) {
    private val imageAdapter by lazy {
        ImageRVA(this, null)
    }
    private val viewModel: WriteCommunicationViewModel by viewModels<WriteCommunicationViewModelImpl>()
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
    private var isEdit = false

    override fun initView() {
        binding.vm = viewModel as WriteCommunicationViewModelImpl
        initImageRVA()
        addImage()

        binding.backBtn.setOnClickListener {
            finish()
        }

        val edit = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("edit", EditCommunication::class.java)
        } else {
            intent.getSerializableExtra("edit") as EditCommunication
        }

        Log.d("edit", edit.toString())

        if(edit != null){
            isEdit = true
            with(binding){
                writeDoneBtn.text = "수정완료"
                viewModel.setTitle(edit.title)
                viewModel.setBody(edit.content)
                viewModel.setPostId(edit.postId)
            }
        }

        binding.writeDoneBtn.setOnClickListener {
            if(isEdit){
                Log.d("isEdit", "뭔데")
                //viewModel.patchCommunication(EditCommunication(edit?.postId!!, binding.titleEt.text.toString(), binding.bodyEt.text.toString()))
            } else {
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
                viewModel.doneButtonClick(imageParts)
            }
        }
    }

    override fun initObserver() {
        with(lifecycleScope) {
            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.doneButtonActivated.collect {
                        binding.writeDoneBtn.isEnabled = it
                    }
                }
            }
            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.title.collect {
                        viewModel.checkDone()
                    }
                }
            }
            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.content.collect {
                        viewModel.checkDone()
                    }
                }
            }
            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.images.collect {
                        Log.d("uri image", it.toString())
                        imageAdapter.submitList(it.toList())
                    }
                }
            }
            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.closePage.collect {
                        Log.d("close page", it.toString())
                        if (it != 0L) {
                            startActivity(ReadCommunicationActivity.newIntent(this@WriteCommunicationActivity, it))
                            finish()
                        }
                    }
                }
            }
        }
    }

    private fun initImageRVA() {
        binding.imageRv.adapter = imageAdapter
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
            imageDialog.show(supportFragmentManager, imageDialog.tag)
        }
    }

    private fun updateImages(uriList: List<Uri>) {
        Log.d("uri", uriList.toString())
        val images = uriList.map { Image(0, it.toString()) }
        viewModel.addImages(images)
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

    companion object{
        fun newIntent(context: Context, editCommunication: EditCommunication) =
            Intent(context, WriteCommunicationActivity::class.java).apply {
                putExtra("edit", editCommunication)
            }
    }
}