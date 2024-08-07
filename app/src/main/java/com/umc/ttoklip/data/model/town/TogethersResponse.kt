package com.umc.ttoklip.data.model.town

data class TogethersResponse(
    val carts: List<Togethers>,
    val totalPage : Int,
    val totalElements : Int,
    val isFirst : Boolean,
    val isLast : Boolean,
){
    constructor() : this(listOf(),0,0,false,false)
}
