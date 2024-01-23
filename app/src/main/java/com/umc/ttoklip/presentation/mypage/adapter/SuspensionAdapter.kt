package com.umc.ttoklip.presentation.mypage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.umc.ttoklip.databinding.ItemSuspensionHistoryBinding

class SuspensionAdapter : ListAdapter<Suspension, SuspensionAdapter.SuspensionViewHolder>(diff) {

    inner class SuspensionViewHolder(
        private val binding: ItemSuspensionHistoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Suspension) {
            binding.suspensionTitleTv.text = data.title
            binding.suspensionReasonTv.text = data.reason
            binding.suspensionPeriodTv.text = data.period
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
        val diff = object : DiffUtil.ItemCallback<Suspension>() {
            override fun areItemsTheSame(
                oldItem: Suspension,
                newItem: Suspension
            ): Boolean {
                return oldItem.period == newItem.period
            }

            override fun areContentsTheSame(
                oldItem: Suspension,
                newItem: Suspension
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}