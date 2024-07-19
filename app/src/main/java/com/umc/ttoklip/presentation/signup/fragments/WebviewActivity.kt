package com.umc.ttoklip.presentation.signup.fragments

import android.annotation.SuppressLint
import android.net.http.SslError
import android.util.Log
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivityWebviewBinding
import com.umc.ttoklip.presentation.base.BaseActivity

class WebviewActivity :BaseActivity<ActivityWebviewBinding>(R.layout.activity_webview) {

    override fun initView() {
        //JS 허용
        binding.webView.settings.javaScriptEnabled=true
        binding.webView.settings.domStorageEnabled=true

        //속도 개선

        //웹뷰에서 새 창이 뜨지 않도록 방치
        binding.webView.webViewClient= object: WebViewClient(){
            override fun onReceivedSslError(
                view: WebView?,
                handler: SslErrorHandler?,
                error: SslError?
            ) {
                super.onReceivedSslError(view, handler, error)
                handler?.proceed()
                val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(applicationContext)
                var message = "SSL Certificate error"
                when (error?.primaryError) {
                    SslError.SSL_UNTRUSTED -> message = "신뢰할 수 없는 사이트입니다."
                    SslError.SSL_EXPIRED -> message = "만료된 사이트입니다."
                    SslError.SSL_IDMISMATCH -> message = "도메인이 없습니다."
                    SslError.SSL_NOTYETVALID -> message = "검증되지 않은 사이트입니다."
                }
                message += "페이지로 이동 하시겠습니까?"
                builder.setTitle("SSL Certificate Error")
                builder.setMessage(message)
                builder.setPositiveButton("확인") { _, _ -> handler?.proceed() }
                builder.setNegativeButton("취소") { _, _ -> handler?.cancel() }
                val dialog: android.app.AlertDialog = builder.create()
                dialog.show()
            }
        }
        binding.webView.webChromeClient= WebChromeClient()

        //링크 주소 로드
        val url=intent.getStringExtra("url")?:""
        Log.i("url",url)
        binding.webView.loadUrl(url)
    }

    override fun onBackPressed() {
        if(binding.webView.canGoBack()){
            // 뒤로 갈 페이지가 존재 할 경우
            binding.webView.goBack()
        }
        else {
            super.onBackPressed()
        }
    }

    override fun initObserver() {
    }

}