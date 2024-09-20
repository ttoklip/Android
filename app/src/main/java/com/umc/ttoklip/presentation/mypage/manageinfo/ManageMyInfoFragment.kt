package com.umc.ttoklip.presentation.mypage.manageinfo

import android.app.Activity
import android.content.ContentResolver
import android.content.ContentUris
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.navercorp.nid.NaverIdLoginSDK.applicationContext
import com.umc.ttoklip.R
import com.umc.ttoklip.TtoklipApplication
import com.umc.ttoklip.databinding.FragmentManageMyInfoBinding
import com.umc.ttoklip.presentation.base.BaseFragment
import com.umc.ttoklip.presentation.honeytip.write.WriteHoneyTipActivity
import com.umc.ttoklip.presentation.mypage.ChooseMainInterestDialogFragment
import com.umc.ttoklip.presentation.mypage.InputIndependentCareerDialogFragment
import com.umc.ttoklip.util.setOnSingleClickListener
import com.umc.ttoklip.util.tabTextToCategory
import com.umc.ttoklip.util.uriToFile
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

class ManageMyInfoFragment :
    BaseFragment<FragmentManageMyInfoBinding>(R.layout.fragment_manage_my_info) {
    private val viewModel: ManageMyInfoViewModel by activityViewModels()
    private var profileImage = Uri.EMPTY
    private var independentYear = 0
    private var independentMonth = 0
    private var category = mutableListOf<String>()
    private var address = ""
    private var locationX = 0
    private var locationY = 0
    private var nickname = ""
    private val navigator by lazy {
        findNavController()
    }
    private var isFetchMyPageInfo = false

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
        viewModel.getMyPageInfo()
        initViewListener()
        Log.d("jwt", TtoklipApplication.prefs.getString("jwt", ""))
    }

    private fun initViewListener() {
        binding.manageMyInfoBackBtn.setOnSingleClickListener {
            requireActivity().finish()
        }


        binding.inputIndependentCareerEt.setOnSingleClickListener {
            val bottomSheet = InputIndependentCareerDialogFragment { year, month ->
                independentYear = year
                independentMonth = month
                viewModel.editIndependentCareer(year, month)

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
            bottomSheet.show(childFragmentManager, bottomSheet.tag)
        }

        binding.findAddressBtn.setOnSingleClickListener {
            navigator.navigate(R.id.action_manageMyInfoFragment_to_myHomeTownAddressFragment)
        }

        binding.checkDuplicationBtn.setOnSingleClickListener {
            viewModel.nickCheck(binding.inputNicknameEt.text.toString())
        }

        binding.mainInterestGroup.setOnSingleClickListener {
            val bottomSheet = ChooseMainInterestDialogFragment { interests ->
                binding.mainInterestGroup.removeAllViews()
                addInterest(interests)
            }
            bottomSheet.show(childFragmentManager, bottomSheet.tag)
        }

        binding.finishUpdateProfileBtn.setOnSingleClickListener {
            TtoklipApplication.prefs.setString("nickname", binding.inputNicknameEt.text.toString())
            val categories = category.map { it -> it.tabTextToCategory() }
            val body = if (profileImage != Uri.EMPTY) {
                val file = requireContext().uriToFile(profileImage)
                val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("profileImage", file.name, requestFile)
            } else {
                null
            }
            viewModel.editMyPageInfo(
                address,
                binding.inputNicknameEt.text.toString(),
                categories,
                body,
                independentYear, independentMonth
            )
        }

        binding.manageProfileImg.setOnSingleClickListener {

            pickMultipleMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            binding.finishUpdateProfileBtn.isEnabled = true
        }
        validateFinishUpdateProfileBtn()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        if (requestCode == 1) {
            if (data != null) {
                binding.inputAddressTv.text = data.getStringExtra("location")
                address = data.getStringExtra("location").toString()
                locationX = data.getIntExtra("locationX", 0)
                locationY = data.getIntExtra("locationY", 0)
            }
        }
    }

    @OptIn(FlowPreview::class)
    override fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.myPageInfo.collectLatest {
                    Log.d("myinfo", it.toString())
                    with(binding) {
                        inputNicknameEt.setText(it.nickname)
                        nickname = it.nickname
                        inputIndependentCareerEt.text =
                            if (it.independentYear == 0) {
                                "${it.independentMonth}개월"
                            } else {
                                "${it.independentYear}년 ${it.independentMonth}개월"
                            }
                        inputAddressTv.text = it.street
                        viewModel.setAddress(it.street.toString(), false)
                        address = it.street.toString()
                        independentYear = it.independentYear
                        independentMonth = it.independentMonth
                        Glide.with(requireContext())
                            .load(it.profileImgUrl)
                            .into(binding.manageProfileImg)
                        addInterest(it.interests.map { it.categoryName })
                        isFetchMyPageInfo = true
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.nickok.collect {
                    if (it) {
                        binding.signup4NickokTv.visibility = View.VISIBLE
                        binding.signup4NicknotokTv.visibility = View.GONE
                    } else {
                        binding.signup4NickokTv.visibility = View.GONE
                        binding.signup4NicknotokTv.visibility = View.VISIBLE
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.myPageEvent.collect {
                    requireActivity().finish()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.toast.collect {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
        }


        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.isButtonEnabled.collect{
                    binding.finishUpdateProfileBtn.isEnabled = it
                    Log.d("ise", it.toString())
                }
            }
        }
    }

    companion object {
        private const val ZERO_CAREER = 0
    }

    private fun updateImages() {
        binding.manageProfileImg.setImageURI(profileImage)
    }


    private fun deleteImage(imageUri: Uri): Boolean {
        val contentResolver: ContentResolver = requireContext().contentResolver

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

    private fun addInterest(list: List<String>) {
        category = list.toMutableList()
        viewModel.editCategory(list)
        list.forEach { interest ->
            val chip = Chip(requireContext())
            chip.text = interest
            chip.setTextAppearance(R.style.TextAppearance_App_12sp_700)
            chip.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            chip.setChipBackgroundColorResource(R.color.yellow)
            chip.setChipStrokeColorResource(R.color.yellow)
            binding.mainInterestGroup.addView(chip)
        }
    }

    private fun validateFinishUpdateProfileBtn() {
        binding.inputNicknameEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (!isFetchMyPageInfo) {
                    Log.d("isFetchMyPageInfo", "false")
                    return
                }
                Log.d("et s", s.toString())
                viewModel.editNickname(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }
}