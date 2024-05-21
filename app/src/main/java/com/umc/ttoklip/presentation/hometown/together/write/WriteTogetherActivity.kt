package com.umc.ttoklip.presentation.hometown.together.write

import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.Rect
import android.icu.text.DecimalFormat
import android.net.Uri
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivityWriteTogetherBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.hometown.TradeLocationActivity
import com.umc.ttoklip.presentation.hometown.dialog.InputMaxMemberDialogFragment
import com.umc.ttoklip.presentation.hometown.dialog.TogetherDialog
import com.umc.ttoklip.presentation.honeytip.adapter.Image
import com.umc.ttoklip.presentation.honeytip.adapter.ImageRVA
import com.umc.ttoklip.presentation.dialog.ImageDialogFragment
import com.umc.ttoklip.presentation.hometown.together.read.ReadTogetherActivity
import com.umc.ttoklip.presentation.honeytip.read.ReadHoneyTipActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
                binding.tradingPlaceTv.text = if (!addressDetail.isNullOrBlank()) {
                    StringBuilder().append(address).append(" (").append(addressDetail).append(")")
                        .toString()
                } else {
                    StringBuilder().append(address).toString()
                }
            }
        }
    private val imageAdapter by lazy {
        ImageRVA(this, null)
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
    private val viewModel: WriteTogetherViewModel by viewModels<WriteTogetherViewModelImpl>()
    override fun initView() {
        binding.vm = viewModel as WriteTogetherViewModelImpl
        initImageRVA()
        addLink()
        addImage()
        binding.backBtn.setOnClickListener {
            finish()
        }

        var result = ""

        binding.totalPriceTv.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!TextUtils.isEmpty(s.toString()) && s.toString() != result){
                    result = s.toString().replace(",","")
                    viewModel.setTotalPrice(result.toLong())
                    result = AMOUNT_FORMAT.format(result.toDouble())
                    binding.totalPriceTv.setText(result)
                    binding.totalPriceTv.setSelection(result.length)
                }
                s?.let {
                    if (it.isBlank()) {
                        viewModel.setTotalPrice(0)
                        viewModel.checkDone()
                        result = ""
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) = Unit

        })

        binding.maxMemberTv.setOnClickListener {
            val bottomSheet = InputMaxMemberDialogFragment { member ->
                viewModel.setTotalMember(member.toLong())
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

        binding.tradingPlaceTv.setOnClickListener {
            val intent = Intent(this, TradeLocationActivity::class.java)
            activityResultLauncher.launch(intent)
        }
    }

    override fun initObserver() {
        with(lifecycleScope) {
            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.doneButtonActivated.collect {
                        Log.d("enable", it.toString())
                        binding.writeDoneBtn.isEnabled = it
                    }
                }
            }
            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.title.collect {
                        Log.d("text", it)
                        viewModel.checkDone()
                    }
                }
            }
            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.content.collect {
                        Log.d("content", it)
                        viewModel.checkDone()
                    }
                }
            }
            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.totalPrice.collect {
                        Log.d("price", it.toString())
                        viewModel.checkDone()
                    }
                }
            }
            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.totalMember.collect {
                        Log.d("member", it.toString())
                        viewModel.checkDone()
                    }
                }
            }
            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.dealPlace.collect {
                        viewModel.checkDone()
                    }
                }
            }
            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.openLink.collect {
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
                    viewModel.doneWriteTogether.collect {
                        if (it) {
                            val together = TogetherDialog()
                            together.setDialogClickListener(object :
                                TogetherDialog.TogetherDialogClickListener {
                                override fun onClick() {
                                    viewModel.writeTogether()
                                }
                            })
                            together.show(supportFragmentManager, together.tag)
                        }
                    }
                }
            }

            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED){
                    viewModel.postId.collect{
                        if(it != 0L) {
                            startActivity(
                                ReadTogetherActivity.newIntent(
                                    this@WriteTogetherActivity,
                                    it
                                )
                            )
                            finish()
                        }
                    }
                }
            }
        }
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
        Log.d("uri", uriList.toString())
        val images = uriList.map { Image(it, "") }
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

    companion object {
        private val AMOUNT_FORMAT = DecimalFormat("#,###")
    }
}