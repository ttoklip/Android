package com.umc.ttoklip.presentation.mypage.manageinfo

import android.content.Context
import android.graphics.Rect
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivityManageMyInfoBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManageMyInfoActivity :
    BaseActivity<ActivityManageMyInfoBinding>(R.layout.activity_manage_my_info) {
    private val viewModel: ManageMyInfoViewModel by viewModels()
    private lateinit var navController: NavController
    override fun initView() {
        initNavigator()
    }

    override fun initObserver() {

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

    private fun initNavigator(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

}