package com.umc.ttoklip.data.model.news

data class CategoryResponse(
    val cookingQuestions: List<CookingQuestion>,
    val houseworkQuestions: List<HouseworkQuestion>,
    val safeLivingQuestions: List<SafeLivingQuestion>,
    val welfarePolicyQuestions: List<WelfarePolicyQuestion>
)