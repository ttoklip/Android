package com.umc.ttoklip.data.model.town

data class CommsResponse(
    val communities: List<Communities>,
    val totalPage : Int,
    val totalElements : Int,
    val isFirst : Boolean,
    val isLast : Boolean,
)
