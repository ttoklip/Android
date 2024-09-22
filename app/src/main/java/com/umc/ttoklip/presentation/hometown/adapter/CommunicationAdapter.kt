package com.umc.ttoklip.presentation.hometown.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.umc.ttoklip.data.model.town.Communities
import com.umc.ttoklip.databinding.ItemCommunicationBinding
import com.umc.ttoklip.util.setOnSingleClickListener

class CommunicationAdapter(private val context: Context, private var listener: OnItemClickListener) :
    ListAdapter<Communities, CommunicationAdapter.CommunicationViewHolder>(object :
        DiffUtil.ItemCallback<Communities>() {
        override fun areItemsTheSame(oldItem: Communities, newItem: Communities): Boolean {
            return oldItem.writer == newItem.writer && oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Communities, newItem: Communities): Boolean {
            return oldItem == newItem
        }

    }) {
    inner class CommunicationViewHolder(private val binding: ItemCommunicationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(honeyTips: Communities, pos: Int) {
            if (pos == itemCount - 1) {
                binding.itemSeparator.isGone = true
            }
            Glide.with(context)
                .load(honeyTips.writerProfileImageUrl)
                .into(binding.writerIv)
            binding.titleTv.text = honeyTips.title
            binding.writerTv.text = honeyTips.writer
            binding.dateTv.isGone = true
            binding.bodyTv.text = honeyTips.content
            binding.starCountTv.text = honeyTips.scrapCount.toString()
            binding.likeCountTv.text = honeyTips.likeCount.toString()
            binding.commentCountTv.text = honeyTips.commentCount.toString()

            binding.root.setOnSingleClickListener {
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
    fun onClick(communication: Communities)
}