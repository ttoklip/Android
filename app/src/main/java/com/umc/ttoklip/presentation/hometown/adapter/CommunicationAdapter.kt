package com.umc.ttoklip.presentation.hometown.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.umc.ttoklip.databinding.ItemCommunicationBinding

class CommunicationAdapter(private var listener: OnItemClickListener) :
    ListAdapter<Communication, CommunicationAdapter.CommunicationViewHolder>(object :
        DiffUtil.ItemCallback<Communication>() {
        override fun areItemsTheSame(oldItem: Communication, newItem: Communication): Boolean {
            return oldItem.writer == newItem.writer && oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Communication, newItem: Communication): Boolean {
            return oldItem == newItem
        }

    }) {
    inner class CommunicationViewHolder(private val binding: ItemCommunicationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(honeyTips: Communication, pos: Int) {
            if (pos == itemCount - 1) {
                binding.itemSeparator.isGone = true
            }
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunicationViewHolder {
        return CommunicationViewHolder(
            ItemCommunicationBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CommunicationViewHolder, position: Int) {
        holder.bind(currentList[position], position)
    }
}

interface OnItemClickListener {
    fun onClick(communication: Communication)
}