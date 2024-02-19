package com.umc.ttoklip.data.api

import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.mypage.ScrapListNewsResponse
import com.umc.ttoklip.data.model.mypage.ScrapListTipResponse
import com.umc.ttoklip.data.model.mypage.ScrapListTownResponse
import com.umc.ttoklip.data.model.signup.SignupResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MyPageApi {

    @GET("/api/v1/my-page/scrap-post/newsletter")
    suspend fun getScrapNews(
        @Query("page") page: Int
    ) : Response<ResponseBody<ScrapListNewsResponse>>

    @GET("/api/v1/my-page/scrap-post/honeytip")
    suspend fun getScrapTip(
        @Query("page") page: Int
    ) : Response<ResponseBody<ScrapListTipResponse>>

    @GET("/api/v1/my-page/scrap-post/community")
    suspend fun getScrapTown(
        @Query("page") page: Int
    ) : Response<ResponseBody<ScrapListTownResponse>>

}