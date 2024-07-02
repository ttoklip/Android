package com.umc.ttoklip.util

import android.annotation.SuppressLint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StyleRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.umc.ttoklip.R


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

@BindingAdapter("bind:isAddressEmpty")
fun ConstraintLayout.setLayoutVisible(address: String){
    this.visibility = if (address.isEmpty()){
        View.GONE
    } else {
        View.VISIBLE
    }
}

@BindingAdapter("bind:totalMember")
fun TextView.setCompoundDrawableColorFilter(totalMember: Long){
    if(totalMember != 0L) {
        this.compoundDrawables.forEach { drawable ->
            if (drawable != null) {
                drawable.colorFilter =
                    PorterDuffColorFilter(
                        this.context.getColor(R.color.black),
                        PorterDuff.Mode.SRC_IN
                    )
            }
        }
    }
}