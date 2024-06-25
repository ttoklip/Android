package com.umc.ttoklip.util

import android.util.Log
import com.umc.ttoklip.R
import com.umc.ttoklip.TtoklipApplication
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Response

class NaverInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = with(chain) {
        val newRequest = request().newBuilder()
            .addHeader("X-NCP-APIGW-API-KEY-ID", TtoklipApplication.getString(R.string.naver_client_key))
            .addHeader("X-NCP-APIGW-API-KEY", TtoklipApplication.getString(R.string.naver_client_secret_key))
            .build()

        proceed(newRequest)
    }
}
