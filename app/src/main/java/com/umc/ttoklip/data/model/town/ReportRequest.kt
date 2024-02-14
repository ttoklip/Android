package com.umc.ttoklip.data.model.town

import okhttp3.RequestBody

data class ReportRequest(
    val content: RequestBody,
    val reportType: RequestBody
)
