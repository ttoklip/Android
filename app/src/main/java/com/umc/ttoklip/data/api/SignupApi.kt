package com.umc.ttoklip.data.api

import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.StandardResponse
import com.umc.ttoklip.data.model.signup.SignupResponse
import com.umc.ttoklip.data.model.signup.SignupRequest
import com.umc.ttoklip.data.model.signup.TermResponse
import com.umc.ttoklip.data.model.signup.VerifyRequest
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

    @POST("/api/v1/email/send")
    suspend fun verifySend(
        @Body request: VerifyRequest
    ):Response<ResponseBody<StandardResponse>>
    @POST("/api/v1/email/verify")
    suspend fun verifyCheck(
        @Body request: VerifyRequest
    ):Response<ResponseBody<StandardResponse>>

    @POST("/api/v1/auth/duplicate")
    suspend fun idCheck(@Query("newId") id:String)
    :Response<ResponseBody<StandardResponse>>

    @GET("/api/v1/privacy/oauth/check-nickname")
    suspend fun nickCheck(@Query("nickname") nickname:String)
            : Response<ResponseBody<SignupResponse>>
    @GET("/api/v1/privacy/local/check-nickname")
    suspend fun nickCheckLocal(@Query("nickname") nickname: String)
        : Response<ResponseBody<SignupResponse>>

    //소셜 회원가입 정보 저장
    @Multipart
    @POST("/api/v1/privacy/insert")
    suspend fun savePrivacy(
        @Part profileImage: MultipartBody.Part?,
        @Part categories:List<MultipartBody.Part>,
        @PartMap params:MutableMap<String,RequestBody>
    ):Response<ResponseBody<SignupResponse>>
    //로컬 로그인 정보 저장
    @Multipart
    @POST("/api/v1/auth/signup")
    suspend fun savePrivacyLocal(
        @Part profileImage: MultipartBody.Part?,
        @Part categories:List<MultipartBody.Part>,
        @PartMap params:MutableMap<String,RequestBody>
    ):Response<ResponseBody<SignupResponse>>

    @GET("/api/v1/term")
    suspend fun getTerm(
        @Query("page")page:Int
    ):Response<ResponseBody<TermResponse>>
}