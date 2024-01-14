package com.umc.ttoklip.data.repository

import com.umc.ttoklip.data.model.TestResponse
import retrofit2.Response

//테스트용 repository
interface TestRepository {

    suspend fun test(): Response<TestResponse>
}