package com.umc.ttoklip.presentation.mypage.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.umc.ttoklip.R
import com.umc.ttoklip.data.model.mypage.NoticeDetail
import com.umc.ttoklip.data.model.mypage.NoticeResponse
import com.umc.ttoklip.databinding.ItemAnnouncementsBinding
import com.umc.ttoklip.util.setOnSingleClickListener

class AnnouncementAdapter :
    ListAdapter<NoticeDetail, AnnouncementAdapter.AnnouncementViewHolder>(diff) {
    inner class AnnouncementViewHolder(
        private val binding: ItemAnnouncementsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: NoticeDetail) {
            binding.announcementTitleTv.text = data.title
            binding.announcementDetailTv.text=data.content

            binding.announcementVisibilityBtn.setOnSingleClickListener {
                if (data.visibility) {
                    binding.announcementDetailSv.visibility = View.GONE
                    binding.announcementVisibilityBtn.setImageResource(R.drawable.ic_arrow_down_24)
                } else {
                    binding.announcementDetailSv.visibility = View.VISIBLE
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
        return AnnouncementViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnnouncementViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val diff = object : DiffUtil.ItemCallback<NoticeDetail>() {
            override fun areItemsTheSame(
                oldItem: NoticeDetail,
                newItem: NoticeDetail
            ): Boolean {
                return oldItem.content == newItem.content
            }

            override fun areContentsTheSame(
                oldItem: NoticeDetail,
                newItem: NoticeDetail
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}