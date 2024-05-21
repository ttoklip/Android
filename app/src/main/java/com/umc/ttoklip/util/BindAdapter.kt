package com.umc.ttoklip.util

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StyleRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


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

@BindingAdapter("bind:isWriter", "bind:isDeadLine")
fun TextView.setJoinFrameTextViewVisible(isWriter: Boolean, isDeadLine: Boolean) {
    this.isVisible = if (isWriter) {
        !isDeadLine
    } else {
        false
    }
}

@BindingAdapter("bind:joinVisible", "bind:isDeadLine")
fun TextView.setJoinBtnVisible(joinState: Boolean, isDeadLine: Boolean) {
    this.isVisible = if (isDeadLine) {
        false
    } else {
        joinState
    }
}

@BindingAdapter("bind:cancelJoinVisible", "bind:isDeadLine")
fun TextView.setCancelJoinBtnVisible(joinState: Boolean, isDeadLine: Boolean) {
    this.isVisible = if (isDeadLine) {
        false
    } else {
        !joinState
    }
}