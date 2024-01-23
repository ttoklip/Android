package com.umc.ttoklip.presentation.honeytip

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class PageDecoration(private val margin: Int): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.left = margin
        outRect.right = margin
    }
}