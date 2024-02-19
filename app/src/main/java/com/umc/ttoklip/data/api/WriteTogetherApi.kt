package com.umc.ttoklip.data.api

import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.town.CreateTogethersResponse
import com.umc.ttoklip.data.model.town.PatchTogetherResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface WriteTogetherApi {
    //함꼐해요 게시글 생성
    @Multipart
    @POST("/api/v1/town/carts")
    suspend fun createTogethers(
        @Part("title") title: String,
        @Part("content") content: String,
        @Part("totalPrice") totalPrice: Long,
        @Part("location") location: String,
        @Part("chatUrl") chatUrl: String,
        @Part("partyMax") party: Long,
        @Part images: List<MultipartBody.Part>,
        @Part("itemUrls") itemUrls: List<String>
    ): Response<ResponseBody<CreateTogethersResponse>>

    //함께해요 게시글 수정
    @Multipart
    @PATCH("/api/v1/town/carts/{postId}")
    suspend fun patchTogethers(
        @Part("title") title: String,
        @Part("content") content: String,
        @Part("totalPrice") totalPrice: Long,
        @Part("location") location: String,
        @Part("chatUrl") chatUrl: String,
        @Part("party") party: Long,
        @Part images: List<MultipartBody.Part>,
        @Part("itemUrls") itemUrls: List<String>,
        @Path("postId") postId: Long
    ): Response<ResponseBody<PatchTogetherResponse>>
}