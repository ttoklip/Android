package com.umc.ttoklip.presentation.honeytip.read

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil.setContentView
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivityReadBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.honeytip.BOARD
import com.umc.ttoklip.presentation.honeytip.DetailHoneyTipFragment
import com.umc.ttoklip.presentation.honeytip.HONEY_TIP
import com.umc.ttoklip.presentation.honeytip.HoneyTipViewModel
import com.umc.ttoklip.presentation.honeytip.dialog.DeleteDialogFragment
import com.umc.ttoklip.presentation.honeytip.dialog.ImageDialogFragment
import com.umc.ttoklip.presentation.honeytip.dialog.ReportDialogFragment
import com.umc.ttoklip.presentation.honeytip.write.WriteHoneyTipFragment

class ReadActivity : BaseActivity<ActivityReadBinding>(R.layout.activity_read) {
    override fun initView() {
        binding.view = this
        binding.reportBtn.bringToFront()
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, DetailHoneyTipFragment())
            .commit()
        setTitle()

        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.dotBtn.setOnClickListener {
            /*binding.reportBtn.isVisible = true*/

            binding.honeyTipMenu.apply {
                visibility = View.VISIBLE
                bringToFront()
            }
        }

        binding.editBtn.setOnClickListener {

        }

        binding.deleteBtn.setOnClickListener {
            val deleteDialog = DeleteDialogFragment()
            deleteDialog.setDialogClickListener(object : DeleteDialogFragment.DialogClickListener {
                override fun onClick() {

                }
            })
            deleteDialog.show(supportFragmentManager, deleteDialog.tag)
        }

        //hideMenu()

        /*binding.reportBtn.setOnClickListener {
            val reportDialog = ReportDialogFragment()
            reportDialog.setDialogClickListener(object : ReportDialogFragment.DialogClickListener{
                override fun onClick() {
                }
            })
            reportDialog.show(supportFragmentManager, reportDialog.tag)
        }*/
    }

    override fun initObserver() {
    }

    private fun setTitle(){
        Log.d("ReadActivity", intent.getStringExtra(BOARD)!!)
        binding.titleT.text = when(intent.getStringExtra(BOARD)){
            HONEY_TIP -> "꿀팁 공유하기"
            else -> "질문하기"
        }
    }

    /*private fun hideMenu(){
        binding.layout.setOnClickListener {
            binding.honeyTipMenu.visibility = View.GONE
        }

        binding.commentEt.setOnClickListener {
            binding.honeyTipMenu.visibility = View.GONE
        }

        binding.container.setOnClickListener {
            binding.honeyTipMenu.visibility = View.GONE
        }
    }*/



    fun showReportDialog(){
        val reportDialog = ReportDialogFragment()
        reportDialog.setDialogClickListener(object : ReportDialogFragment.DialogClickListener{
            override fun onClick() {
            }
        })
        reportDialog.show(supportFragmentManager, reportDialog.tag)
    }

    /*override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        binding.honeyTipMenu.visibility = View.GONE
        return super.dispatchTouchEvent(ev)
    }*/
}