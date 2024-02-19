package com.umc.ttoklip.data.model.honeytip

data class HoneyTipPagingResponse(
    val data: List<HoneyTipMain>,
    val category: String,
    val totalPage: Int,
    val totalElements: Int,
    val isFirst: Boolean,
    val isLast: Boolean,
) {
    constructor(): this(listOf(), "", 0, 0, true, false)
}
