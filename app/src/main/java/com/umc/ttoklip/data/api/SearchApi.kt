package com.umc.ttoklip.data.api

import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.news.detail.NewsDetailResponse
import com.umc.ttoklip.data.model.search.NewsSearchResponse
import com.umc.ttoklip.data.model.search.TipSearchResponse
import com.umc.ttoklip.data.model.search.TownSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {

    @GET("/api/v1/search/newsletter")
    suspend fun getSearchNewsApi(
        @Query("title") title : String,
    ): Response<ResponseBody<NewsSearchResponse>>

    @GET("/api/v1/search/honeytip")
    suspend fun getSearchTipApi(
        @Query("title") title : String,
    ): Response<ResponseBody<TipSearchResponse>>


    @GET("/api/v1/search/our-town")
    suspend fun getSearchTownApi(
        @Query("title") title : String,
    ): Response<ResponseBody<TownSearchResponse>>

}