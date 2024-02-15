package com.umc.ttoklip.data.model.honeytip

data class HoneyTipMainResponse(
    val questionCategory: QuestionCategory,
    val honeyTipCategory: HoneyTipCategory,
    val topFiveQuestions: List<HoneyTipMain>
)
