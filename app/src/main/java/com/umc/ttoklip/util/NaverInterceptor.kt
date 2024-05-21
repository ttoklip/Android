package com.umc.ttoklip.util

import android.util.Log
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Response

class NaverInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = with(chain) {
        val newRequest = request().newBuilder()
            .addHeader("X-NCP-APIGW-API-KEY-ID", "")
            .addHeader("X-NCP-APIGW-API-KEY", "")
            .build()

        proceed(newRequest)
    }
}
