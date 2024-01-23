package com.umc.ttoklip.data.repository

import com.umc.ttoklip.data.api.TestApi
import com.umc.ttoklip.data.model.TestResponse
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton


//테스트용 repositoryimpl
@Singleton
class TestRepositoryImpl @Inject constructor(
    private val api: TestApi
) : TestRepository {
    override suspend fun test(): Response<TestResponse> {
        return api.testGet()
    }
}