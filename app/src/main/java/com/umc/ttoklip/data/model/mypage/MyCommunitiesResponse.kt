package com.umc.ttoklip.data.model.mypage

data class MyCommunitiesResponse(
    val communities: List<CommunityX>,
    val isFirst: Boolean,
    val isLast: Boolean,
    val totalElements: Int,
    val totalPage: Int
)