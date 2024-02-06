package com.umc.ttoklip.presentation.mypage

import android.text.Editable
import android.text.InputType.TYPE_CLASS_TEXT
import android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
import android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.InputMethodManager
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivityManageAccountBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManageAccountActivity :
    BaseActivity<ActivityManageAccountBinding>(R.layout.activity_manage_account) {
    override fun initView() {
        binding.manageAccountInfoBackBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.root.setOnClickListener {
            removeKeyboard()
        }
        binding.manageAccountInfoFrame.setOnClickListener {
            removeKeyboard()
        }
        binding.inputCurrentPasswordEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                    if (it.isNotEmpty()) {
                        when (binding.inputCurrentPasswordEt.tag) {
                            PASSWORD_INVISIBLE_BLANK -> {
                                binding.currentPasswordVisibilityBtn.setImageResource(R.drawable.ic_eye_off_black_24)
                                setTag(PASSWORD_INVISIBLE)
                            }

                            PASSWORD_VISIBLE_BLANK -> {
                                //검은 눈 올 예정
                                binding.currentPasswordVisibilityBtn.setImageResource(R.drawable.ic_eye_on_24)
                                setTag(PASSWORD_VISIBLE)
                            }
                        }
                    } else {
                        when (binding.inputCurrentPasswordEt.tag) {
                            PASSWORD_INVISIBLE -> {
                                binding.currentPasswordVisibilityBtn.setImageResource(R.drawable.ic_eye_off_24)
                                setTag(PASSWORD_INVISIBLE_BLANK)
                            }

                            PASSWORD_VISIBLE_BLANK -> {
                                binding.currentPasswordVisibilityBtn.setImageResource(R.drawable.ic_eye_on_24)
                                setTag(PASSWORD_VISIBLE_BLANK)
                            }
                        }
                    }
                }
                Log.d("visibility et", binding.inputCurrentPasswordEt.tag.toString())
                Log.d("visibility btn", binding.currentPasswordVisibilityBtn.tag.toString())
            }

            override fun afterTextChanged(s: Editable?) = Unit
        })

        binding.currentPasswordVisibilityBtn.setOnClickListener {
            when (binding.currentPasswordVisibilityBtn.tag) {
                PASSWORD_INVISIBLE_BLANK -> {
                    binding.currentPasswordVisibilityBtn.setImageResource(R.drawable.ic_eye_on_24)
                    setTag(PASSWORD_VISIBLE_BLANK)
                    binding.inputCurrentPasswordEt.inputType =
                        TYPE_TEXT_VARIATION_VISIBLE_PASSWORD or TYPE_CLASS_TEXT
                }

                PASSWORD_INVISIBLE -> {
                    //검은 눈 올 예정
                    binding.currentPasswordVisibilityBtn.setImageResource(R.drawable.ic_eye_on_24)
                    setTag(PASSWORD_VISIBLE)
                    binding.inputCurrentPasswordEt.inputType =
                        TYPE_TEXT_VARIATION_VISIBLE_PASSWORD or TYPE_CLASS_TEXT
                }

                PASSWORD_VISIBLE_BLANK -> {
                    binding.currentPasswordVisibilityBtn.setImageResource(R.drawable.ic_eye_off_24)
                    setTag(PASSWORD_INVISIBLE_BLANK)
                    binding.inputCurrentPasswordEt.inputType =
                        TYPE_TEXT_VARIATION_PASSWORD or TYPE_CLASS_TEXT
                }

                PASSWORD_VISIBLE -> {
                    binding.currentPasswordVisibilityBtn.setImageResource(R.drawable.ic_eye_off_black_24)
                    setTag(PASSWORD_INVISIBLE)
                    binding.inputCurrentPasswordEt.inputType =
                        TYPE_TEXT_VARIATION_PASSWORD or TYPE_CLASS_TEXT
                }
            }
            Log.d("visibility et", binding.inputCurrentPasswordEt.tag.toString())
            Log.d("visibility btn", binding.currentPasswordVisibilityBtn.tag.toString())
        }
    }

    private fun setTag(status: String) {
        binding.inputCurrentPasswordEt.tag = status
        binding.currentPasswordVisibilityBtn.tag = status
    }

    private fun removeKeyboard() {
        val imm: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

    override fun initObserver() = Unit

    companion object {
        private const val PASSWORD_INVISIBLE_BLANK = "eye_off_blank"
        private const val PASSWORD_INVISIBLE = "eye_off"
        private const val PASSWORD_VISIBLE_BLANK = "eye_on_blank"
        private const val PASSWORD_VISIBLE = "eye_on"

    }
}