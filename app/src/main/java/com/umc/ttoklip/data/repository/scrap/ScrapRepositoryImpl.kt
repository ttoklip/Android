package com.umc.ttoklip.data.repository.scrap

import com.umc.ttoklip.data.api.MyPageApi
import com.umc.ttoklip.data.api.NewsApi
import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.mypage.ScrapListNewsResponse
import com.umc.ttoklip.data.model.mypage.ScrapListTipResponse
import com.umc.ttoklip.data.model.mypage.ScrapListTownResponse
import com.umc.ttoklip.module.NetworkResult
import com.umc.ttoklip.module.handleApi
import javax.inject.Inject

class ScrapRepositoryImpl @Inject constructor(
    private val api: MyPageApi
) : ScrapRepository {
    override suspend fun getNewsScrap(page: Int): NetworkResult<ScrapListNewsResponse> {
        return handleApi({ api.getScrapNews(page) }) { response: ResponseBody<ScrapListNewsResponse> -> response.result }
    }

    override suspend fun getTipScrap(page: Int): NetworkResult<ScrapListTipResponse> {
        return handleApi({ api.getScrapTip(page) }) { response: ResponseBody<ScrapListTipResponse> -> response.result }
    }

    override suspend fun getTownScrap(page: Int): NetworkResult<ScrapListTownResponse> {
        return handleApi({ api.getScrapTown(page) }) { response: ResponseBody<ScrapListTownResponse> -> response.result }
    }
}