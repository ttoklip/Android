package com.umc.ttoklip.presentation.mypage

import android.net.http.SslError
import android.os.Message
import android.webkit.JavascriptInterface
import android.webkit.JsResult
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.FragmentSearchAddressBinding
import com.umc.ttoklip.presentation.base.BaseBottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class SearchAddressDialogFragment(private val btnClickListener: (String) -> Unit) :
    BaseBottomSheetDialogFragment<FragmentSearchAddressBinding>(R.layout.fragment_search_address) {
    override fun initObserver() = Unit

    override fun initView() {
        with(binding.searchAddressWebView) {
            settings.apply {
                javaScriptEnabled = true
                javaScriptCanOpenWindowsAutomatically = false
                setSupportMultipleWindows(false)
                cacheMode = WebSettings.LOAD_NO_CACHE
                builtInZoomControls = false // 화면 확대 축소 허용여부
                displayZoomControls = false // 줌 컨트롤 없애기.
                useWideViewPort = true
            }
            apply {
                addJavascriptInterface(WebViewData(), "")
                webViewClient = client
                webChromeClient = chromeClient
                loadUrl("https://github.com/posite")
            }
        }
    }

    private inner class WebViewData {
        @JavascriptInterface
        fun getAddress(zoneCode: String, roadAddress: String, buildingName: String) {
            CoroutineScope(Dispatchers.Default).launch {
                withContext(CoroutineScope(Dispatchers.Main).coroutineContext) {
                    btnClickListener(
                        getString(
                            R.string.road_address_fromat,
                            roadAddress,
                            buildingName
                        )
                    )
                    dismiss()
                }
            }
        }
    }

    private val client: WebViewClient = object : WebViewClient() {

        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            return false
        }

        override fun onReceivedSslError(
            view: WebView?,
            handler: SslErrorHandler?,
            error: SslError?
        ) {
            handler?.proceed()
        }
    }

    private val chromeClient = object : WebChromeClient() {

        override fun onCreateWindow(
            view: WebView?,
            isDialog: Boolean,
            isUserGesture: Boolean,
            resultMsg: Message?
        ): Boolean {


            val newWebView = WebView(requireContext())

            newWebView.webChromeClient = object : WebChromeClient() {
                override fun onJsAlert(
                    view: WebView,
                    url: String,
                    message: String,
                    result: JsResult
                ): Boolean {
                    super.onJsAlert(view, url, message, result)
                    return true
                }

                override fun onCloseWindow(window: WebView?) {

                }
            }
            val transport = resultMsg!!.obj as WebView.WebViewTransport
            transport.webView = newWebView
            resultMsg.sendToTarget()
            return true
        }
    }
}