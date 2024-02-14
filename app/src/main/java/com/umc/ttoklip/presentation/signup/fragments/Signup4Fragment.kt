package com.umc.ttoklip.presentation.signup.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.chip.Chip
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.FragmentSignup4Binding
import com.umc.ttoklip.presentation.base.BaseFragment
import com.umc.ttoklip.presentation.honeytip.dialog.ImageDialogFragment
import com.umc.ttoklip.presentation.mypage.ChooseMainInterestDialogFragment
import com.umc.ttoklip.presentation.mypage.InputIndependentCareerDialogFragment
import com.umc.ttoklip.presentation.signup.LocationActivity
import com.umc.ttoklip.presentation.signup.SignupActivity
import com.umc.ttoklip.presentation.signup.SignupViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Signup4Fragment : BaseFragment<FragmentSignup4Binding>(R.layout.fragment_signup4) {

    private val viewModel: SignupViewModel by viewModels()
    private lateinit var interestArray: ArrayList<String>

    private var independentCareerYear: Int? = null
    private var independentCareerMonth: Int? = null

    private var nickcheckbtn: Boolean = false

    override fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.nickok.collect {
                        if (nickcheckbtn) {
                            if (it) {
                                binding.signup4NickokTv.visibility = View.VISIBLE
                                binding.signup4NicknotokTv.visibility = View.GONE
                            } else {
                                binding.signup4NickokTv.visibility = View.GONE
                                binding.signup4NicknotokTv.visibility = View.VISIBLE
                            }
                            nextok()
                        } else {
                            binding.signup4NickokTv.visibility = View.GONE
                            binding.signup4NicknotokTv.visibility = View.GONE
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

        binding.signup4NickcheckButton.setOnClickListener {
            viewModel.nickCheck(binding.signup4NicknameEt.text.toString())
            nickcheckbtn = true
        }

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
                    activity,
                    R.color.black
                )
            )
            viewModel.independentCheck(true)
            nextok()
        }

        binding.signup4IndependenceEt.setOnClickListener {
            viewModel.independentCheck(true)
            val bottomSheet = InputIndependentCareerDialogFragment { year, month ->
                if (year != ZERO_CAREER && month != ZERO_CAREER) {
                    binding.signup4IndependenceEt.text =
                        getString(R.string.my_independent_career_base_format, year, month)
                } else if (year != ZERO_CAREER) {
                    binding.signup4IndependenceEt.text =
                        getString(R.string.my_independent_career_year_format, year)
                } else if (month != ZERO_CAREER) {
                    binding.signup4IndependenceEt.text =
                        getString(R.string.my_independent_career_month_format, month)
                } else {
                    binding.signup4IndependenceEt.text =
                        getString(R.string.my_independent_career_month_format, ZERO_CAREER)
                }
                independentCareerYear = year
                independentCareerMonth = month
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

                    interestArray.add(interest)
                }
                if (interests.isNotEmpty()) viewModel.interestCheck(true)
                nextok()
            }
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
                val bundle= Bundle()
                bundle.putString("nickname",viewModel.nickname.value)
                bundle.putStringArrayList("interest",viewModel.categories.value)
                bundle.putString("imageUri",viewModel.profileImage.value)
                bundle.putInt("independentCareerYear",viewModel.independenctYear.value)
                bundle.putInt("independentCareerMonth",viewModel.independenctMonth.value)

                findNavController().navigate(R.id.action_signup4_fragment_to_signup5_fragment,bundle)
            }
        }
    }

    private lateinit var imageUri: Uri
    private val pickMedia= registerForActivityResult(ActivityResultContracts.PickVisualMedia()){ uri ->
        if (uri!=null) {
            imageUri=uri
            Glide.with(this).load(uri)
                .circleCrop()
                .into(binding.signup4ProfileImageIv)
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