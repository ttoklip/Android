package com.umc.ttoklip.data.model.home

import androidx.annotation.DrawableRes
import com.google.gson.annotations.SerializedName
import com.umc.ttoklip.R

enum class Weather(val label :String, @DrawableRes val resId : Int) {

    //@SerializedName("1")
    SUN("지금은 맑아요", R.drawable.sun_img),
    
    //@SerializedName("1")
    SNOW("지금은 눈이 와요", R.drawable.snow_img),

    RAIN("지금은 비가 와요", R.drawable.rain_img),

    CLOUD("지금은 흐려요",R.drawable.cloud_img)
}
