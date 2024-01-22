package com.umc.ttoklip.presentation.mypage.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ItemAnnouncementsBinding

class AnnouncementAdapter :
    ListAdapter<Announcement, AnnouncementAdapter.AnnouncementViewHolder>(diff) {
    inner class AnnouncementViewHolder(
        private val binding: ItemAnnouncementsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Announcement) {
            binding.announcementTitleTv.text = data.title
            val adapter = AnnouncementContentAdapter()
            binding.announcementContentRv.adapter = adapter
            adapter.submitList(data.announcement)

            binding.announcementVisibilityBtn.setOnClickListener {
                if (data.visibility) {
                    binding.announcementContentFrame.visibility = View.GONE
                    binding.announcementVisibilityBtn.setImageResource(R.drawable.ic_arrow_down_24)
                } else {
                    binding.announcementContentFrame.visibility = View.VISIBLE
                    binding.announcementVisibilityBtn.setImageResource(R.drawable.ic_arrow_up_24)
                }
                data.visibility = data.visibility.not()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnnouncementViewHolder {
        val binding = ItemAnnouncementsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        binding.announcementContentRv.layoutManager = LinearLayoutManager(parent.context)
        return AnnouncementViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(holder: AnnouncementViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val diff = object : DiffUtil.ItemCallback<Announcement>() {
            override fun areItemsTheSame(
                oldItem: Announcement,
                newItem: Announcement
            ): Boolean {
                return oldItem.announcement == newItem.announcement
            }

            override fun areContentsTheSame(
                oldItem: Announcement,
                newItem: Announcement
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}