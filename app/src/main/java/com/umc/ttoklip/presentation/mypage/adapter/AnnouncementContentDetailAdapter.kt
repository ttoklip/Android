package com.umc.ttoklip.presentation.mypage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.umc.ttoklip.databinding.ItemAnnouncementContentDetailBinding

class AnnouncementContentDetailAdapter :
    ListAdapter<AnnouncementContentDetail, AnnouncementContentDetailAdapter.DetailViewHolder>(diff) {
    inner class DetailViewHolder(
        private val binding: ItemAnnouncementContentDetailBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: AnnouncementContentDetail) {

            binding.announcementContentDetailTv.text = data.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        return DetailViewHolder(
            ItemAnnouncementContentDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val diff = object : DiffUtil.ItemCallback<AnnouncementContentDetail>() {
            override fun areItemsTheSame(
                oldItem: AnnouncementContentDetail,
                newItem: AnnouncementContentDetail
            ): Boolean {
                return oldItem.content == newItem.content
            }

            override fun areContentsTheSame(
                oldItem: AnnouncementContentDetail,
                newItem: AnnouncementContentDetail
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}