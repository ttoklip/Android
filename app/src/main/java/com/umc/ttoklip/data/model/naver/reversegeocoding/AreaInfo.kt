package kr.ac.tukorea.whereareu.data.model.naver

data class AreaInfo(
    val name: String,
    val coords:Coords
)

data class Coords(
    val center: Center
)

data class Center(
    val crs: String,
    val x: Float,
    val y: Float
)
