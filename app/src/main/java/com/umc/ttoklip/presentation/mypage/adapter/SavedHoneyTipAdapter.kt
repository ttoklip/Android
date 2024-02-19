package com.umc.ttoklip.presentation.mypage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.umc.ttoklip.data.model.mypage.ScrapResponse
import com.umc.ttoklip.databinding.ItemSavedHoneyTipBinding

class SavedHoneyTipAdapter(private var listener: OnSpinnerItemClickListener) :
    ListAdapter<ScrapResponse, SavedHoneyTipAdapter.HoneyTipListViewHolder>(object :
        DiffUtil.ItemCallback<ScrapResponse>() {
        override fun areItemsTheSame(oldItem: ScrapResponse, newItem: ScrapResponse): Boolean {
            return oldItem.writer == newItem.writer && oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: ScrapResponse, newItem: ScrapResponse): Boolean {
            return oldItem == newItem
        }

    }) {
    inner class HoneyTipListViewHolder(private val binding: ItemSavedHoneyTipBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(honeyTips: ScrapResponse, pos: Int) {
            binding.item = honeyTips
            binding.commentCountTv.text = honeyTips.commentCount.toString()
            binding.likeCountTv.text = honeyTips.likeCount.toString()
            binding.starCountTv.text = honeyTips.scrapCount.toString()

            if (pos == itemCount - 1) {
                binding.itemSeparator.isGone = true
            }

            binding.root.setOnClickListener {
                listener.onClick(honeyTips)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoneyTipListViewHolder {
        return HoneyTipListViewHolder(
            ItemSavedHoneyTipBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: HoneyTipListViewHolder, position: Int) {
        holder.bind(currentList[position], position)
    }
}

interface OnSpinnerItemClickListener {
    fun onClick(honeyTip: ScrapResponse)
}