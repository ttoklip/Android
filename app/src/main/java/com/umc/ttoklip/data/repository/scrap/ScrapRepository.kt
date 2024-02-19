package com.umc.ttoklip.data.repository.scrap

import com.umc.ttoklip.data.model.CommonResponse
import com.umc.ttoklip.data.model.mypage.ScrapListNewsResponse
import com.umc.ttoklip.data.model.mypage.ScrapListTipResponse
import com.umc.ttoklip.data.model.mypage.ScrapListTownResponse
import com.umc.ttoklip.data.model.news.MainNewsResponse
import com.umc.ttoklip.data.model.news.NewsPageResponse
import com.umc.ttoklip.data.model.news.ReportRequest
import com.umc.ttoklip.data.model.news.comment.NewsCommentRequest
import com.umc.ttoklip.data.model.news.detail.NewsDetailResponse
import com.umc.ttoklip.data.model.search.NewsSearchResponse
import com.umc.ttoklip.data.model.search.TipSearchResponse
import com.umc.ttoklip.data.model.search.TownSearchResponse
import com.umc.ttoklip.module.NetworkResult

interface ScrapRepository {

    suspend fun getNewsScrap(page: Int): NetworkResult<ScrapListNewsResponse>

    suspend fun getTipScrap( page: Int): NetworkResult<ScrapListTipResponse>

    suspend fun getTownScrap(page: Int): NetworkResult<ScrapListTownResponse>
}