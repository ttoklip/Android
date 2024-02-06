package com.umc.ttoklip.data.model

class KakaoResponse{
    data class ResultSearchKeyword(
        var documents: List<Place>          // 검색 결과
    )
    data class Place(
        var id: String,                     // 장소 ID
        var place_name: String,             // 장소명, 업체명
        var address_name: String,           // 전체 지번 주소
        var road_address_name: String,      // 전체 도로명 주소
        var x: String,                      // X 좌표값 혹은 longitude
        var y: String                      // Y 좌표값 혹은 latitude
    )
}
