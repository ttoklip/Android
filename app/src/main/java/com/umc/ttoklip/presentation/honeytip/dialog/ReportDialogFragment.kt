package com.umc.ttoklip.presentation.honeytip.dialog

import CustomSpinnerAdapter
import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.os.Build
import android.view.WindowManager
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.DialogReportBinding
import com.umc.ttoklip.presentation.base.BaseDialogFragment
import dagger.hilt.android.AndroidEntryPoint
class ReportDialogFragment: BaseDialogFragment<DialogReportBinding>(R.layout.dialog_report) {
    private lateinit var dialogClickListener: DialogClickListener
    override fun initObserver() {

    }

    override fun initView() {
        //resize(dialog!!, 0.6f, )
        val list = arrayListOf(
            "낚시 / 중복 / 도배성 게시물",
            "상업적 광고 / 홍보 글",
            "선정적 / 불쾌함이 느껴지는 부적절한 글",
            "비방 / 욕설 / 혐오 표현이 사용된 글",
            "종교 / 포교 관련 글",
            "게시판 성격에 부적절",
            "유출 / 사칭 / 사기",
        )
        val adapter = CustomSpinnerAdapter(requireContext(), list)
        binding.spinner.adapter = adapter
        binding.cancelBtn.setOnClickListener {
            dismiss()
        }
        binding.acceptBtn.setOnClickListener {
            dialogClickListener.onClick()
            dismiss()
        }
    }

    fun setDialogClickListener(dialogClickListener: DialogClickListener){
        this.dialogClickListener = dialogClickListener
    }

    interface DialogClickListener{
        fun onClick()
    }

    private fun resize(dialog: Dialog, width: Float, height: Float){
        val windowManager = context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager

        if (Build.VERSION.SDK_INT < 30) {
            val size = Point()
            windowManager.defaultDisplay.getSize(size)

            val x = (size.x * width).toInt()
            val y = (size.y * height).toInt()
            dialog.window?.setLayout(x, y)
        } else {
            val rect = windowManager.currentWindowMetrics.bounds

            val x = (rect.width() * width).toInt()
            val y = (rect.height() * height).toInt()
            dialog.window?.setLayout(x, y)
        }
    }

}