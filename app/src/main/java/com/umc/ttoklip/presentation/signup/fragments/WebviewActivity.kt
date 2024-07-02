package com.umc.ttoklip.presentation.signup.fragments

import android.annotation.SuppressLint
import android.util.Log
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
        binding.webView.webViewClient= WebViewClient()
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