package com.umc.ttoklip.presentation.signup

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.umc.ttoklip.R
import com.umc.ttoklip.data.model.KakaoResponse
import com.umc.ttoklip.databinding.ActivityDirectLocationBinding
import com.umc.ttoklip.di.appModul
import com.umc.ttoklip.presentation.base.BaseActivity
import retrofit2.Call
import retrofit2.Response

class DirectLocationActivity :
    BaseActivity<ActivityDirectLocationBinding>(R.layout.activity_direct_location) {
    private val kakaoInfo = appModul.kakaoAddress.Companion
    private val kakaoApi = appModul.kakaoAddress.kakaoApiRetrofitClient.apiService

    override fun initView() {
        callKakaoSearch("서울시 관악구 조원로12길 28")
    }

    fun callKakaoSearch(address: String) {
        val kakao = MutableLiveData<KakaoResponse.ResultSearchKeyword>()
        kakaoApi.getSearchKeyword(kakaoInfo.API_KEY, query = address)
            .enqueue(object : retrofit2.Callback<KakaoResponse.ResultSearchKeyword> {
                override fun onResponse(
                    call: Call<KakaoResponse.ResultSearchKeyword>,
                    response: Response<KakaoResponse.ResultSearchKeyword>
                ) {
                    kakao.value = response.body()
                    Log.i("kakao", "${kakao.value!!.documents[0].address_name}")
                }

                override fun onFailure(
                    call: Call<KakaoResponse.ResultSearchKeyword>,
                    t: Throwable
                ) {
                    t.printStackTrace()
                }
            })
    }

    override fun initObserver() {
    }
}