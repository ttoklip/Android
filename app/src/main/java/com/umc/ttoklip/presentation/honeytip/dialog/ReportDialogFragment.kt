package com.umc.ttoklip.presentation.honeytip.dialog

import CustomSpinnerAdapter
import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.os.Build
import android.util.Log
import android.view.WindowManager
import com.umc.ttoklip.R
import com.umc.ttoklip.data.model.honeytip.request.ReportRequest
import com.umc.ttoklip.databinding.DialogReportBinding
import com.umc.ttoklip.presentation.base.BaseDialogFragment

class ReportDialogFragment: BaseDialogFragment<DialogReportBinding>(R.layout.dialog_report), CustomSpinnerAdapter.GetSpinnerTextListener {
    private lateinit var dialogClickListener: DialogClickListener
    private var reportType = ""
    override fun initObserver() {

    }

    override fun initView() {
        val list = arrayListOf(
            "신고사유",
            "낚시 / 중복 / 도배성 게시물",
            "상업적 광고 / 홍보 글",
            "선정적 / 불쾌함이 느껴지는 부적절한 글",
            "비방 / 욕설 / 혐오 표현이 사용된 글",
            "종교 / 포교 관련 글",
            "게시판 성격에 부적절",
            "유출 / 사칭 / 사기",
        )
        val adapter = CustomSpinnerAdapter(requireContext(), list, this)
        binding.spinner.adapter = adapter
        binding.cancelBtn.setOnClickListener {
            dismiss()
        }
        binding.acceptBtn.setOnClickListener {
            dialogClickListener.onClick(ReportRequest(binding.reasonEt.text.toString(), stringToEnum(reportType)))
            dismiss()
        }
    }

    fun setDialogClickListener(dialogClickListener: DialogClickListener){
        this.dialogClickListener = dialogClickListener
    }

    interface DialogClickListener{
        fun onClick(request: ReportRequest)
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

    override fun getText(text: String) {
        reportType = text
        Log.d("reportType", reportType.toString())
    }

    private fun stringToEnum(reportType: String): String {
        return when (reportType) {
            "낚시 / 중복 / 도배성 게시물" -> ReportType.FISHING_DUPLICATE_SPAM.toString()
            "상업적 광고 / 홍보 글" -> ReportType.COMMERCIAL_ADVERTISING.toString()
            "선정적 / 불쾌함이 느껴지는 부적절한 글" -> ReportType.INAPPROPRIATE_CONTENT.toString()
            "비방 / 욕설 / 혐오 표현이 사용된 글" -> ReportType.ABUSE.toString()
            "종교 / 포교 관련 글" -> ReportType.RELIGIOUS_PROSELYTIZING.toString()
            "게시판 성격에 부적절" -> ReportType.INAPPROPRIATE_FOR_FORUM.toString()
            else -> ReportType.LEAK_IMPERSONATION_FRAUD.toString()
        }
    }
    enum class ReportType{
        FISHING_DUPLICATE_SPAM,
        COMMERCIAL_ADVERTISING,
        INAPPROPRIATE_CONTENT,
        ABUSE,
        RELIGIOUS_PROSELYTIZING,
        INAPPROPRIATE_FOR_FORUM,
        LEAK_IMPERSONATION_FRAUD
    }
}