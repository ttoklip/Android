package com.umc.ttoklip.data.repository.mypage

import com.umc.ttoklip.data.api.MyPage2Api
import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.honeytip.CreateHoneyTipResponse
import com.umc.ttoklip.data.model.mypage.MyPageInfoResponse
import com.umc.ttoklip.data.model.signup.SignupResponse
import com.umc.ttoklip.module.NetworkResult
import com.umc.ttoklip.module.handleApi
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class MyPageRepository2Impl @Inject constructor(
    private val api: MyPage2Api
) : MyPageRepository2 {
    override suspend fun getMyPageInfo(): NetworkResult<MyPageInfoResponse> {
        return handleApi({ api.getMyPageInfo() }) { response: ResponseBody<MyPageInfoResponse> -> response.result }
    }

    override suspend fun editMyPageInfo(
        street: RequestBody,
        nickname: RequestBody,
        categories: RequestBody,
        profileImage: MultipartBody.Part?,
        independentYear: RequestBody,
        independentMonth: RequestBody
    ): NetworkResult<CreateHoneyTipResponse> {
        return handleApi({
            api.editMyPageInfo(
                street, nickname, categories, profileImage, independentYear, independentMonth
            )
        }) { response: ResponseBody<CreateHoneyTipResponse> -> response.result }
    }

    override suspend fun checkNickname(nick: String): NetworkResult<SignupResponse> {
        return handleApi({api.nickCheck(nick)}) {response: ResponseBody<SignupResponse> ->response.result}
    }
}