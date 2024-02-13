package com.umc.ttoklip.presentation.honeytip.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.umc.ttoklip.data.model.honeytip.HoneyTipResponse
import com.umc.ttoklip.databinding.ItemDailyPopularHoneyTipBinding
import kotlin.String

class DailyPopularHoneyTipsVPA(val onClick: (HoneyTipResponse) -> Unit) :
    ListAdapter<HoneyTipResponse, DailyPopularHoneyTipsVPA.DailyPopularHoneyTipsViewHolder>(object :
        DiffUtil.ItemCallback<HoneyTipResponse>() {
        override fun areItemsTheSame(
            oldItem: HoneyTipResponse,
            newItem: HoneyTipResponse
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: HoneyTipResponse,
            newItem: HoneyTipResponse
        ): Boolean {
           return oldItem == newItem
        }

    }) {
    inner class DailyPopularHoneyTipsViewHolder(private val binding: ItemDailyPopularHoneyTipBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(honeyTipResponse: HoneyTipResponse) {
            binding.writerTv.text = honeyTipResponse.writer
            binding.honeyTipTitleTv.text = honeyTipResponse.title
            binding.honeyTipBodyContentTv.text = honeyTipResponse.content
            binding.starCountTv.text = "0"
            binding.likeCountTv.text = honeyTipResponse.likeCount.toString()
            binding.commentCountTv.text = honeyTipResponse.commentCount.toString()
            binding.root.setOnClickListener {
                onClick(honeyTipResponse)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DailyPopularHoneyTipsViewHolder {
        return DailyPopularHoneyTipsViewHolder(
            ItemDailyPopularHoneyTipBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun onBindViewHolder(holder: DailyPopularHoneyTipsViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}