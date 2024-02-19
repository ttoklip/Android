package com.umc.ttoklip.data.repository.mypage

import com.umc.ttoklip.data.api.MyAccountRestrictApi
import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.mypage.RestrictedResponse
import com.umc.ttoklip.module.NetworkResult
import com.umc.ttoklip.module.handleApi
import javax.inject.Inject

class MyAccountRestrictRepositoryImpl @Inject constructor(private val api: MyAccountRestrictApi) :
    MyAccountRestrictRepository {
    override suspend fun getRestrictedReason(): NetworkResult<RestrictedResponse> {
        return handleApi({ api.getRestrictedReason() }) { response: ResponseBody<RestrictedResponse> -> response.result }
    }

}