package com.umc.ttoklip.data.model.honeytip

data class HoneyTipCategory(
    val housework: List<HoneyTipMain>,
    val cooking: List<HoneyTipMain>,
    val safeLiving: List<HoneyTipMain>,
    val welfarePolicy: List<HoneyTipMain>
)
