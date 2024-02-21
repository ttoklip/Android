package com.umc.ttoklip

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.annotation.StringRes
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.kakao.sdk.common.KakaoSdk
import com.navercorp.nid.NaverIdLoginSDK
import com.umc.ttoklip.module.NetworkConnectionChecker
import com.umc.ttoklip.util.PreferenceUtil
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class TtoklipApplication : Application(), DefaultLifecycleObserver {
    override fun onCreate() {
        super<Application>.onCreate()
        context = applicationContext
        networkConnectionChecker = NetworkConnectionChecker(context)
        prefs=PreferenceUtil(applicationContext)

        //login sdk 객체 초기화
        KakaoSdk.init(this, BuildConfig.kakaoNativeKey)
        NaverIdLoginSDK.initialize(this,
            BuildConfig.naverClientId,
            BuildConfig.naverClientSecret,
            "똑립")

    }


    override fun onStop(owner: LifecycleOwner) {
        networkConnectionChecker.unregister()
        super.onStop(owner)
    }

    override fun onStart(owner: LifecycleOwner) {
        networkConnectionChecker.register()
        super.onStart(owner)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var context: Context

        fun getString(@StringRes stringResId: Int): String {
            return context.getString(stringResId)
        }

        private lateinit var networkConnectionChecker: NetworkConnectionChecker
        fun isOnline() = networkConnectionChecker.isOnline()

        lateinit var prefs:PreferenceUtil
    }
}