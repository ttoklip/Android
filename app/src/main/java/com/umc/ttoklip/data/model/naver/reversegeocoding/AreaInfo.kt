package com.umc.ttoklip.data.model.naver.reversegeocoding

data class AreaInfo(
    val name: String,
    val coords: Coords
)

data class Coords(
    val center: Center
)

data class Center(
    val crs: String,
    val x: Float,
    val y: Float
)
