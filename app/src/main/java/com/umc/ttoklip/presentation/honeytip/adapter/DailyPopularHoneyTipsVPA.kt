package com.umc.ttoklip.presentation.honeytip.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.umc.ttoklip.R
import com.umc.ttoklip.data.model.honeytip.HoneyTipMain
import com.umc.ttoklip.databinding.ItemDailyPopularHoneyTipBinding

class DailyPopularHoneyTipsVPA(
    private val fragment:Fragment,
    val onClick: (HoneyTipMain) -> Unit) :
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
            Glide.with(fragment.requireContext())
                .load(honeyTip.writerProfileImageUrl)
                .into(binding.profilePictureIv)
            binding.writerTv.text = honeyTip.writer
            binding.honeyTipTitleTv.text = honeyTip.title
            binding.honeyTipBodyContentTv.text = honeyTip.content
            binding.starCountTv.text = honeyTip.scrapCount.toString()
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