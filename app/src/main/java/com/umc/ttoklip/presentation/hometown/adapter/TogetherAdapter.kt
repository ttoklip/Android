package com.umc.ttoklip.presentation.hometown.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.umc.ttoklip.databinding.ItemTogetherBinding
import com.umc.ttoklip.util.setOnSingleClickListener

class TogetherAdapter(private var listener: OnTogetherClickListener, private val type: String) :
    ListAdapter<Together, TogetherAdapter.TogetherViewHolder>(diff) {
    inner class TogetherViewHolder(
        private val binding: ItemTogetherBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Together, pos: Int) {
            binding.togetherTitleTv.text = data.title
            binding.togetherAddressTv.text = data.address
            if (pos == itemCount - 1) {
                binding.divider.isGone = true
            }
            binding.root.setOnSingleClickListener {
                listener.onClick(data.id.toLong(), type)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TogetherViewHolder {
        return TogetherViewHolder(
            ItemTogetherBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TogetherViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    companion object {
        val diff = object : DiffUtil.ItemCallback<Together>() {
            override fun areItemsTheSame(
                oldItem: Together,
                newItem: Together
            ): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(
                oldItem: Together,
                newItem: Together
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}

interface OnTogetherClickListener {
    fun onClick(items: Long, type: String)
}