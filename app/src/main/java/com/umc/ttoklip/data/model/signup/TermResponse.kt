package com.umc.ttoklip.data.model.signup

data class TermResponse (
    val agreeTermsOfService:TermDetailResponse,
    val agreeLocationService:TermDetailResponse,
    val agreePrivacyPolicy:TermDetailResponse
)