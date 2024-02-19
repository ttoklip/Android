package com.umc.ttoklip.data.model.stranger

data class StrangerResponse (
    val nickname:String,
    val residence:String,
    val level:Int,
    val experience:ExperienceResponse
)
data class ExperienceResponse(
    val current:Int,
    val required:Int,
    val levelimageurl:String
)