package com.umc.ttoklip.data.model.town

data class TownMainResponse(
    val cartRecent3: List<CartRecent3>,
    val communityRecent3: List<CommunityRecent3>,
    val street: String
){
    constructor() : this(listOf(), listOf(),"")
}