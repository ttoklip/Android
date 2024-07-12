package com.umc.ttoklip.data.model.naver.reversegeocoding

import kr.ac.tukorea.whereareu.data.model.naver.ReverseGeoCodingResult
import kr.ac.tukorea.whereareu.data.model.naver.StatusResult

data class ReverseGeocodingResponse(
    val status: StatusResult,
    val results: List<ReverseGeoCodingResult>
)
