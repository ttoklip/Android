package com.umc.ttoklip.data.model.town

import okhttp3.RequestBody

data class CreateCommentRequest(
    val content: RequestBody,
    val parentCommentId: RequestBody
)
