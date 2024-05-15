package com.umc.ttoklip.presentation.mypage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.umc.ttoklip.data.model.mypage.RestrictedResponse
import com.umc.ttoklip.databinding.ItemSuspensionHistoryBinding

class SuspensionAdapter : ListAdapter<RestrictedResponse, SuspensionAdapter.SuspensionViewHolder>(diff) {

    inner class SuspensionViewHolder(
        private val binding: ItemSuspensionHistoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: RestrictedResponse) {
            binding.suspensionTitleTv.text = data.type
            binding.suspensionReasonTv.text = data.reason
            binding.suspensionPeriodTv.text = data.duration
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuspensionViewHolder {
        return SuspensionViewHolder(
            ItemSuspensionHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SuspensionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val diff = object : DiffUtil.ItemCallback<RestrictedResponse>() {
            override fun areItemsTheSame(
                oldItem: RestrictedResponse,
                newItem: RestrictedResponse
            ): Boolean {
                return oldItem.duration == newItem.duration
            }

            override fun areContentsTheSame(
                oldItem: RestrictedResponse,
                newItem: RestrictedResponse
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}