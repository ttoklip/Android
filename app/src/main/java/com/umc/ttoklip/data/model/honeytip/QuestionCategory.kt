package com.umc.ttoklip.data.model.honeytip

data class QuestionCategory(
    val housework: List<HoneyTipMain>,
    val cooking: List<HoneyTipMain>,
    val safeLiving: List<HoneyTipMain>,
    val welfarePolicy: List<HoneyTipMain>
)
