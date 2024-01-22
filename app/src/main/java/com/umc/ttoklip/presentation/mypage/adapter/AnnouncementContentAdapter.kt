package com.umc.ttoklip.presentation.mypage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.umc.ttoklip.databinding.ItemAnnouncementContentBinding

class AnnouncementContentAdapter :
    ListAdapter<AnnouncementContent, AnnouncementContentAdapter.AnnouncementContentViewHolder>(diff) {
    inner class AnnouncementContentViewHolder(
        private val binding: ItemAnnouncementContentBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: AnnouncementContent) {
            binding.announcementContentTitleTv.text = data.title
            val adapter = AnnouncementContentDetailAdapter()
            binding.announcementContentDetailRv.adapter = adapter
            adapter.submitList(data.contents)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AnnouncementContentViewHolder {
        val binding = ItemAnnouncementContentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        binding.announcementContentDetailRv.layoutManager = LinearLayoutManager(parent.context)
        return AnnouncementContentViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(holder: AnnouncementContentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val diff = object : DiffUtil.ItemCallback<AnnouncementContent>() {
            override fun areItemsTheSame(
                oldItem: AnnouncementContent,
                newItem: AnnouncementContent
            ): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(
                oldItem: AnnouncementContent,
                newItem: AnnouncementContent
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}