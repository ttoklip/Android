package com.umc.ttoklip.util

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StyleRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayout
import com.umc.ttoklip.presentation.news.NewsViewModel


@BindingAdapter("bindTextViewStyle")
fun AppCompatTextView.bindTextViewStyle(@StyleRes styleResourceId: Int) {
    this.setTextAppearance(styleResourceId)
}

@BindingAdapter("setBold")
fun AppCompatTextView.setBold(isBold: Boolean) {
    if (isBold) {
        this.setTypeface(null, Typeface.BOLD)
    } else {
        this.setTypeface(null, Typeface.NORMAL)
    }
}

@SuppressLint("CheckResult")
@BindingAdapter(value = ["bind:url", "bind:baseImg"], requireAll = false)
fun ImageView.setUrlImg(imageUrl: String?, placeholder: Drawable?) {
    Glide.with(this.context)
        .load(imageUrl)
        .placeholder(placeholder)
        .error(placeholder)
        .into(this)
}

@BindingAdapter("textInt")
fun AppCompatTextView.textInt(int: Int) {
    this.text = int.toString()
}