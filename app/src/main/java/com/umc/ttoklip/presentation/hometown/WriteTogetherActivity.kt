package com.umc.ttoklip.presentation.hometown

import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.Rect
import android.icu.text.DecimalFormat
import android.net.Uri
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivityWriteTogetherBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.hometown.dialog.InputMaxMemberDialogFragment
import com.umc.ttoklip.presentation.honeytip.adapter.Image
import com.umc.ttoklip.presentation.honeytip.adapter.ImageRVA
import com.umc.ttoklip.presentation.honeytip.dialog.ImageDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WriteTogetherActivity :
    BaseActivity<ActivityWriteTogetherBinding>(R.layout.activity_write_together) {
    private val activityResultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val addressIntent = it.data
            addressIntent?.let { aIntent ->
                val address = aIntent.getStringExtra("address")
                val addressDetail = aIntent.getStringExtra("addressDetail")
                binding.tradingPlaceTv.setTextColor(getColor(R.color.black))
                binding.tradingPlaceTv.compoundDrawables.forEach { drawable ->
                    if (drawable != null) {
                        drawable.colorFilter =
                            PorterDuffColorFilter(getColor(R.color.black), PorterDuff.Mode.SRC_IN)
                    }
                }
                binding.tradingPlaceTv.text =
                    StringBuilder().append(address).append(" ").append(addressDetail).toString()
            }
        }
    private val imageAdapter by lazy {
        ImageRVA(null)
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
        addLink()
        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.totalPriceTv.setOnEditorActionListener { v, actionId, event ->
            var current = binding.totalPriceTv.text.toString()
            if (current.contains(",")) {
                current = current.replace(",", "")
            }
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                Log.d("price", current)
                if (current.length > 3) {
                    val currentAmount = AMOUNT_FORMAT.format(current.toLong())
                    binding.totalPriceTv.setText(currentAmount)
                    binding.totalPriceTv.compoundDrawables.forEach { drawable ->
                        if (drawable != null) {
                            drawable.colorFilter =
                                PorterDuffColorFilter(
                                    getColor(R.color.black),
                                    PorterDuff.Mode.SRC_IN
                                )
                        }
                    }
                }
            }
            false
        }

        binding.maxMemberTv.setOnClickListener {
            val bottomSheet = InputMaxMemberDialogFragment { member ->
                binding.maxMemberTv.text = getString(R.string.max_member_format, member)
                binding.maxMemberTv.compoundDrawables.forEach { drawable ->
                    if (drawable != null) {
                        drawable.colorFilter =
                            PorterDuffColorFilter(
                                getColor(R.color.black),
                                PorterDuff.Mode.SRC_IN
                            )
                    }
                }
                binding.maxMemberTv.hint = ""
            }
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }

        binding.openChatLinkTv.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                binding.openChatLinkTv.compoundDrawables.forEach { drawable ->
                    if (drawable != null) {
                        drawable.colorFilter =
                            PorterDuffColorFilter(
                                getColor(R.color.black),
                                PorterDuff.Mode.SRC_IN
                            )
                    }
                }
            }
            false
        }

        binding.tradingPlaceTv.setOnClickListener {
            val intent = Intent(this, TradeLocationActivity::class.java)
            activityResultLauncher.launch(intent)
        }
    }

    override fun initObserver() {

    }

    private fun addLink() {
        binding.addLinkBtn.setOnClickListener {
            binding.addLinkBtn.visibility = View.GONE
            binding.inputUrlBtn.visibility = View.VISIBLE
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

    companion object {
        private val AMOUNT_FORMAT = DecimalFormat("#,###")
    }
}