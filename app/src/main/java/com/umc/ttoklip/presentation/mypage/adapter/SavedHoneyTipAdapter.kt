package com.umc.ttoklip.presentation.mypage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.umc.ttoklip.databinding.ItemSavedHoneyTipBinding

class SavedHoneyTipAdapter(private var listener: OnSpinnerItemClickListener) :
    ListAdapter<HoneyTip, SavedHoneyTipAdapter.HoneyTipListViewHolder>(object :
        DiffUtil.ItemCallback<HoneyTip>() {
        override fun areItemsTheSame(oldItem: HoneyTip, newItem: HoneyTip): Boolean {
            return oldItem.writer == newItem.writer && oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: HoneyTip, newItem: HoneyTip): Boolean {
            return oldItem == newItem
        }

    }) {
    inner class HoneyTipListViewHolder(private val binding: ItemSavedHoneyTipBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(honeyTips: HoneyTip) {
            binding.titleTv.text = honeyTips.title
            binding.writerTv.text = honeyTips.writer
            binding.dateTv.text = honeyTips.date
            binding.bodyTv.text = honeyTips.body
            binding.commentCountTv.text = honeyTips.chatCnt.toString()

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
        holder.bind(currentList[position])
    }
}

interface OnSpinnerItemClickListener {
    fun onClick(honeyTip: HoneyTip)
}