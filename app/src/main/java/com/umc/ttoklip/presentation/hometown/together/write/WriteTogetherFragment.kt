package com.umc.ttoklip.presentation.hometown.together.write

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.icu.text.DecimalFormat
import android.net.Uri
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.api.LogDescriptor
import com.umc.ttoklip.R
import com.umc.ttoklip.TtoklipApplication
import com.umc.ttoklip.databinding.FragmentWriteTogetherBinding
import com.umc.ttoklip.presentation.base.BaseFragment
import com.umc.ttoklip.presentation.dialog.ImageDialogFragment
import com.umc.ttoklip.presentation.hometown.dialog.InputMaxMemberDialogFragment
import com.umc.ttoklip.presentation.hometown.dialog.TogetherDialog
import com.umc.ttoklip.presentation.hometown.together.read.ReadTogetherActivity
import com.umc.ttoklip.presentation.hometown.together.write.adapter.TogetherImageRVA
import com.umc.ttoklip.presentation.honeytip.adapter.Image
import com.umc.ttoklip.presentation.honeytip.adapter.ImageRVA
import com.umc.ttoklip.util.isValidUri
import com.umc.ttoklip.util.setOnSingleClickListener
import com.umc.ttoklip.util.showToast
import com.umc.ttoklip.util.uriToFile
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

@AndroidEntryPoint
class WriteTogetherFragment: BaseFragment<FragmentWriteTogetherBinding>(R.layout.fragment_write_together) {

    private val imageAdapter by lazy {
        TogetherImageRVA(requireContext(), null)
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
    private val viewModel: WriteTogetherViewModel by activityViewModels<WriteTogetherViewModelImpl>()

    private val navigator by lazy {
        findNavController()
    }

    private var isEdit = false
    override fun initView() {
        binding.vm = viewModel as WriteTogetherViewModelImpl
        initImageRVA()
        addLink()
        addImage()

        binding.backBtn.setOnSingleClickListener {
            requireActivity().finish()
        }

        binding.totalPriceTv.addTextChangedListener(object : TextWatcher {
            private var result = ""

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty() && s.toString() != result) {
                    // 숫자로 변환하기 전에 콤마 제거
                    val plainNumber = s.toString().replace(",", "")
                    try {
                        // 숫자로 변환
                        viewModel.setTotalPrice(plainNumber.toLong())

                        // 콤마를 추가하여 형식 지정
                        result = AMOUNT_FORMAT.format(plainNumber.toDouble())
                        binding.totalPriceTv.setText(result)
                        binding.totalPriceTv.setSelection(result.length)
                    } catch (e: NumberFormatException) {
                        // 잘못된 형식의 숫자일 경우 예외 처리
                        Log.e("NumberFormatException", e.message.toString())
                    }
                } else if (s.isNullOrBlank()) {
                    viewModel.setTotalPrice(0)
                    viewModel.checkDone()
                    result = ""
                }
            }

            override fun afterTextChanged(s: Editable?) = Unit
        })

        binding.maxMemberTv.setOnSingleClickListener {
            val bottomSheet = InputMaxMemberDialogFragment { member ->
                viewModel.setTotalMember(member.toLong())
                binding.maxMemberTv.text = getString(R.string.max_member_format, member)
                binding.maxMemberTv.compoundDrawables.forEach { drawable ->
                    if (drawable != null) {
                        drawable.colorFilter =
                            PorterDuffColorFilter(
                                requireContext().getColor(R.color.black),
                                PorterDuff.Mode.SRC_IN
                            )
                    }
                }
                binding.maxMemberTv.hint = ""
            }
            bottomSheet.show(childFragmentManager, bottomSheet.tag)
        }

        binding.tradingPlaceTv.setOnSingleClickListener {
            navigator.navigate(R.id.action_writeTogetherFragment_to_tradeLocationFragment)
        }

        binding.writeDoneBtn.setOnSingleClickListener {
            if (isEdit) {
                viewModel.patchTogether(emptyList())
            } else {
                val together = TogetherDialog()
                together.setDialogClickListener(object :
                    TogetherDialog.TogetherDialogClickListener {
                    override fun onClick() {
                        viewModel.writeTogether(makeImageParts())
                    }
                })
                together.show(childFragmentManager, together.tag)
            }
        }
    }

    private fun makeImageParts(): MutableList<MultipartBody.Part?>{
        val imageParts = mutableListOf<MultipartBody.Part?>()
        val images =
            imageAdapter.currentList.filterIsInstance<Image>().map { it.src }
                .filter { it.isValidUri() }.toList()

        Log.d("images", images.toString())
        if(images.isNotEmpty()) {
            images.forEach { uri ->
                val file = requireContext().uriToFile(Uri.parse(uri))
                val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                val body =
                    MultipartBody.Part.createFormData("images", file.name, requestFile)
                imageParts.add(body)
            }
        }
        return imageParts
    }

    override fun initObserver() {
        with(viewLifecycleOwner.lifecycleScope) {
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
                repeatOnLifecycle(Lifecycle.State.STARTED){
                    viewModel.isEdit.collect{
                        isEdit = it
                        if(isEdit){
                            with(binding){
                                writeDoneBtn.text = "수정완료"
                                requiredParameterFrame.visibility = View.GONE
                                titleLine.visibility = View.GONE
                                imageRv.visibility = View.GONE
                                addLinkBtn.visibility  = View.GONE
                                addImageBtn.visibility = View.GONE
                            }
                        }
                    }
                }
            }
            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.totalPrice.collect {
                        if(it!=0L) {
                            binding.totalPriceTv.setText(AMOUNT_FORMAT.format(it))
                            Log.d("price", it.toString())
                            viewModel.checkDone()
                        }
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
                        Log.d("dealPlace", it)
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
                        if (it.isNotEmpty()) {
                            if(!isEdit) {
                                binding.imageRv.visibility = View.VISIBLE
                                binding.addImageBtn.visibility = View.GONE
                                Log.d("uri image", it.toString())
                                imageAdapter.submitList(it.toList())
                            }
                        }
                    }
                }
            }

            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED){
                    viewModel.postId.collectLatest{
                        Log.d("post id together", it.toString())
                        if(isEdit){
                            return@collectLatest
                        }
                        if(it != 0L) {
                            startActivity(
                                ReadTogetherActivity.newIntent(
                                    requireContext(),
                                    it
                                )
                            )
                            requireActivity().finish()
                        }
                    }
                }
            }

            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED){
                    viewModel.isEditDone.collect{
                        if(it){
                            startActivity(ReadTogetherActivity.newIntent(requireContext(), viewModel.postId.value))
                            requireActivity().finish()
                        }
                    }
                }
            }

            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED){
                    viewModel.includeSwear.collect{
                        showToast(it)
                    }
                }
            }
        }
    }

    private fun addLink() {
        binding.addLinkBtn.setOnSingleClickListener {
            binding.addLinkBtn.visibility = View.GONE
            binding.inputUrlBtn.visibility = View.VISIBLE
        }
    }

    private fun initImageRVA() {
        binding.imageRv.adapter = imageAdapter
    }

    private fun addImage() {
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
                imageDialog.show(childFragmentManager, imageDialog.toString())
            } else {
                binding.imageRv.visibility = View.VISIBLE
                pickMultipleMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
        }
    }

    private fun updateImages(uriList: List<Uri>) {
        Log.d("uri", uriList.toString())
        val images = uriList.map { Image(0, it.toString()) }
        viewModel.addImages(images)
    }

    companion object {
        private val AMOUNT_FORMAT = DecimalFormat("#,###")
    }
}