package com.umc.ttoklip.presentation.hometown

import android.content.Context
import android.graphics.Rect
import android.net.Uri
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivityWriteCommunicationBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.honeytip.adapter.Image
import com.umc.ttoklip.presentation.honeytip.adapter.ImageRVA
import com.umc.ttoklip.presentation.honeytip.dialog.ImageDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WriteCommunicationActivity :
    BaseActivity<ActivityWriteCommunicationBinding>(R.layout.activity_write_communication) {
    private val imageAdapter by lazy {
        ImageRVA({})
    }

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
        initImageRVA()
        addImage()

        binding.backBtn.setOnClickListener {
            finish()
        }
    }

    override fun initObserver() {

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
        val images = uriList.map { Image(it) }
        val updatedImages = imageAdapter.currentList.toMutableList().apply { addAll(images) }
        imageAdapter.submitList(updatedImages)
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
}