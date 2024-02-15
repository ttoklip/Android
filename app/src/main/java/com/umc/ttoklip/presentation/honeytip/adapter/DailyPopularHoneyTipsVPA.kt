package com.umc.ttoklip.presentation.honeytip.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.umc.ttoklip.data.model.honeytip.HoneyTipMain
import com.umc.ttoklip.databinding.ItemDailyPopularHoneyTipBinding

class DailyPopularHoneyTipsVPA(val onClick: (HoneyTipMain) -> Unit) :
    ListAdapter<HoneyTipMain, DailyPopularHoneyTipsVPA.DailyPopularHoneyTipsViewHolder>(object :
        DiffUtil.ItemCallback<HoneyTipMain>() {
        override fun areItemsTheSame(
            oldItem: HoneyTipMain,
            newItem: HoneyTipMain
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: HoneyTipMain,
            newItem: HoneyTipMain
        ): Boolean {
           return oldItem == newItem
        }

    }) {
    inner class DailyPopularHoneyTipsViewHolder(private val binding: ItemDailyPopularHoneyTipBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(honeyTip: HoneyTipMain) {
            binding.writerTv.text = honeyTip.writer
            binding.honeyTipTitleTv.text = honeyTip.title
            binding.honeyTipBodyContentTv.text = honeyTip.content
            binding.starCountTv.text = "0"
            binding.likeCountTv.text = honeyTip.likeCount.toString()
            binding.commentCountTv.text = honeyTip.commentCount.toString()
            binding.root.setOnClickListener {
                onClick(honeyTip)
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