package com.umc.ttoklip.presentation.hometown.together.write

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Build
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.umc.ttoklip.R
import com.umc.ttoklip.data.model.town.EditTogether
import com.umc.ttoklip.databinding.ActivityWriteTogetherBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.honeytip.adapter.Image
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WriteTogetherActivity :
    BaseActivity<ActivityWriteTogetherBinding>(R.layout.activity_write_together) {
    private lateinit var navController: NavController
    private val viewModel: WriteTogetherViewModel by viewModels<WriteTogetherViewModelImpl>()

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
    override fun initView() {
        initNavigator()
        initEditView()
    }

    override fun initObserver() {

    }

    private fun initEditView(){
        val editTogether = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("edit", EditTogether::class.java)
        } else {
            intent.getSerializableExtra("edit") as? EditTogether
        }

        if(editTogether != null){
            with(editTogether) {
                viewModel.setTitle(title)
                viewModel.setContent(content)
                val images = image.map { Image(it.imageId, it.imageUrl) }
                viewModel.setImage(images)
                viewModel.setAddress(address)
                viewModel.setTotalMember(member.toLong())
                viewModel.setTotalPrice(price.toLong())
                viewModel.setOpenLink(url)
                viewModel.setIsEdit(true)
                viewModel.setDoneButtonActivated(true)
                viewModel.setPostId(postId)
            }
        }
    }

    private fun initNavigator(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    companion object {
        fun newIntent(context: Context, editTogether: EditTogether): Intent =
            Intent(context, WriteTogetherActivity::class.java).apply{
                putExtra("edit", editTogether)
            }
    }
}