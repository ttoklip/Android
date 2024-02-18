package com.umc.ttoklip.data.model.signup

data class TermResponse (
    val terms:ArrayList<TermDetailResponse>,
    val totalPage:Int,
    val totalElements:Int,
    val isFirst:Boolean,
    val isLast:Boolean
)