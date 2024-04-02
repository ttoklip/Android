package com.umc.ttoklip.presentation.signup.fragments

import android.app.Application
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.umc.ttoklip.R
import com.umc.ttoklip.TtoklipApplication
import com.umc.ttoklip.databinding.FragmentSignup4Binding
import com.umc.ttoklip.presentation.base.BaseFragment
import com.umc.ttoklip.presentation.honeytip.dialog.ImageDialogFragment
import com.umc.ttoklip.presentation.mypage.ChooseMainInterestDialogFragment
import com.umc.ttoklip.presentation.mypage.InputIndependentCareerDialogFragment
import com.umc.ttoklip.presentation.signup.SignupActivity
import com.umc.ttoklip.presentation.signup.SignupViewModel
import com.umc.ttoklip.presentation.signup.location.LocationActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URI


@AndroidEntryPoint
class Signup4Fragment : BaseFragment<FragmentSignup4Binding>(R.layout.fragment_signup4) {

    private val viewModel: SignupViewModel by activityViewModels()
    private lateinit var interestArray: ArrayList<String>

    private var independentCareerYear: Int? = null
    private var independentCareerMonth: Int? = null

    override fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.nickcheckbtn.collect {
                        if (it) {
                            delay(200)
                            viewModel.nickok.collect {
                                if (it) {
                                    binding.signup4NickokTv.visibility = View.VISIBLE
                                    binding.signup4NicknotokTv.visibility = View.GONE
                                } else {
                                    binding.signup4NickokTv.visibility = View.GONE
                                    binding.signup4NicknotokTv.visibility = View.VISIBLE
                                }
                                nextok()
                            }
                        }
                    }
                }
            }
        }
    }

    override fun initView() {
        interestArray = ArrayList()
        val activity = activity as SignupActivity
        activity?.setProg(2)

        initIndependent()

        binding.signup4NickcheckButton.setOnClickListener {
            viewModel.nickCheck(binding.signup4NicknameEt.text.toString())
            viewModel.nickcheckclick()
        }

        binding.signup4IndependenceEt.setOnClickListener {
            viewModel.independentCheck(true)
            val bottomSheet = InputIndependentCareerDialogFragment { year, month ->
                independentCareerYear = year
                independentCareerMonth = month
                initIndependent()
            }
            bottomSheet.show(activity.supportFragmentManager, bottomSheet.tag)
            binding.signup4IndependenceEt.setTextColor(
                ContextCompat.getColor(
                    activity,
                    R.color.black
                )
            )
            viewModel.independentCheck(true)
            nextok()
        }

        binding.signup4InterestGroup.setOnClickListener {
            binding.signup4InterestDescTv.visibility = View.GONE
            binding.signup4InterestIconIv.visibility = View.GONE
            viewModel.interestCheck(false)
            //chip 클릭해야 발생
            val bottomSheet = ChooseMainInterestDialogFragment { interests ->
                binding.signup4InterestGroup.removeAllViews()
                interestArray.clear()
                interests.forEach { interest ->
                    val chip = Chip(activity)
                    chip.text = interest
                    chip.setTextAppearance(R.style.TextAppearance_App_12sp_700)
                    chip.setTextColor(ContextCompat.getColor(activity, R.color.black))
                    chip.setChipBackgroundColorResource(R.color.yellow)
                    chip.setChipStrokeColorResource(R.color.yellow)
                    binding.signup4InterestGroup.addView(chip)

                    val interestType =
                        if (interest.equals("집안일")) "HOUSEWORK"
                        else if (interest.equals("레시피")) "RECIPE"
                        else if (interest.equals("안전한 생활")) "SAFE_LIVING"
                        else "WELFARE_POLICY"
                    interestArray.add(interestType)
                }
                if (interests.isNotEmpty()) viewModel.interestCheck(true)
                nextok()
            }
            // 마이페이지에서 관심사 불러오기 위해 작성
            TtoklipApplication.prefs.setString("interest", interestArray.toString())
            bottomSheet.show(activity.supportFragmentManager, bottomSheet.tag)
        }

        binding.signup4CameraIv.setOnClickListener {
            setProfileImage(activity)
        }
        binding.signup4ProfileImageIv.setOnClickListener {
            setProfileImage(activity)
        }

        binding.signup4NextBtn.setOnClickListener {
            if (
                viewModel.independentCareerok.value &&
                viewModel.interestok.value &&
                viewModel.nickok.value
            ) {
                val bundle=Bundle()
                bundle.putString("nickname",viewModel.nickname.value)
                bundle.putStringArrayList("interest",viewModel.categories.value)
                bundle.putString("imageUri",viewModel.profileImage.value)
                bundle.putInt("independentCareerYear",viewModel.independenctYear.value)
                bundle.putInt("independentCareerMonth",viewModel.independenctMonth.value)
                val intent=Intent(activity, LocationActivity::class.java)
                intent.putExtra("userInfo",bundle)
                startActivity(intent)
                activity.finish()

                //navigate to signup fragment 5
//                val bundle = Bundle()
//                bundle.putString("nickname", binding.signup4NicknameEt.text.toString())
//                bundle.putStringArrayList("interest", interestArray)
//                bundle.putString(
//                    "imageUri",
//                    if (imageSource.isNotEmpty()) imageSource
//                    else ""
////                        Uri.fromFile(File("//android_asset/profile_image_default.png")).toString()
//                )
//                bundle.putInt("independentCareerYear", independentCareerYear!!)
//                bundle.putInt("independentCareerMonth", independentCareerMonth!!)
//
//                findNavController().navigate(
//                    R.id.action_signup4_fragment_to_signup5_fragment,
//                    bundle
//                )
            }
        }
    }

    private fun initIndependent(){
        if (independentCareerYear != null || independentCareerMonth != null) {
            if (independentCareerYear != ZERO_CAREER && independentCareerMonth != ZERO_CAREER) {
                binding.signup4IndependenceEt.text =
                    getString(
                        R.string.my_independent_career_base_format,
                        independentCareerYear,
                        independentCareerMonth
                    )
            } else if (independentCareerYear != ZERO_CAREER) {
                binding.signup4IndependenceEt.text =
                    getString(R.string.my_independent_career_year_format, independentCareerYear)
            } else if (independentCareerMonth != ZERO_CAREER) {
                binding.signup4IndependenceEt.text =
                    getString(R.string.my_independent_career_month_format, independentCareerMonth)
            } else {
                binding.signup4IndependenceEt.text =
                    getString(R.string.my_independent_career_month_format, ZERO_CAREER)
            }
            binding.signup4IndependenceEt.setTextColor(
                ContextCompat.getColor(
                    requireActivity(),
                    R.color.black
                )
            )
            viewModel.independentCheck(true)
            nextok()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context
    }

    private var imageSource: String = ""
    private lateinit var context: Context
    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                Glide.with(this).load(uri)
                    .circleCrop()
                    .into(binding.signup4ProfileImageIv)
                imageSource = getRealPathFromUri(uri, context)
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }

    private fun setProfileImage(activity: SignupActivity) {
        val imageDialog = ImageDialogFragment()
        imageDialog.setDialogClickListener(object : ImageDialogFragment.DialogClickListener {
            override fun onClick() {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
        })
        imageDialog.show(activity.supportFragmentManager, imageDialog.toString())
    }

    fun getRealPathFromUri(contentUri: Uri, context: Context): String {
        val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
        context.contentResolver.takePersistableUriPermission(contentUri, flag)
        var cursor: Cursor? = null
        try {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            cursor = context.contentResolver.query(contentUri, proj, null, null, null)
            val columnIndex = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            Log.i("ColumnIndex", columnIndex.toString())
            if (cursor != null && cursor.moveToFirst()) {
                return cursor.getString(columnIndex!!)
            }
        } finally {
            cursor?.close()
        }
        return ""
    }

    private fun nextok() {
        if (viewModel.nickok.value &&
            viewModel.independentCareerok.value &&
            viewModel.interestok.value
        ) {
            binding.signup4NextBtn.isClickable = true
            binding.signup4NextBtn.setBackgroundResource(R.drawable.rectangle_corner_10_yellow)
            binding.signup4NextBtn.setTextAppearance(R.style.TextAppearance_App_16sp_700)
        } else {
            binding.signup4NextBtn.isClickable = false
            binding.signup4NextBtn.setBackgroundResource(R.drawable.rectangle_corner_10_strok_1_black)
            binding.signup4NextBtn.setTextAppearance(R.style.TextAppearance_App_16sp_500)
        }
    }

    companion object {
        private const val ZERO_CAREER = 0
    }
}