package com.umc.ttoklip.presentation.hometown.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.umc.ttoklip.databinding.ItemRecentlyUsedPlaceBinding

class RecentlyUsedPlaceAdapter(
    private var listener: OnRecentPlaceClickListener
) :
    ListAdapter<RecentPlace, RecentlyUsedPlaceAdapter.RecentlyUsedPlaceViewHolder>(diff) {
    inner class RecentlyUsedPlaceViewHolder(
        private val binding: ItemRecentlyUsedPlaceBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: RecentPlace, pos: Int) {
            binding.placeAddressTv.text = data.address
            binding.placeAddressDetailTv.text = data.addressDetail
            if (pos == itemCount - 1) {
                binding.divider.isGone = true
            }
            binding.root.setOnClickListener {
                listener.onClick(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentlyUsedPlaceViewHolder {
        return RecentlyUsedPlaceViewHolder(
            ItemRecentlyUsedPlaceBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecentlyUsedPlaceViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    companion object {
        val diff = object : DiffUtil.ItemCallback<RecentPlace>() {
            override fun areItemsTheSame(
                oldItem: RecentPlace,
                newItem: RecentPlace
            ): Boolean {
                return oldItem.address == newItem.address
            }

            override fun areContentsTheSame(
                oldItem: RecentPlace,
                newItem: RecentPlace
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}

interface OnRecentPlaceClickListener {
    fun onClick(items: RecentPlace)
}