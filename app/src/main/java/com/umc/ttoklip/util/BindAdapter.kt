package com.umc.ttoklip.util

import android.graphics.Typeface
import android.widget.TextView
import androidx.annotation.StyleRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter


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