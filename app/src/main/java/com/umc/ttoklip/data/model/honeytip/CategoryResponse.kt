package com.umc.ttoklip.data.model.honeytip

data class CategoryResponse(
    val housework: List<HoneyTipResponse>,
    val cooking: List<HoneyTipResponse>,
    val safeLiving: List<HoneyTipResponse>,
    val welfarePolicy: List<HoneyTipResponse>
)
