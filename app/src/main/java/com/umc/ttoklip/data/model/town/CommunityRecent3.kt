package com.umc.ttoklip.data.model.town

import com.umc.ttoklip.presentation.hometown.adapter.Together

data class CommunityRecent3(
    val communityId: Int,
    val street: String,
    val title: String
){
    fun toModel()= Together(title,street, communityId,"소통해요")
}