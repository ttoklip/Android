package com.umc.ttoklip.data.repository.mypage

import com.umc.ttoklip.data.api.MyPage2Api
import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.honeytip.CreateHoneyTipResponse
import com.umc.ttoklip.data.model.mypage.MyPageInfoResponse
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
        photo: MultipartBody.Part?,
        info: MutableMap<String, RequestBody>, cate: List<MultipartBody.Part>?
    ): NetworkResult<CreateHoneyTipResponse> {
        return handleApi({
            api.editMyPageInfo(
                photo,
                cate,
                info
            )
        }) { response: ResponseBody<CreateHoneyTipResponse> -> response.result }
    }
}