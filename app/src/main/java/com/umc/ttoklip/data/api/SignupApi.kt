package com.umc.ttoklip.data.api

import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.signup.SignupResponse
import com.umc.ttoklip.data.model.signup.SignupRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Query

interface SignupApi {
    @GET("/api/v1/privacy/check-nickname")
    suspend fun nickCheck(@Query("nickname") nickname:String)
            : Response<ResponseBody<SignupResponse>>

    @Multipart
    @POST("/api/v1/privacy/insert")
    suspend fun savePrivacy(
        @Part profileImage: MultipartBody.Part,
        @Part categories:List<MultipartBody.Part>,
        @PartMap params:MutableMap<String,RequestBody>
    )
            :Response<ResponseBody<SignupResponse>>
}