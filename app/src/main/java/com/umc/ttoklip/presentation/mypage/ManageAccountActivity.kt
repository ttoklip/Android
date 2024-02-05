package com.umc.ttoklip.presentation.mypage

import android.content.Context
import android.graphics.Rect
import android.text.Editable
import android.text.InputType.TYPE_CLASS_TEXT
import android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
import android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
import android.text.TextWatcher
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import androidx.core.view.isInvisible
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivityManageAccountBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Pattern

@AndroidEntryPoint
class ManageAccountActivity :
    BaseActivity<ActivityManageAccountBinding>(R.layout.activity_manage_account) {
    override fun initView() {
        binding.manageAccountInfoBackBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        setCurrentPasswordIcons(
            binding.inputCurrentPasswordEt,
            binding.currentPasswordVisibilityBtn
        )
        setCurrentPasswordIcons(binding.inputNewPasswordEt, binding.newPasswordVisibilityBtn)
        setCurrentPasswordIcons(
            binding.inputCheckNewPasswordEt,
            binding.inputCheckNewPasswordVisibilityBtn
        )
        passwordIconListener()
    }

    private fun passwordIconListener() {
        binding.currentPasswordVisibilityBtn.setOnClickListener {
            setPasswordIcon(
                binding.currentPasswordVisibilityBtn,
                it.tag.toString(),
                binding.inputCurrentPasswordEt
            )
        }

        binding.newPasswordVisibilityBtn.setOnClickListener {
            setPasswordIcon(
                binding.newPasswordVisibilityBtn,
                it.tag.toString(),
                binding.inputNewPasswordEt
            )
        }

        binding.inputCheckNewPasswordVisibilityBtn.setOnClickListener {
            setPasswordIcon(
                binding.inputCheckNewPasswordVisibilityBtn,
                it.tag.toString(),
                binding.inputCheckNewPasswordEt
            )
        }
    }

    private fun setPasswordIcon(btn: ImageButton, tag: String, edit: EditText) {
        when (tag) {
            PASSWORD_INVISIBLE_BLANK -> {
                btn.setImageResource(R.drawable.ic_eye_on_24)
                setTag(PASSWORD_VISIBLE_BLANK, btn, edit)
                edit.inputType = TYPE_TEXT_VARIATION_VISIBLE_PASSWORD or TYPE_CLASS_TEXT
            }

            PASSWORD_INVISIBLE -> {
                btn.setImageResource(R.drawable.ic_eye_on_black_24)
                setTag(PASSWORD_VISIBLE, btn, edit)
                edit.inputType = TYPE_TEXT_VARIATION_VISIBLE_PASSWORD or TYPE_CLASS_TEXT
            }

            PASSWORD_VISIBLE_BLANK -> {
                btn.setImageResource(R.drawable.ic_eye_off_24)
                setTag(PASSWORD_INVISIBLE_BLANK, btn, edit)
                edit.inputType = TYPE_TEXT_VARIATION_PASSWORD or TYPE_CLASS_TEXT
            }

            PASSWORD_VISIBLE -> {
                btn.setImageResource(R.drawable.ic_eye_off_black_24)
                setTag(PASSWORD_INVISIBLE, btn, edit)
                edit.inputType = TYPE_TEXT_VARIATION_PASSWORD or TYPE_CLASS_TEXT
            }
        }
    }

    private fun setCurrentPasswordIcons(edit: EditText, btn: ImageButton) {
        edit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                    if (it.isNotEmpty()) {
                        if (edit == binding.inputCheckNewPasswordEt || edit == binding.inputNewPasswordEt) {
                            binding.inputCheckNewPasswordVerifiedTv.isInvisible =
                                binding.inputCheckNewPasswordEt.text.toString() != binding.inputNewPasswordEt.text.toString()
                            if (edit == binding.inputNewPasswordEt) {
                                val str = edit.text.toString()
                                matchEnglish(str)
                                matchNumber(str)
                                matchSpecialCharacter(str)
                            }
                        }
                        when (edit.tag) {
                            PASSWORD_INVISIBLE_BLANK -> {
                                btn.setImageResource(R.drawable.ic_eye_off_black_24)
                                setTag(PASSWORD_INVISIBLE, btn, edit)
                            }

                            PASSWORD_VISIBLE_BLANK -> {
                                btn.setImageResource(R.drawable.ic_eye_on_black_24)
                                setTag(PASSWORD_VISIBLE, btn, edit)
                            }
                        }
                    } else {
                        if (edit == binding.inputNewPasswordEt) {
                            binding.containEnglishImg.setImageResource(R.drawable.ic_check_off_20)
                            binding.containEnglishTv.setTextColor(getColor(R.color.gray40))
                            binding.containNumberImg.setImageResource(R.drawable.ic_check_off_20)
                            binding.containNumberTv.setTextColor(getColor(R.color.gray40))
                            binding.containSpecialCharacterImg.setImageResource(R.drawable.ic_check_off_20)
                            binding.containSpecialCharacterTv.setTextColor(getColor(R.color.gray40))
                        }
                        when (edit.tag) {
                            PASSWORD_INVISIBLE -> {
                                btn.setImageResource(R.drawable.ic_eye_off_24)
                                setTag(PASSWORD_INVISIBLE_BLANK, btn, edit)
                            }

                            PASSWORD_VISIBLE -> {
                                btn.setImageResource(R.drawable.ic_eye_on_24)
                                setTag(PASSWORD_VISIBLE_BLANK, btn, edit)
                            }
                        }
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) = Unit
        })
    }

    private fun matchEnglish(password: String) {
        if (PASSWORD_ENGLISH_FORMAT.matcher(password)
                .find() && password.length >= PASSWORD_MIN_LENGTH
        ) {
            Log.d("str", "$password 영어 있음")
            binding.containEnglishImg.setImageResource(R.drawable.ic_check_on_20)
            binding.containEnglishTv.setTextColor(getColor(R.color.black))
        } else {
            Log.d("str", "$password 영어 없음")
            binding.containEnglishImg.setImageResource(R.drawable.ic_check_off_20)
            binding.containEnglishTv.setTextColor(getColor(R.color.gray40))
        }
    }

    private fun matchNumber(password: String) {
        if (PASSWORD_NUMBER_FORMAT.matcher(password)
                .find() && password.length >= PASSWORD_MIN_LENGTH
        ) {
            Log.d("str", "$password 숫자 있음")
            binding.containNumberImg.setImageResource(R.drawable.ic_check_on_20)
            binding.containNumberTv.setTextColor(getColor(R.color.black))
        } else {
            Log.d("str", "$password 숫자 없음")
            binding.containNumberImg.setImageResource(R.drawable.ic_check_off_20)
            binding.containNumberTv.setTextColor(getColor(R.color.gray40))
        }
    }

    private fun matchSpecialCharacter(password: String) {
        if (PASSWORD_SPECIAL_CHARACTER_FORMAT.matcher(password)
                .find() && password.length >= PASSWORD_MIN_LENGTH
        ) {
            Log.d("str", "$password 특수 있음")
            binding.containSpecialCharacterImg.setImageResource(R.drawable.ic_check_on_20)
            binding.containSpecialCharacterTv.setTextColor(getColor(R.color.black))
        } else {
            Log.d("str", "$password 특수 없음")
            binding.containSpecialCharacterImg.setImageResource(R.drawable.ic_check_off_20)
            binding.containSpecialCharacterTv.setTextColor(getColor(R.color.gray40))
        }
    }

    private fun setTag(status: String, btn: ImageButton, edit: EditText) {
        edit.tag = status
        btn.tag = status
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

    override fun initObserver() = Unit

    companion object {
        private const val PASSWORD_INVISIBLE_BLANK = "eye_off_blank"
        private const val PASSWORD_INVISIBLE = "eye_off"
        private const val PASSWORD_VISIBLE_BLANK = "eye_on_blank"
        private const val PASSWORD_VISIBLE = "eye_on"
        private const val PASSWORD_MIN_LENGTH = 8
        private const val PASSWORD_ENGLISH_REGEX = ".*[a-zA-Z]+.*"
        private const val PASSWORD_NUMBER_REGEX = ".*[0-9]+.*"
        private const val PASSWORD_SPECIAL_CHARACTER_REGEX =
            ".*[$@^!%*#?&.]+.*"
        private val PASSWORD_ENGLISH_FORMAT = Pattern.compile(PASSWORD_ENGLISH_REGEX)
        private val PASSWORD_NUMBER_FORMAT = Pattern.compile(PASSWORD_NUMBER_REGEX)
        private val PASSWORD_SPECIAL_CHARACTER_FORMAT =
            Pattern.compile(PASSWORD_SPECIAL_CHARACTER_REGEX)
    }
}