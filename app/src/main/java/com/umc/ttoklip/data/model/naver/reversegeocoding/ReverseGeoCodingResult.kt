package com.umc.ttoklip.data.model.naver.reversegeocoding

import kr.ac.tukorea.whereareu.data.model.naver.Land

data class ReverseGeoCodingResult(
    val region: RegionInfo,
    val land: Land
)

data class RegionInfo(
    val area1: AreaInfo,
    val area2: AreaInfo,
    val area3: AreaInfo,
    val area4: AreaInfo,
)
