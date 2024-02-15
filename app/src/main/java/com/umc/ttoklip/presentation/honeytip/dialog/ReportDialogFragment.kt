package com.umc.ttoklip.presentation.honeytip.dialog

import CustomSpinnerAdapter
import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.os.Build
import android.util.Log
import android.view.WindowManager
import android.widget.ArrayAdapter
import com.umc.ttoklip.R
import com.umc.ttoklip.data.model.honeytip.request.ReportRequest
import com.umc.ttoklip.databinding.DialogReportBinding
import com.umc.ttoklip.presentation.base.BaseDialogFragment
import com.umc.ttoklip.presentation.honeytip.read.ReadHoneyTipViewModel
import dagger.hilt.android.AndroidEntryPoint
class ReportDialogFragment: BaseDialogFragment<DialogReportBinding>(R.layout.dialog_report), CustomSpinnerAdapter.GetSpinnerText {
    private lateinit var dialogClickListener: DialogClickListener
    private var reportType = ""
    override fun initObserver() {

    }

    override fun initView() {
        //resize(dialog!!, 0.6f, )
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
        /*val strings = resources.getStringArray(R.array.report_list)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.spinner_inner_view, strings)
        binding.auto.setAdapter(arrayAdapter)*/
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
            "낚시 / 중복 / 도배성 게시물" -> ReadHoneyTipViewModel.ReportType.FISHING_DUPLICATE_SPAM.toString()
            "상업적 광고 / 홍보 글" -> ReadHoneyTipViewModel.ReportType.COMMERCIAL_ADVERTISING.toString()
            "선정적 / 불쾌함이 느껴지는 부적절한 글" -> ReadHoneyTipViewModel.ReportType.INAPPROPRIATE_CONTENT.toString()
            "비방 / 욕설 / 혐오 표현이 사용된 글" -> ReadHoneyTipViewModel.ReportType.ABUSE.toString()
            "종교 / 포교 관련 글" -> ReadHoneyTipViewModel.ReportType.RELIGIOUS_PROSELYTIZING.toString()
            "게시판 성격에 부적절" -> ReadHoneyTipViewModel.ReportType.INAPPROPRIATE_FOR_FORUM.toString()
            else -> ReadHoneyTipViewModel.ReportType.LEAK_IMPERSONATION_FRAUD.toString()
        }
    }

}