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
import com.umc.ttoklip.presentation.honeytip.adapter.Image
import com.umc.ttoklip.presentation.honeytip.adapter.ImageRVA
import com.umc.ttoklip.util.isValidUri
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
        ImageRVA(requireContext(), null)
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
    override fun initView() {
        binding.vm = viewModel as WriteTogetherViewModelImpl
        initImageRVA()
        addLink()
        addImage()

        binding.backBtn.setOnClickListener {
            requireActivity().finish()
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
                                requireContext().getColor(R.color.black),
                                PorterDuff.Mode.SRC_IN
                            )
                    }
                }
                binding.maxMemberTv.hint = ""
            }
            bottomSheet.show(childFragmentManager, bottomSheet.tag)
        }

        binding.tradingPlaceTv.setOnClickListener {
            navigator.navigate(R.id.action_writeTogetherFragment_to_tradeLocationFragment)
        }

        binding.writeDoneBtn.setOnClickListener {
            val together = TogetherDialog()
            together.setDialogClickListener(object :
                TogetherDialog.TogetherDialogClickListener {
                override fun onClick() {
                    val imageParts = mutableListOf<MultipartBody.Part?>()
                    val images = imageAdapter.currentList.filterIsInstance<Image>().map { it.src }
                        .filter { it.isValidUri() }.toList()

                    images.forEach { uri ->
                        val file = requireContext().uriToFile(Uri.parse(uri))
                        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                        val body = MultipartBody.Part.createFormData("images", file.name, requestFile)
                        imageParts.add(body)
                    }
                    viewModel.writeTogether(imageParts)
                }
            })
            together.show(childFragmentManager, together.tag)
        }
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
                repeatOnLifecycle(Lifecycle.State.STARTED){
                    viewModel.postId.collectLatest{
                        Log.d("post id together", it.toString())
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