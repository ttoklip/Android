package com.umc.ttoklip.presentation.signup.location

import com.umc.ttoklip.data.repository.location.DirectLocationRepositoryImpl

class DirectLocationModelView(
    private val kakaoApi:DirectLocationRepositoryImpl
) {

    fun getDirectaddress(){

//        val call=kakaoApi.getDirectAddress("서울시 관악구 조원로")
//        call.enqueue(object: Callback<KakaoResponse.ResultSearchKeyword> {
//            override fun onResponse(
//                call: Call<KakaoResponse.ResultSearchKeyword>,
//                response: Response<KakaoResponse.ResultSearchKeyword>
//            ) {
//                Log.i("LocaSearch","통신 성공:${response.body()}")
//            }
//            override fun onFailure(call: Call<KakaoResponse.ResultSearchKeyword>, t: Throwable) {
//                Log.w("LocaSearch","통신 실패: ${t.message}")
//            }
//        })
    }

}